package view;

import javax.swing.*;
import java.awt.*;

public class DetailsPanel extends JPanel {
    private final JLabel imageLabel = new JLabel();
    private final JTextArea descriptionsArea = new JTextArea();

    public DetailsPanel() {
        super(new BorderLayout(8, 8));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        descriptionsArea.setLineWrap(true);
        descriptionsArea.setEditable(false);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(imageLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(descriptionsArea), BorderLayout.CENTER);
    }
}
