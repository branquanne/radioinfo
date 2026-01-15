package view;

import model.Channel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class MainFrame {
    private final JFrame frame = new JFrame("Radio Info");
    private final ChannelsPanel channelsPanel = new ChannelsPanel();
    private final ProgramsPanel programsPanel = new ProgramsPanel();
    private final DetailsPanel detailsPanel = new DetailsPanel();
    private final AppMenuBar appMenuBar;

    public MainFrame() {
        appMenuBar = new AppMenuBar(this::showChannelsTable);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setJMenuBar(appMenuBar);
        frame.setLocationRelativeTo(null);

        WelcomePanel welcomePanel = new WelcomePanel();
        frame.getContentPane().add(welcomePanel, BorderLayout.CENTER);
        welcomePanel.setBrowseButton(this::showChannelsTable);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    public void setRefreshAction(Runnable refreshAction) {
        SwingUtilities.invokeLater(() -> appMenuBar.setRefreshAction(refreshAction));
    }

    public void setChannelsTableModel(DefaultTableModel model) {
        channelsPanel.setModel(model);
    }

    public void setProgramsTableModel(DefaultTableModel model) {
        programsPanel.setModel(model);
    }

    public void setChannelsTableRowHeight(int height) {
        channelsPanel.setTableRowHeight(height);
    }

    public void setChannelsTableColumnWidth(int[] widths) {
        channelsPanel.setTableColumnWidths(widths);
    }

    public void clearDetails() {
        detailsPanel.clear();
    }

    public void setProgramDetails(ImageIcon image, String description) {
        detailsPanel.setProgramDetails(image, description);
    }

    public void setProgramsSelectionListener(Consumer<Integer> onSelect) {
        programsPanel.setSelectionListener(onSelect);
    }

    public void updateMenu(List<Channel> channels, Consumer<Channel> onSelect) {
        SwingUtilities.invokeLater(() -> appMenuBar.updateMenu(channels, onSelect));
    }

    public void showChannelsTable() {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(channelsPanel, BorderLayout.CENTER);
            frame.revalidate();
            frame.repaint();
        });

    }

    public void showProgramsTable() {
        SwingUtilities.invokeLater(() -> {
            frame.getContentPane().removeAll();
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, programsPanel, detailsPanel);
            frame.getContentPane().add(splitPane, BorderLayout.CENTER);
            detailsPanel.clear();
            frame.revalidate();
            frame.repaint();
        });
    }
}
