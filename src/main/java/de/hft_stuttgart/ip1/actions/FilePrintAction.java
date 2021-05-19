package de.hft_stuttgart.ip1.actions;

import de.hft_stuttgart.ip1.MandelBrot;
import de.hft_stuttgart.ip1.models.MandelbrotConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.print.*;
import java.util.ArrayList;


public class FilePrintAction extends AbstractAction {
    final static Color[] colors = new Color[] {
            Color.WHITE, Color.LIGHT_GRAY, Color.YELLOW,
            Color.RED,Color.PINK, Color.ORANGE, Color.MAGENTA,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.BLACK};

    static {
        AbstractAction theInstance = new FilePrintAction();
        theInstance.putValue(Action.NAME, "Print");
        theInstance.putValue(CommonAction.WEIGHT, 50);
        theInstance.putValue(CommonAction.HIERARCHY, "File");
        CommonAction.addMenuEntry(theInstance);
    }

    /**
     * Print the current Mandelbrot graphics on a single sheet of
     * paper. For the first attempt, it suffices to write the
     * String "Mandelbrot" centered on the page. You must create
     * an instance of PrinterJob, assign it a printable which
     * performs the actual printing onto a Graphics2D and finally
     * call the Method printDialog. If this method returns true,
     * you must call the print method.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new Thread(() -> {
            setEnabled(false);
            PrinterJob printerJob = PrinterJob.getPrinterJob();

            Printable printable = (g, pageFormat, pageIndex) -> {
                if (pageIndex != 0) return Printable.NO_SUCH_PAGE;

                int scale = MandelbrotConfig.getInstance().getResolution();
                int iterations = MandelbrotConfig.getInstance().getIterations();

                ArrayList<Integer[]> plp = MandelBrot.calculate(0,0, 300*scale, 200*scale, scale, iterations, 4);

                plp.forEach(x -> {
                    g.setColor(colors[x[2] / (iterations /10)]);
                    g.drawLine(x[0],x[1],x[0],x[1]);
                });

                g.dispose();
                return Printable.PAGE_EXISTS;
            };

            printerJob.setPrintable(printable);

            printerJob.printDialog();

            try {
                printerJob.print();
                System.out.println("Done!");
            } catch (PrinterException printerException) {
                System.out.println("Print canceled!");
            }

            setEnabled(true);
        }).start();
    }
}
