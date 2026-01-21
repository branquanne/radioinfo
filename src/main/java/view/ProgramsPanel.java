package view;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.function.Consumer;

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

  public void setSelectionListener(Consumer<Integer> onSelect) {
    SwingUtilities.invokeLater(() -> {
      ListSelectionListener ls = e -> {
        if (!e.getValueIsAdjusting()) {
          int row = programsTable.getSelectedRow();
          if (row >= 0) {
            onSelect.accept(row);
          }
        }
      };
      programsTable.getSelectionModel().addListSelectionListener(ls);
    });
  }
}
