package cuploader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.awt.DimensionConverter;
import com.thoughtworks.xstream.converters.awt.PointConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import cuploader.frames.*;
import cuploader.SessionList;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.undo.UndoManager;
import org.wikipedia.Wiki;

public class Data implements Serializable {
    private transient static PropertyChangeSupport propchange;
    public static Settings settings;
    
    public enum Elem { NAME, EXT, DATE, COOR, DESC, CATS; }
    
    //text
    public static ResourceBundle text = ResourceBundle.getBundle("cuploader/text/messages", Locale.ENGLISH);
    public static String text(String s){
        return text.getString(s);
    }
    
    public static Wiki wiki;
    public static UndoManager manager = new UndoManager();
    
    public static String version;
    public static String minorVersion;
    public static String typeVersion;
    public static String date;
    private static boolean loggedIn = false;
    
    //windows
    public static FSettings fSettings;
    public static FAbout fAbout;
    public static FLogin fLogin;
    public static FUploadCheck fUploadCheck;
    public static FFileEdit fFileEdit;
    
    //help vars
    public static double sizeToUpload = 0;
    public static boolean ctrlPress = false;
    public static boolean shiftPress = false;
    public static boolean isLoadSession = false;
    public static String[][] loadSessionData;
      
    //storage info
    private static ArrayList<PFile> files = new ArrayList<PFile>();       //stores all images
    
    //counters
    public static int filesUpload = 0;
    public static int filesEdit = 0;

    //quick templates
    public static JPopupMenu mQuickTemplates = new JPopupMenu();
    
    public Data() {
        propchange = new PropertyChangeSupport(this);
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean n) {
        boolean old = loggedIn;
        loggedIn = n;
        propchange.firePropertyChange("loggedIn", old, n);
    }
    
    /***
     * Updates upload counter in bottom right corner on main frame
     */
    public static void updateFileCounter() {
        DecimalFormat df = new DecimalFormat("##.##");
        int toUpload = 0;
        float toUploadSize = 0;
        
        for(PFile f : files) {
            if(f.toUpload) {
                ++toUpload;
                toUploadSize += 9.5367e-7*f.file.length();
            }
        }
        filesUpload = toUpload;
        Main.lFileUpload.setText(toUpload + " / " + files.size() + " (" + df.format(toUploadSize) + " MiB)");
    }
    
    /*
     * Quick templates stuff
     */
    public static void initializeQuickTemplates() {
        mQuickTemplates.removeAll();
        for(final QuickTemplate qt : settings.quickTemplates) {
            if(qt.active) {
                final JMenuItem item = new JMenuItem(qt.name);
                item.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        insertTemplate((JTextArea)((JPopupMenu)((JMenuItem)e.getSource()).getParent()).getInvoker(), qt);
                    }
                });
                mQuickTemplates.add(item);
            }
        }
    }
    public static void destroyQuickTemplates() {
        mQuickTemplates.removeAll();
    }
    
    static void insertTemplate(JTextArea textarea, QuickTemplate i) {
        String template = i.template;
        String selection = textarea.getSelectedText();
        
        textarea.requestFocus();
        if(template.contains("%TEXT%")) {
            if(selection==null) {
                textarea.append(template.replace("%TEXT%", Data.text("file-wiki-text")));
                textarea.select(textarea.getText().indexOf(Data.text("file-wiki-text")), textarea.getText().indexOf(Data.text("file-wiki-text"))+Data.text("file-wiki-text").length());
            } else
                textarea.replaceSelection(template.replace("%TEXT%", selection));
        } else
            textarea.append(template);
    }
    
    /***
     * Adds file panel to main window
     * @param p file panel
     */
    public static void addPanel(PFile p) {
        GridBagConstraints gbc = new GridBagConstraints();
        //ogranicznik.insets = new Insets(5, 10, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0; //files.size()%2;
        gbc.gridy = files.size();///2;

        files.add(p);
        Main.pFiles.add(p, gbc);
        updateFileCounter();
    }
    
    //get
    public static ArrayList<PFile> getFiles() {
        return files;
    }
    
    public static SessionList getFilesForXML() {
       SessionList sessionList = new SessionList();
       for(PFile file : files)
         sessionList.addSessionFile(file.returnData());
       return sessionList;
    }

    public static int getFirstFileEdit() {
        for (int i = 0; i < files.size(); ++i) {
            if (files.get(i).toEdit) {
                return i;
            }
        }
        return -1;
    }
    
        /**
     * 
     */
    public static void saveSettings() {
      try {
        XStream xstream = new XStream(new DomDriver("UTF-8"));
        xstream.processAnnotations(cuploader.Settings.class);
        xstream.processAnnotations(cuploader.QuickTemplate.class);
        xstream.processAnnotations(cuploader.DescSource.class);
        xstream.registerConverter(new DimensionConverter(), Integer.MAX_VALUE);
        xstream.registerConverter(new PointConverter(), Integer.MAX_VALUE);

        try {
          Writer writer = new OutputStreamWriter(new FileOutputStream("settings.vicuna"), Charset.forName("UTF-8"));
          xstream.toXML(settings, writer);
        } catch (Exception e) {
          JOptionPane.showMessageDialog(fSettings, e.getMessage());
          System.err.println("Error: " + e.getMessage());
        }
      } catch (Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public static int getOrientation(boolean isImage) {
      ComponentOrientation co = ComponentOrientation.getOrientation(settings.getLang());
      if(isImage) return co.isLeftToRight() ? SwingConstants.RIGHT : SwingConstants.LEFT;
      return co.isLeftToRight() ? SwingConstants.LEFT : SwingConstants.RIGHT;
    }

    public static ComponentOrientation getComponentOrientation() {
      return ComponentOrientation.getOrientation(settings.getLang());
    }

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        propchange.addPropertyChangeListener(listener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener listener) {
        propchange.removePropertyChangeListener(listener);
    }

    static final long serialVersionUID = 5293929884165981611L;
}
