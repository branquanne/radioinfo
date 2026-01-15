package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainFrame {
    private final JFrame frame = new JFrame("Radio Info");
    private final ChannelsPanel channelsPanel = new ChannelsPanel();
    private final ProgramsPanel programsPanel = new ProgramsPanel();
    private final DetailsPanel detailsPanel = new DetailsPanel();
    private final AppMenuBar appMenuBar;

    public MainFrame(){

    }



    public void show() {
        frame = new JFrame("Radio Info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        initMenu();
        frame.setJMenuBar(appMenuBar);

        channelsTable = new JTable(new DefaultTableModel(new String[]{"Channel", "Description"}, 0));
        programsTable = new JTable(new DefaultTableModel(new String[]{"Program", "Start time", "End time"}, 0));


        frame.getContentPane().add(new JScrollPane(channelsTable), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initMenu() {
        appMenuBar = new JMenuBar();

        channelsMenu = new JMenu("Channels");
        JMenu viewMenu = new JMenu("View");
        JMenuItem showChannels = new JMenuItem("Show Channels");
        showChannels.addActionListener(e -> showChannelsTable());
        viewMenu.add(showChannels);
        appMenuBar.add(channelsMenu);
        appMenuBar.add(viewMenu);
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



        appMenuBar.revalidate();
        appMenuBar.repaint();


}
