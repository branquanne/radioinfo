package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private final JFrame frame = new JFrame("Radio Info");
    private final ChannelsPanel channelsPanel = new ChannelsPanel();
    private final ProgramsPanel programsPanel = new ProgramsPanel();
    private final DetailsPanel detailsPanel = new DetailsPanel();
    private final WelcomePanel welcomePanel = new WelcomePanel();
    private final AppMenuBar appMenuBar;

    public MainFrame() {
        appMenuBar = new AppMenuBar(this::showChannelsTable);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setJMenuBar(appMenuBar.getMenuBar());
        frame.setLocationRelativeTo(null);

        frame.getContentPane().add(welcomePanel, BorderLayout.CENTER);
        welcomePanel.setBrowseButton(this::showChannelsTable);
    }

    public void show() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
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
