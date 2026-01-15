package view;

import javax.swing.*;
import java.awt.*;

public class WelcomePanel extends JPanel {
    private final JButton browseButton = new JButton("Browse channels");

    public WelcomePanel() {
        super(new BorderLayout());
        JLabel title = new JLabel("Welcome to Radio Info", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JLabel subtitle = new JLabel("Select a channel from the menu above or press the browse button below.", SwingConstants.CENTER);
        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.add(title, BorderLayout.NORTH);
        centerPanel.add(subtitle, BorderLayout.SOUTH);

        JPanel southPanel = new JPanel();
        southPanel.add(browseButton);

        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    public void setBrowseButton(Runnable action) {
        browseButton.addActionListener(e -> {
            if (action != null) {
                action.run();
            }
        });
    }
}
