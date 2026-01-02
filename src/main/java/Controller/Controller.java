package Controller;

import Model.ApiClient;
import Model.domain.Channel;
import View.MainGui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class Controller {
    private List<Channel> channels;
    private ApiClient apiClient;

    public Controller() {
        SwingUtilities.invokeLater(this::buildGUI);
    }

    private void buildGUI() {
        MainGui mainGui = new MainGui();
        mainGui.showGUI();


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
        channel.getPrograms().forEach(program -> model.addRow(new Object[]{program.getProgramTitle(), program.getStartTime(), program.getEndTime()}));

        return model;
    }

}
