package Controller;

import Model.ApiClient;
import Model.Domain.Channel;
import View.Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Controller {
    private List<Channel> channels;
    private ApiClient apiClient;
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
        SwingWorker<List<Channel>, Void> worker = new SwingWorker<List<Channel>, Void>() {
            @Override
            protected List<Channel> doInBackground() throws Exception {
                apiClient = new ApiClient();
                return apiClient.fetchData();
            }

            @Override
            protected void done() {
                try {
                    channels = get();
                    DefaultTableModel model = createChannelsModel(channels);
                    gui.setChannelsTableModel(model);
                    gui.updateChannelsMenu(channels, selectedChannel -> {
                        DefaultTableModel programsModel = createProgramsModel(selectedChannel);
                        gui.setProgramsTableModel(programsModel);
                        gui.showProgramsTable();
                    });
                    gui.showChannelsTable();
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        worker.execute();
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
        channel.getPrograms().forEach(program -> model
                .addRow(new Object[]{program.getProgramTitle(), program.getStartTime(), program.getEndTime()}));

        return model;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Controller());
    }

}
