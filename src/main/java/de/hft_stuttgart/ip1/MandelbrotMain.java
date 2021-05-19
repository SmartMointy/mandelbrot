package de.hft_stuttgart.ip1;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MandelbrotMain {
    public final static String ICON_IMAGE = "/de/hft_stuttgart/ip1/Mandelbrot-S.png";
    public static void main(String[] args) {
        EventQueue.invokeLater(()-> {
            JFrame mainFrame = new JFrame("Mandelbrot");
            ImageIcon img = new ImageIcon(Objects.requireNonNull(MandelbrotMain.class.getResource(ICON_IMAGE)));

            mainFrame.setIconImage(img.getImage());
            mainFrame.setContentPane(new MandelbrotMainFrame(mainFrame).$$$getRootComponent$$$());
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.pack();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}
