package View;

import javax.swing.*;
import java.awt.*;

public class MainGui {
    private static JFrame frame;
    private static JMenuBar menuBar;
    private static JTable programsTable;
    private static JTable channelsTable;

    public void showGUI() {
        frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        initMenu();
        frame.setJMenuBar(menuBar);

        if (channelsTable == null) {
            channelsTable = new JTable(new Object[][]{{"No channels", ""}}, new String[]{"Channel", "Tagline"});
        }
        if (programsTable == null) {
            programsTable = new JTable(new Object[][]{{"No programs", ""}}, new String[]{"Program", "Tagline"});

        }
        frame.getContentPane().add(new JScrollPane(channelsTable), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    private static void initMenu() {
        menuBar = new JMenuBar();

        JMenu channelsMenu = new JMenu("Channels");
        JMenu programsMenu = new JMenu("Programs");

        JMenuItem showChannelsTable = new JMenuItem("Show Table");
        JMenuItem showProgramsTable = new JMenuItem("Show Table");

        showChannelsTable.addActionListener(e -> showTable(channelsTable));
        showProgramsTable.addActionListener(e -> showTable(programsTable));

        channelsMenu.add(showChannelsTable);
        programsMenu.add(showProgramsTable);

        menuBar.add(channelsMenu);
        menuBar.add(programsMenu);
    }

    private static void showTable(JTable table) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setProgramsTable(JTable programsTable) {
        MainGui.programsTable = programsTable;
    }

    public JTable getProgramsTable() {
        return programsTable;
    }

    public JTable getChannelsTable() {
        return channelsTable;
    }

    public void setChannelsTable(JTable channelsTable) {
        MainGui.channelsTable = channelsTable;
    }

}
