package controller;

import model.ApiClient;
import model.domain.Channel;
import view.Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Controller {

    private List<Channel> channels;
    private Gui gui;
    private final ApiClient apiClient = new ApiClient();
    private Channel selectedChannel;

    public Controller() {
        SwingUtilities.invokeLater(this::buildGUI);
    }

    private void buildGUI() {
        gui = new Gui();
        gui.show();

        loadChannelsAsynchronously();
    }

    private void loadChannelsAsynchronously() {
        SwingWorker<List<Channel>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Channel> doInBackground() {
                return apiClient.fetchChannels();
            }

            @Override
            protected void done() {
                try {
                    channels = get();
                    DefaultTableModel model = createChannelsModel(channels);
                    gui.setChannelsTableModel(model);
                    gui.updateChannelsMenu(channels, selectedChannel -> {
                        DefaultTableModel programsModel = createProgramsModel(
                                selectedChannel
                        );
                        gui.setProgramsTableModel(programsModel);
                        gui.showProgramsTable();
                    });
                    gui.showChannelsTable();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    JOptionPane.showMessageDialog(
                            null,
                            "Operation interrupted",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } catch (ExecutionException e) {
                    String message =
                            e.getCause() != null
                                    ? e.getCause().getMessage()
                                    : e.getMessage();
                    JOptionPane.showMessageDialog(
                            null,
                            "Failed to fetch data: " + message,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
    }

    private void loadProgramsForChannelAsynchronously(Channel channel) {
        if (channel == null) {
            gui.setProgramsTableModel(createEmptyProgramsModel());
            gui.showProgramsTable();
            return;
        }

        if (channel.getPrograms() != null && !channel.getPrograms().isEmpty()) {
            gui.setProgramsTableModel(createProgramsModel(channel));
            gui.showProgramsTable();
            return;
        }

        gui.setProgramsTableModel(createLoadingModel());
        gui.showProgramsTable();
    }

    private DefaultTableModel createChannelsModel(List<Channel> channels) {
        String[] columnNames = {"Channel", "Description"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Channel ch : channels) {
            model.addRow(new Object[]{ch.getChannelName(), ch.getTagline()});
        }

        return model;
    }

    private DefaultTableModel createProgramsModel(Channel channel) {
        String[] columnNames = {"Program", "Start", "End"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        if (channel == null || channel.getPrograms() == null) {
            return model;
        }
        channel
                .getPrograms()
                .forEach(program ->
                        model.addRow(
                                new Object[]{
                                        program.getProgramTitle(),
                                        program.getStartTimeString(),
                                        program.getEndTimeString(),
                                }
                        )
                );

        return model;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::new);
    }
}
