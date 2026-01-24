package controller;

import model.ApiClient;
import model.Channel;
import model.ImageLoader;
import model.Program;
import view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProgramController {
  private final MainFrame mainFrame;
  private final ApiClient apiClient = new ApiClient();
  private List<Program> displayedPrograms = Collections.emptyList();

  public ProgramController(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
  }

  void loadProgramsForChannelAsync(Channel channel) {
    if (channel == null) {
      mainFrame.setProgramsTableModel(createEmptyProgramsModel());
      mainFrame.showProgramsTable();
      return;
    }

    if (channel.getPrograms() != null && !channel.getPrograms().isEmpty()) {
      mainFrame.setProgramsTableModel(createProgramsModel(channel));
      mainFrame.showProgramsTable();
      return;
    }

    mainFrame.setProgramsTableModel(createLoadingModel());
    mainFrame.showProgramsTable();

    SwingWorker<List<Program>, Void> worker = new SwingWorker<>() {
      @Override
      protected List<Program> doInBackground() {
        return apiClient.fetchProgramsForChannel(channel.getChannelId());
      }

      @Override
      protected void done() {
        try {
          List<Program> programs = get();
          channel.setPrograms(programs);
          mainFrame.setProgramsTableModel(createProgramsModel(channel));
          setSelectedChannel(channel);
          mainFrame.showProgramsTable();
        } catch (ExecutionException e) {
          JOptionPane.showMessageDialog(null, "Failed to fetch programs: " + e.getCause(), "Error",
              JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          JOptionPane.showMessageDialog(null, "Operation interrupted", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    worker.execute();
  }

  private DefaultTableModel createEmptyProgramsModel() {
    String[] columnNames = { "Program", "Start", "End" };
    return new DefaultTableModel(columnNames, 0);
  }

  private DefaultTableModel createProgramsModel(Channel channel) {
    String[] columnNames = { "Program", "Start", "End" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    if (channel == null || channel.getPrograms() == null) {
      return model;
    }

    // Får inte riktigt detta med timmarna att fungera och vet inte riktigt vart jag
    // går fel :/
    LocalDateTime referencePoint = channel.getLastFetched();
    LocalDateTime windowStart = null;
    LocalDateTime windowEnd = null;
    if (referencePoint != null) {
      windowStart = referencePoint.minusHours(12);
      windowEnd = referencePoint.plusHours(12);
    }

    List<Program> filteredPrograms = new ArrayList<>();
    for (Program program : channel.getPrograms()) {
      LocalDateTime start = program.getStartTime();
      if (start == null) {
        continue;
      }
      if (windowStart != null && windowEnd != null) {
        if (start.isBefore(windowStart) || start.isAfter(windowEnd)) {
          continue;
        }
      }
      filteredPrograms.add(program);
      model.addRow(new Object[] {
          program.getProgramTitle(),
          program.getStartTimeString(),
          program.getEndTimeString()
      });
    }
    displayedPrograms = filteredPrograms;
    return model;
  }

  private DefaultTableModel createLoadingModel() {
    String[] columnNames = { "Program", "Start", "End" };
    DefaultTableModel loadingModel = new DefaultTableModel(columnNames, 0);

    loadingModel.addRow(new Object[] { "Loading...", "", "" });
    displayedPrograms = Collections.emptyList();
    return loadingModel;
  }

  private void setSelectedChannel(Channel channel) {
    mainFrame.setProgramsSelectionListener(row -> {
      if (displayedPrograms == null) {
        return;
      }

      Program program = displayedPrograms.get(row);
      ImageLoader loader = new ImageLoader();
      ImageIcon icon;
      try {
        icon = loader.loadAndScaleImage(program.getThumbnailLink(), 256, 256);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      mainFrame.setProgramDetails(icon, program.getDescription());
    });
  }

}
