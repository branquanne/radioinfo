package controller;

import model.ApiClient;
import model.Channel;
import model.Program;
import view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProgramController {
    private MainFrame mainFrame;
    private ApiClient apiClient = new ApiClient();

    public ProgramController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    private void loadProgramsForChannelAsynchronously(Channel channel) {
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

                    if (channel == currentlyShowingChannel) {
                        mainFrame.setProgramsTableModel(createProgramsModel(channel));
                        mainFrame.showProgramsTable();
                    }
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
        String[] columnNames = {"Program", "Start", "End"};
        return new DefaultTableModel(columnNames, 0);
    }


    private DefaultTableModel createProgramsModel(Channel channel) {
        String[] columnNames = {"Program", "Start", "End"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        if (channel == null || channel.getPrograms() == null) {
            return model;
        }
        channel
                .getPrograms()
                .forEach(program -> model.addRow(
                        new Object[]{
                                program.getProgramTitle(),
                                program.getStartTimeString(),
                                program.getEndTimeString(),
                        }));

        return model;
    }

    private DefaultTableModel createLoadingModel() {
        String[] columnNames = {"Program", "Start", "End"};
        DefaultTableModel loadingModel = new DefaultTableModel(columnNames, 0);
        loadingModel.addRow(new Object[]{"Loading...", "", ""});
        return loadingModel;
    }


}
