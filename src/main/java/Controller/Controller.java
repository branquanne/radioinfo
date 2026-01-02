package Controller;

import Model.ApiClient;
import Model.domain.Channel;
import View.MainGui;

import javax.swing.*;
import java.util.List;

public class Controller {

    public Controller() {
        SwingUtilities.invokeLater(this::buildGUI);
    }

    private void buildGUI() {
        MainGui mainGui = new MainGui();
        mainGui.setChannelsTable(populateChannelsTable());
        mainGui.showGUI();


    }

    private JTable populateChannelsTable() {
        ApiClient apiClient = new ApiClient();
        List<Channel> channels = apiClient.fetchData();

        String[] columnNames = {"Channel", "Description"};
        Object[][] data = new Object[channels.size()][2];
        for (int i = 0; i < channels.size(); i++) {
            Channel ch = channels.get(i);
            data[i][0] = ch.getChannelName();
            data[i][1] = ch.getTagline();
        }

        return new JTable(data, columnNames);
    }


}
