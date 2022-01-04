package com.thoughtworks.xstream.converters.awt;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import cuploader.ServerMonitor;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.awt.Point;

public class PointConverter implements Converter {

    @Override
    public boolean canConvert(final Class type) {
        return type == Point.class;
    }

    @Override
    public void marshal(final Object source, final HierarchicalStreamWriter writer, final MarshallingContext context) {
        final Point point = (Point) source;

        writer.startNode("x");
        writer.setValue(String.valueOf(point.getX()));
        writer.endNode();

        writer.startNode("y");
        writer.setValue(String.valueOf(point.getY()));
        writer.endNode();
    }

    @Override
    public Object unmarshal(final HierarchicalStreamReader reader, final UnmarshallingContext context) {
        reader.moveDown();
        final int x = (int)Double.parseDouble(reader.getValue());
        reader.moveUp();

        reader.moveDown();
        final int y = (int)Double.parseDouble(reader.getValue());
        reader.moveUp();

        return new Point(x, y);
    }

    protected static Logger log = Logger.getLogger(PointConverter.class.getName());

    protected static void debug(String s) {
        log.log(Level.INFO, s);
    }
}
