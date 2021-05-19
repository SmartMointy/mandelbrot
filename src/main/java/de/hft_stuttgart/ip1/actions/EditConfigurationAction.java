package de.hft_stuttgart.ip1.actions;

import de.hft_stuttgart.ip1.MandelbrotConfiguration;
import de.hft_stuttgart.ip1.MandelbrotMain;
import de.hft_stuttgart.ip1.MandelbrotMainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Objects;

public class EditConfigurationAction extends AbstractAction {
    static {
        AbstractAction theInstance = new EditConfigurationAction();
        theInstance.putValue(Action.NAME, "Configuration");
        theInstance.putValue(CommonAction.WEIGHT, 100);
        theInstance.putValue(CommonAction.HIERARCHY, "Edit");
        theInstance.putValue(AbstractAction.MNEMONIC_KEY, KeyEvent.VK_E);
        theInstance.putValue(AbstractAction.ACCELERATOR_KEY,
                KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK));
        CommonAction.addMenuEntry(theInstance);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MandelbrotConfiguration configuration = new MandelbrotConfiguration(CommonAction.frame, this);
        configuration.pack();
        configuration.setVisible(true);
        configuration.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
