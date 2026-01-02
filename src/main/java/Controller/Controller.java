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
        mainGui.setChannelsTable(populateChannelsTable());
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


}
