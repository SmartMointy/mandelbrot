package de.hft_stuttgart.ip1.actions;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class FileExitAction extends AbstractAction {
    static {
        AbstractAction theInstance = new FileExitAction();
        theInstance.putValue(Action.NAME, "Exit");
        theInstance.putValue(CommonAction.WEIGHT, 100);
        theInstance.putValue(CommonAction.HIERARCHY, "File");
        CommonAction.addMenuEntry(theInstance);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CommonAction.frame.setVisible(false);
        CommonAction.frame.dispose();
    }
}
