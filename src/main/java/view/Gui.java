package view;

import model.domain.Channel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Gui {
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenu channelsMenu;
    private JTable programsTable;
    private JTable channelsTable;

    public void show() {
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
        JMenu viewMenu = new JMenu("view");


        JMenuItem showChannels = new JMenuItem("Show Channels");
        JMenuItem showPrograms = new JMenuItem("Show Programs");

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
        if (channels == null || channels.isEmpty()) {
            JMenuItem empty = new JMenuItem("No channels");
            empty.setEnabled(false);
            channelsMenu.add(empty);
        } else {
            Map<String, List<Channel>> groups = new LinkedHashMap<>();
            for (Channel ch : channels) {
                String name = ch.getChannelName() == null ? "" : ch.getChannelName();
                String group = name.contains(" ") ? name.split(" ", 2)[0] : name;
                groups.computeIfAbsent(group, k -> new ArrayList<>()).add(ch);
            }

            for (Map.Entry<String, List<Channel>> entry : groups.entrySet()) {
                List<Channel> groupsOfChannels = entry.getValue();

                if (groupsOfChannels.size() == 1) {
                    Channel ch = groupsOfChannels.getFirst();
                    JMenuItem channelItem = new JMenuItem((ch.getChannelName()));
                    channelItem.addActionListener(e -> onSelect.accept(ch));
                    channelsMenu.add(channelItem);
                } else {
                    JMenu groupMenu = new JMenu(entry.getKey());
                    for (Channel ch : groupsOfChannels) {
                        JMenuItem channelItem = new JMenuItem(ch.getChannelName());
                        channelItem.addActionListener(e -> onSelect.accept(ch));
                        groupMenu.add(channelItem);
                    }
                    channelsMenu.add(groupMenu);
                }
            }
        }

        menuBar.revalidate();
        menuBar.repaint();
    }
}
