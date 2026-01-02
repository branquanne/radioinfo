package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private void initMenu() {
        menuBar = new JMenuBar();

        JMenu channelsMenu = new JMenu("Channels");
        JMenu viewMenu = new JMenu("View");

        JMenuItem showChannels = new JMenuItem("Show Table");
        JMenuItem showPrograms = new JMenuItem("Show Table");

        showChannels.addActionListener(e -> showChannelsTable());
        showPrograms.addActionListener(e -> showProgramsTable());

        viewMenu.add(showPrograms);
        viewMenu.add(showChannels);
        
        menuBar.add(channelsMenu);
        menuBar.add(viewMenu);
    }

    public void setProgramsTableModel(DefaultTableModel model) {
        SwingUtilities.invokeLater(() -> programsTable.setModel(model));
    }

    public void setChannelsTableModel(DefaultTableModel model) {
        SwingUtilities.invokeLater(() -> channelsTable.setModel(model));
    }

    public void showChannelsTable() {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new JScrollPane(channelsTable), BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

    }

    public void showProgramsTable() {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new JScrollPane(programsTable), BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });
    }

}
