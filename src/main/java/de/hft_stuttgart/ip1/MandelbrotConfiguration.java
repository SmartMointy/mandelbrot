package de.hft_stuttgart.ip1;

import de.hft_stuttgart.ip1.actions.EditConfigurationAction;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Objects;

import de.hft_stuttgart.ip1.models.MandelbrotConfig;

public class MandelbrotConfiguration extends JDialog {
    public final static String ICON_IMAGE = "/de/hft_stuttgart/ip1/Mandelbrot-L.png";
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSlider slider1;
    private JSlider slider2;
    private JSlider slider3;
    private JPanel naviPanel;

    public MandelbrotConfiguration(JFrame parent, EditConfigurationAction action) {
        super(parent, "Konfiguration");
        $$$setupUI$$$();
        action.setEnabled(false);
        MandelbrotConfig config = MandelbrotConfig.getInstance();

        // call onClose() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose(action);
            }
        });

        // call onClose() on ESCAPE
        contentPane.registerKeyboardAction(e -> onClose(action), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        add(contentPane);
        setResizable(false);
        ImageIcon img = new ImageIcon(Objects.requireNonNull(MandelbrotMain.class.getResource(ICON_IMAGE)));
        setIconImage(img.getImage());

        // BUTTONS LISTENER
        buttonCancel.addActionListener(e -> onClose(action));

        buttonOK.addActionListener(e -> {
            int res = (int) Math.round(Math.pow(2, (slider1.getValue() - 1) * 0.5));
            config.setResolution(res);
            config.setIterations(slider2.getValue());
            config.setThreads(slider3.getValue());
        });

        // CUSTOM LABELS
        Hashtable<Integer, JLabel> lableTableSlider1 = new Hashtable<>();

        lableTableSlider1.put(1, new JLabel(Integer.toString(1)));
        for (int i = 1; i < 12; i++) {
            lableTableSlider1.put(2 * i + 1, new JLabel(Integer.toString((int) Math.pow(2, i))));
        }
        slider1.setLabelTable(lableTableSlider1);

        Hashtable<Integer, JLabel> lableTableSlider2 = new Hashtable<>();

        lableTableSlider2.put(100, new JLabel(Integer.toString(100)));
        lableTableSlider2.put(5000, new JLabel(Integer.toString(5000)));
        lableTableSlider2.put(10000, new JLabel(Integer.toString(10000)));

        slider2.setLabelTable(lableTableSlider2);

        int coreCount = Runtime.getRuntime().availableProcessors();
        slider3.setMaximum(Math.min(coreCount, 10));

        JScrollPane scrollPane = (JScrollPane) parent.getContentPane().getComponent(0).getParent();

        scrollPane.getVerticalScrollBar().addAdjustmentListener((evt -> {
            updateSmallPanel(parent);
        }));
        scrollPane.getHorizontalScrollBar().addAdjustmentListener((evt -> {
            updateSmallPanel(parent);
        }));

        // Werte von Config laden
        int res = (int) (Math.log(config.getResolution()) * 2 / Math.log(2)) + 1; // Zurückrechnen
        slider1.setValue(res);
        slider2.setValue(config.getIterations());
        slider3.setValue(config.getThreads());
    }

    private void onClose(EditConfigurationAction action) {
        action.setEnabled(true);
        dispose();
    }

    private void updateSmallPanel(JFrame parent) {
        JScrollPane scrollPane = (JScrollPane) parent.getContentPane().getComponent(0).getParent();

        double res = MandelbrotConfig.getInstance().getResolution();
        double x = (double) (scrollPane.getHorizontalScrollBar().getValue() / (res * 300));
        double y = (double) scrollPane.getVerticalScrollBar().getValue() / (res * 200);

        naviPanel.firePropertyChange("scrollX", -1, x);
        naviPanel.firePropertyChange("scrollY", -1, y);
        naviPanel.firePropertyChange("scaleX", -1, (double) (parent.getSize().width - 35) / (300.0 * MandelbrotConfig.getInstance().getResolution()));
        naviPanel.firePropertyChange("scaleY", -1, (double) (parent.getSize().height - 80) / (200.0 * MandelbrotConfig.getInstance().getResolution()));
    }

    private void createUIComponents() {
        naviPanel = new MandelbrotSmallPanel();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setMinimumSize(new Dimension(450, 270));
        contentPane.setPreferredSize(new Dimension(500, 275));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, BorderLayout.CENTER);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_NORTH, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        slider1 = new JSlider();
        slider1.setMajorTickSpacing(2);
        slider1.setMaximum(21);
        slider1.setMinimum(1);
        slider1.setMinorTickSpacing(1);
        slider1.setPaintLabels(true);
        slider1.setPaintTicks(true);
        slider1.setPaintTrack(true);
        slider1.setSnapToTicks(false);
        slider1.setValue(3);
        slider1.putClientProperty("Slider.paintThumbArrowShape", Boolean.FALSE);
        panel2.add(slider1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(260, -1), new Dimension(260, -1), 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Iterationen");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Auflösung");
        panel2.add(label2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        slider2 = new JSlider();
        slider2.setMajorTickSpacing(4900);
        slider2.setMaximum(10000);
        slider2.setMinimum(100);
        slider2.setMinorTickSpacing(0);
        slider2.setPaintLabels(true);
        slider2.setPaintTicks(true);
        slider2.setValue(100);
        panel2.add(slider2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Threads");
        panel2.add(label3, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new BorderLayout(0, 0));
        panel2.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 5, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        slider3 = new JSlider();
        slider3.setMajorTickSpacing(1);
        slider3.setMaximum(4);
        slider3.setMinimum(1);
        slider3.setMinorTickSpacing(0);
        slider3.setPaintLabels(true);
        slider3.setPaintTicks(true);
        slider3.setValue(1);
        panel3.add(slider3, BorderLayout.CENTER);
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer1, BorderLayout.WEST);
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer2, BorderLayout.EAST);
        panel1.add(naviPanel, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(250, 24), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, new Dimension(10, -1), new Dimension(15, -1), null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        contentPane.add(panel4, BorderLayout.SOUTH);
        buttonOK = new JButton();
        buttonOK.setText("Übernehmen");
        panel4.add(buttonOK);
        buttonCancel = new JButton();
        buttonCancel.setText("Schließen");
        panel4.add(buttonCancel);
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel5.setPreferredSize(new Dimension(0, 15));
        contentPane.add(panel5, BorderLayout.NORTH);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}