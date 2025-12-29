package View;

import javax.swing.*;

public class MainGui {
    private static JMenuBar menuBar;
    private JTable programsTable;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainGui::showGUI);
    }

    private static void showGUI() {
        JFrame frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        initMenu();
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    private static void initMenu() {
        menuBar = new JMenuBar();

        JMenu channelsMenu = new JMenu("Channels");
        JMenu programsMenu = new JMenu("Programs");

        JMenuItem showChannelsTable = new JMenuItem("Show Table");
        JMenuItem showProgramsTable = new JMenuItem("Show Table");

        channelsMenu.add(showChannelsTable);
        programsMenu.add(showProgramsTable);

        menuBar.add(channelsMenu);
        menuBar.add(programsMenu);
    }

    private void showChannelsTable() {

        Object[][] channelData = {{"FM-radio", "14.00"}, {"P3", "13.55"}};
        String[] columnNames = {"Channel", "Time"};
        JTable channelsTable = new JTable(channelData, columnNames);

    }

    private void showProgramsTable() {

    }

}
