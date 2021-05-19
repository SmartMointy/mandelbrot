package de.hft_stuttgart.ip1.models;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class is a singleton, see https://de.wikipedia.org/wiki/Singleton_(Entwurfsmuster)
 * The class must contain a PropertyChangeSupport, such that every setter
 * for its properties fires a PropertyChangeEvent. All setters must set the property
 * before the PropertyChangeEvent is fired.
 */
public class MandelbrotConfig {
    public static final String ITERATIONS_PROPERTY_NAME = "iterations";
    public static final String RESOLUTION_PROPERTY_NAME = "resolution";
    public static final String THREADS_PROPERTY_NAME = "threads";
    public static final String RECT_PROPERTY_NAME = "rect";

    private static class Holder {
        private final static MandelbrotConfig THE_INSTANCE = new MandelbrotConfig();
    }

    public static MandelbrotConfig getInstance() {
        return Holder.THE_INSTANCE;
    }

    private PropertyChangeSupport pcs;
    // Properties
    private int iterations = 100;
    private int resolution = 2;
    private int threads = 1;
    private Rectangle rect;


    private MandelbrotConfig() {
        pcs = new PropertyChangeSupport(this);
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        pcs.firePropertyChange(ITERATIONS_PROPERTY_NAME, this.iterations, iterations);
        this.iterations = iterations;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        int old = this.resolution;
        this.resolution = resolution;
        pcs.firePropertyChange(RESOLUTION_PROPERTY_NAME, old, resolution);
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        int old = this.threads;
        this.threads = threads;
        pcs.firePropertyChange(THREADS_PROPERTY_NAME, old, threads);
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        Rectangle old = this.rect;
        this.rect = rect;
        pcs.firePropertyChange(RECT_PROPERTY_NAME, old, rect);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void addPropertyChangeListener(String name, PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(name, pcl);
    }
}
