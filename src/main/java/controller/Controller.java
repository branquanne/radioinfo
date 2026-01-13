package controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.ApiClient;
import model.domain.Channel;
import view.Gui;

public class Controller {

    private List<Channel> channels;
    private Gui gui;

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
                ApiClient apiClient = new ApiClient();
                return apiClient.fetchData();
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

    private DefaultTableModel createChannelsModel(List<Channel> channels) {
        String[] columnNames = { "Channel", "Description" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Channel ch : channels) {
            model.addRow(new Object[] { ch.getChannelName(), ch.getTagline() });
        }

        return model;
    }

    private DefaultTableModel createProgramsModel(Channel channel) {
        String[] columnNames = { "Program", "Start", "End" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        if (channel == null || channel.getPrograms() == null) {
            return model;
        }
        channel
            .getPrograms()
            .forEach(program ->
                model.addRow(
                    new Object[] {
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
