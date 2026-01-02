package View;

import javax.swing.*;

public class MainGui {
    private static JMenuBar menuBar;
    private JTable programsTable;

    public void showGUI() {
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

    public void initTables() {
        programsTable = new JTable();

    }

    public void setProgramsTable(JTable programsTable) {
        this.programsTable = programsTable;
    }

    public JTable getProgramsTable() {
        return programsTable;
    }

}
