package de.hft_stuttgart.ip1;

import de.hft_stuttgart.ip1.models.MandelbrotConfig;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** Non-scrollable, non-scalable and single threaded version of
 * MandelbrotLargePanel. Needs to redraw itself if either resolution
 * or rect of MandelbrotConfig change, because the navigation
 * rectangle changes.
 */
public class MandelbrotSmallPanel extends JPanel {
    final static Color[] colors = new Color[] {
            Color.WHITE, Color.LIGHT_GRAY, Color.YELLOW,
            Color.RED,Color.PINK, Color.ORANGE, Color.MAGENTA,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.BLACK};
    private Rectangle rect;

    public MandelbrotSmallPanel() {
        MandelbrotConfig config = MandelbrotConfig.getInstance();
        config.addPropertyChangeListener((evt)->{
            if ( evt.getPropertyName().equals("resolution") ||
                    evt.getPropertyName().equals("rect") ) {
                repaint();
            }
        });

        rect = new Rectangle(0,0,298,200);

        addPropertyChangeListener((evt) -> {
            switch (evt.getPropertyName()) {
                case "scrollX":
                    double x = (double) evt.getNewValue();
                    rect.setLocation((int) (300*x), rect.y);
                    break;
                case "scrollY":
                    double y = (double) evt.getNewValue();
                    rect.setLocation(rect.x, (int) (200*y));
                    break;
                case "scaleX":
                    double dimX = (double) evt.getNewValue();
                    rect.setSize( Math.min((int) (300 * dimX), 298), rect.height);
                    break;
                case "scaleY":
                    double dimY = (double) evt.getNewValue();
                    rect.setSize(rect.width, Math.min((int) (200 * dimY), 200));
                    break;
                default:
                    break;

            }

            repaint();
        });
    }

    /**
     * Paint a Mandelbrot set and the rectangle. You may use only
     * 100 iterations. If the result diverges after x iterations,
     * you can use COLORS[x/10] to paint the dot.
     * @param gc
     */
    @Override
    protected void paintComponent(Graphics gc) {
        Graphics2D gc2 = (Graphics2D)gc;
        super.paintComponent(gc);

        ArrayList<Integer[]> px = MandelBrot.calculate(0, 0, 300, 200, 1, 200, 4);

        px.forEach(x -> {
            gc2.setColor(colors[x[2] / (200 /10)]);
            gc2.drawLine(x[0],x[1],x[0],x[1]);
        });

        gc2.setColor(colors[3]);
        gc2.drawRect(rect.x, rect.y, rect.width, rect.height);
    }
}
