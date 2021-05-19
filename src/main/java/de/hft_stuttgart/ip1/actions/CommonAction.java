package de.hft_stuttgart.ip1.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * The class CommonAction is responsible for loading all the subclasses
 * of Action into the system. This is done by loading all classes with
 * <pre>Class.forName()</pre>. The loaded class then calls addMenuEntry
 * an registers itself inside the menu.
 */
public abstract class CommonAction {
    static JFrame frame;

    public static final String WEIGHT = "Weight";
    public static final String HIERARCHY = "Hierarchy";
    public static final String MENU_ENTRY = "MenuEntry";

    // List of all actions
    private final static String[] ACTIONS = new String[]{
            "de.hft_stuttgart.ip1.actions.FilePrintAction",
            "de.hft_stuttgart.ip1.actions.FileExitAction",
            "de.hft_stuttgart.ip1.actions.EditConfigurationAction",
    };

    // Called from MandelbrotMainFrame to initialize the menu by calling
    // the registered classes. Must throw an IllegalStateException if
    // called twice
    public static void initActions(JFrame parent) {
        if ( CommonAction.frame != null ) {
            throw new IllegalStateException("Static initializer called twice!");
        }

        CommonAction.frame = parent;

        for (String name : ACTIONS) {
            try {
                Class.forName(name);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + e);
            }
        }

    }

    /**
     * Adds a menu entry to the main menu in frame. This method ist called from
     * the Action classes static initializer. It enters the action as a menu
     * entry in the submenu defined by the Action classes HIERARCHY-value:
     * HIERARCHY = Run implies that the action is added to the submenu under the
     * main menu entry Run. All entries in a submenu are sorted according to
     * their non-decreasing WEIGHT-value.
     * @param action The action to be introduced. If this action does not define
     *               the Integer-value WEIGHT and the String-value HIERARCHY,
     *               behaviour is undefined.
     */
    public static void addMenuEntry(AbstractAction action) {
        String[] hierarchy = ((String) action.getValue(HIERARCHY)).split("\\|");

        if (hierarchy.length < 1) {
            throw new IllegalArgumentException("So nicht Minnjung!");
        }

        if (frame.getJMenuBar() == null) {
            JMenuBar menuBar = new JMenuBar();
            frame.add(menuBar);
        }

        JMenuBar mb = frame.getJMenuBar();
        JMenu element = (JMenu) findOrCreate(mb, (String) action.getValue(HIERARCHY));
        element.add(new JMenuItem(action));

        createSeparators(element);
    }

    /**
     * Method to create necessary separators in a submenu. It runs through
     * a submenu and compares two successive entries. To get the entries,
     * it uses the methods <pre>getMenuComponentCount</pre> and
     * <pre>getMenuComponent</pre>. If two successive entries are both
     * instances of JMenuItem, and if their weights divided by 100 are not
     * equal, a <pre>JSeparator</pre>-entry is inserted between them.
     * @param myMenu
     */
    private static void createSeparators(JMenu myMenu) {
        for(int i = 0; i < myMenu.getMenuComponentCount() - 1; i++) {
            if (myMenu.getMenuComponent(i) instanceof JMenuItem && myMenu.getMenuComponent(i + 1) instanceof JMenuItem) {
                Action action1 = ((JMenuItem) myMenu.getMenuComponent(i)).getAction();
                Action action2 = ((JMenuItem) myMenu.getMenuComponent(i + 1)).getAction();

                int weight1 = (int) action1.getValue(WEIGHT);
                int weight2 = (int) action2.getValue(WEIGHT);

                if (weight1 / 100 != weight2 / 100) {
                    myMenu.insertSeparator(i + 1);
                }
            }
        }
    }

    /**
     * Finds or creates a submenu of the given MenuElement with the given
     * text. If a submenu exists, the existing entry must be returned,
     * otherwise a new entry must be created.
     * @param menuElement The element the menu is put in, can be a JMenuBar
     *                    instance or a JMenu instance.
     * @param text Text to be shown, if it contains an underscore (`_`), the
     *             underscore must be removed and the character immediately
     *             following becomes the mnemonic.
     * @return
     */
    public static MenuElement findOrCreate (MenuElement menuElement, String text){
        JMenuBar menu = (JMenuBar) menuElement;
        for (MenuElement mE : menu.getSubElements()) {
            JMenu subMenu = (JMenu) mE;

            if (subMenu.getText().equals(text)) {
                return mE;
            }
        }
        JMenu newSub = new JMenu(text);
        menu.add(newSub);

        return newSub;
    }
}
