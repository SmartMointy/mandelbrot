package de.hft_stuttgart.ip1;

import de.hft_stuttgart.ip1.models.MandelbrotConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * This class implement a scrollable panel that draws the Mandelbrot set.
 * It must register itself as a property change listener to the Mandelbrot
 * config and must react accordingly, if one of the properties changes
 * (except rect, since this property changed is created by the class itself).
 * If you want to support multithreaded drawing, you can do so by letting the
 * SwingWorker store the value inside its variable colorMap which is then
 * used inside paintComponent to paint the component.
 */
public class MandelbrotLargePanel extends JPanel implements Scrollable {
    // Proposal for the colors: WHITE, if less than 10 iterations are needed
    // to get a value whose absolute is larger than 1024^3, etc. until BLACK,
    // if the maximum number of iterations the absolute is still less than 2.
    final static Color[] colors = new Color[] {
            Color.WHITE, Color.LIGHT_GRAY, Color.YELLOW,
            Color.RED,Color.PINK, Color.ORANGE, Color.MAGENTA,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.BLACK};
    Color[] colorMap;

    private int iterations;
    private int threads;
    private int completed;
    // Needed to detect repaint because the visible rectangle changed
    private Rectangle lastRect;
    private final ArrayList<SwingWorker<Void, Void>> workerList = new ArrayList<SwingWorker<Void, Void>>();

    public MandelbrotLargePanel() {
        setIterations(100);
        setThreads(1);
        MandelbrotConfig config = MandelbrotConfig.getInstance();
        config.setResolution(1);
        config.addPropertyChangeListener((evt)->{
            if ( evt.getPropertyName().equals("rect") ) {
                return;
            }

            switch (evt.getPropertyName()) {
                case MandelbrotConfig.RESOLUTION_PROPERTY_NAME:
                    Dimension dim = new Dimension(300 * config.getResolution(), 200 * config.getResolution());
                    setPreferredSize(dim);
                    setSize(dim);
                    break;
                case MandelbrotConfig.ITERATIONS_PROPERTY_NAME:
                    setIterations((int) evt.getNewValue());
                    break;
                case MandelbrotConfig.THREADS_PROPERTY_NAME:
                    setThreads((int) evt.getNewValue());
                    break;
                default:
                    break;
            }

            repaint();
        });

        setLayout(null);
        Dimension dim = new Dimension(300 * config.getResolution(), 200 * config.getResolution());
        setPreferredSize(dim);
        setSize(dim);
    }

    @Override
    protected void paintComponent(Graphics gc) {
        Graphics2D gc2 = (Graphics2D) gc;
        super.paintComponent(gc);

        Rectangle rect = getVisibleRect();

        int scale = MandelbrotConfig.getInstance().getResolution();
        ArrayList<Integer[]> px = MandelBrot.calculate(rect.x,rect.y, rect.width, rect.height, scale, iterations, threads);

        px.forEach(x -> {
            gc2.setColor(colors[x[2] / (iterations /10)]);
            gc2.drawLine(x[0],x[1],x[0],x[1]);
        });
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 1;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        return false;
    }
}
