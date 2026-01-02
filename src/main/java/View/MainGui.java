package View;

import Model.domain.Channel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class MainGui {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu channelsMenu;
    private JTable programsTable;
    private JTable channelsTable;

    public void showGUI() {
        frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        initMenu();
        frame.setJMenuBar(menuBar);

        channelsTable = new JTable(new DefaultTableModel(new String[]{"Channel", "Description"}, 0));
        programsTable = new JTable(new DefaultTableModel(new String[]{"Program", "Start time", "End time"}, 0));

        frame.getContentPane().add(new JScrollPane(channelsTable), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initMenu() {
        menuBar = new JMenuBar();

        channelsMenu = new JMenu("Channels");
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

    public void updateChannelsMenu(List<Channel> channels, Consumer<Channel> onSelect) {
        channelsMenu.removeAll();
        if (channels.isEmpty()) {
            JMenuItem empty = new JMenuItem("No channels");
            empty.setEnabled(false);
            channelsMenu.add(empty);
        } else {
            for (Channel ch : channels) {
                JMenuItem item = new JMenuItem(ch.getChannelName());
                item.addActionListener(e -> onSelect.accept(ch));
                channelsMenu.add(item);
            }
        }

        menuBar.revalidate();
        menuBar.repaint();
    }
}
