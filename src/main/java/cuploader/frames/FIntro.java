package cuploader.frames;

import cuploader.Data;
import static cuploader.frames.Main.error;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import org.wikipedia.Wiki;

public class FIntro extends javax.swing.JFrame {

  Data data = new Data();
  Main m;

  public final static String HOMEPAGE_URL = "https://github.com/exander77/vicuna/releases";
  
  public FIntro() {
    initComponents();
    setLocationRelativeTo(null);
    bUpdate.setVisible(false);
    bDownload.setVisible(false);
    bCancel.setVisible(false);
    
    Data.version = "1.3";
    Data.minorVersion = ".3a";
    Data.typeVersion = "Supported Edition";
    Data.date = "";
    
    Runnable run = new Runnable() {
      @Override
      public void run() {
        tTitle.setText("<html><body><b>VicuñaUploader " + Data.version + Data.minorVersion + " " + Data.typeVersion + "</b></body></html>");
        setVisible(true);
        
        boolean b = checkVersion();
        if(!b) {
          dispose();
          m = new Main(data);
        }
      }
    };

    Thread t = new Thread(run);
    t.setName("FIntro: Splash screen/Updater");
    t.start();
  }

  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar = new javax.swing.JProgressBar();
        lLogo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tStatus = new javax.swing.JLabel();
        bUpdate = new javax.swing.JButton();
        bDownload = new javax.swing.JButton();
        bCancel = new javax.swing.JButton();
        tTitle = new javax.swing.JLabel();

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/cuploader/resources/logo.png")));
        setUndecorated(true);
        setResizable(false);

        jProgressBar.setIndeterminate(true);

        lLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cuploader/resources/logo.png"))); // NOI18N

        tStatus.setText("Initializing...");

        bUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cuploader/resources/tick.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("cuploader.text.messages"); // NOI18N
        bUpdate.setText(bundle.getString("button-autoupdate")); // NOI18N
        bUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUpdateActionPerformed(evt);
            }
        });

        bDownload.setText(bundle.getString("button-download")); // NOI18N
        bDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bDownloadActionPerformed(evt);
            }
        });

        bCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cuploader/resources/cross.png"))); // NOI18N
        bCancel.setText(bundle.getString("button-cancel")); // NOI18N
        bCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCancelActionPerformed(evt);
            }
        });

        tTitle.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(tTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bUpdate)
                    .addComponent(bDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bCancel)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lLogo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

  private void bCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCancelActionPerformed
      dispose();
      m = new Main(data);
  }//GEN-LAST:event_bCancelActionPerformed

  private void bDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bDownloadActionPerformed
    try {
      Desktop.getDesktop().browse(new URI(HOMEPAGE_URL));
      dispose();
    } catch (IOException ex) {
      error("Cannot open the home page", ex);
    } catch (URISyntaxException ex) {
      error("Cannot open the home page", ex);
    }
  }//GEN-LAST:event_bDownloadActionPerformed

  private void bUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUpdateActionPerformed
    URL source = forcedUpdateURL();
    if (source == null) {
       String updateURL = "UNKNOWN";
       try {
         List<String> pages = List.of("User:Exander77/VicunaUploader/version");
         String v = Wiki.newSession("commons.wikimedia.org").getPageText(pages).get(0).trim();
         updateURL = "https://github.com/exander77/vicuna/releases/download/v" + v + "/" + v + ".jar";
         source = new URL(updateURL);
       } catch (MalformedURLException ex) {
         error("Invalid update URL: <" + updateURL + ">", ex);
       } catch (IOException ex) {
            Logger.getLogger(FIntro.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    new FDownload(this, source);
  }//GEN-LAST:event_bUpdateActionPerformed


  /**
   * Return URL to download the update from (if faking)
   * @return String
   */
  public URL forcedUpdateURL() {
     String forceupdate = System.getProperties().getProperty("cuploader.test.forceupdate");
     URL u = null;
     if (forceupdate != null) {
       try {
          u = new URL(forceupdate);
       } catch (MalformedURLException ex) {
          error("Ignored fake update request: <" + forceupdate + ">", ex);
          u = null;
       }
     }
     return u;
  }

  /**
   * Checks current version of program and compate it to latest version available online.
   * @return boolean need update?
   */
  private boolean checkVersion() {
    try {
      List<String> pages = List.of("User:Exander77/VicunaUploader/version");
      String v = Wiki.newSession("commons.wikimedia.org").getPageText(pages).get(0).trim();
      Logger.getLogger(FIntro.class.getName()).log(Level.INFO, "Newest version: {0}", v);
      Logger.getLogger(FIntro.class.getName()).log(Level.INFO, "Curent version: {0}{1}", new Object[]{Data.version, Data.minorVersion});
      if(v.compareTo(Data.version+Data.minorVersion)>0) {
        bUpdate.setVisible(true);
        bDownload.setVisible(true);
        bCancel.setVisible(true);
        jProgressBar.setIndeterminate(false);
        Toolkit.getDefaultToolkit().beep();
        
        tStatus.setText("<html><body>" + Data.text("about-checkupdate-text") + " (<b>" + v + "</b>).</body></html>");
        return true;
      } else return false;
    } catch (UncheckedIOException ex) {
      error("Cannot check for the new version", ex);
    } catch (IOException ex) {
      error("Cannot check for the new version", ex);
    }
    return false;
  }
  
  public static void main(String args[]) {
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code ">
    try {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException ex) {
        error("Cannot setup look and feel", ex);
      } catch (InstantiationException ex) {
        error("Cannot setup look and feel", ex);
      } catch (IllegalAccessException ex) {
        error("Cannot setup look and feel", ex);
      }
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      error("Cannot setup look and feel", ex);
    }
        //</editor-fold>

    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new FIntro();
      }
    });
  }
      
    protected static void error(String s, Object ex) {
      logger.log(java.util.logging.Level.SEVERE, s, ex);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCancel;
    private javax.swing.JButton bDownload;
    private javax.swing.JButton bUpdate;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar;
    private javax.swing.JLabel lLogo;
    private javax.swing.JLabel tStatus;
    private javax.swing.JLabel tTitle;
    // End of variables declaration//GEN-END:variables

    static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FIntro.class.getName());

    static final long serialVersionUID = 8446207171851566363L;
}
