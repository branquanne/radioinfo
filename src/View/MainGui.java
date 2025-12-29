package View;

import javax.swing.*;

public class MainGui {
    private static JFrame frame;
    private static JMenuBar menuBar;
    private static JMenu channelsMenu;
    private static JMenu programsMenu;
    private static JMenuItem showChannelsTable;
    private static JMenuItem showProgramsTable;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGui::showGUI);
    }

    private static void showGUI() {
        frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        initMenu();
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    private static void initMenu() {
        menuBar = new JMenuBar();

        channelsMenu = new JMenu("Channels");
        programsMenu = new JMenu("Programs");

        showChannelsTable = new JMenuItem("Show Table");
        showProgramsTable = new JMenuItem("Show Table");

        channelsMenu.add(showChannelsTable);
        programsMenu.add(showProgramsTable);

        menuBar.add(channelsMenu);
        menuBar.add(programsMenu);
    }

}
