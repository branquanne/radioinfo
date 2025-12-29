package View;

import javax.swing.*;

public class MainGui {
    private static JFrame frame;
    private static JMenuBar menuBar;
    private static JMenu channelsMenu;
    private static JMenu programsMenu;
    private static JMenuItem showChannelsTable;
    private static JMenuItem showProgramsTable;


    public MainGui() {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGui::showGUI);
    }

    private static void showGUI() {
        JFrame frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);


        frame.setVisible(true);
    }

    private static void initMenu() {
        menuBar = new JMenuBar();

        channelsMenu = new JMenu();
        programsMenu = new JMenu();

        showChannelsTable = new JMenuItem("Show Table");
        showProgramsTable = new JMenuItem("Show Table");

    }

}
