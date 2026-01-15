package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ChannelsPanel extends JPanel {
    private final JTable channelsTable = new JTable();

    public ChannelsPanel() {
        super(new BorderLayout());
        add(new JScrollPane(channelsTable), BorderLayout.CENTER);
    }

    public void setModel(DefaultTableModel model) {
        SwingUtilities.invokeLater(() -> channelsTable.setModel(model));
    }

    public void setTableRowHeight(int height) {
        SwingUtilities.invokeLater(() -> channelsTable.setRowHeight(height));
    }

    public void setTableColumnWidths(int[] widths) {
        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < widths.length; i++) {
                channelsTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            }
        });
    }

    public JTable getTable() {
        return channelsTable;
    }
}
