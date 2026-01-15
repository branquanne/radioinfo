package view;

import model.Channel;

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
    private JPanel detailsPanel;
    private JLabel detailsLabel;
    private JTextArea detailsDescriptionArea;

    public void show() {
        frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
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
        JMenuItem showChannels = new JMenuItem("Show Channels");
        showChannels.addActionListener(e -> showChannelsTable());
        viewMenu.add(showChannels);
        menuBar.add(channelsMenu);
        menuBar.add(viewMenu);
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



        menuBar.revalidate();
        menuBar.repaint();
    }

}
