package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProgramsPanel extends JPanel {
    private final JTable programsTable = new JTable();

    public ProgramsPanel() {
        super(new BorderLayout());
        add(new JScrollPane(programsTable), BorderLayout.CENTER);
    }

    public void setModel(DefaultTableModel model) {
        SwingUtilities.invokeLater(() -> programsTable.setModel(model));
    }

    public JTable getTable() {
        return programsTable;
    }

}
