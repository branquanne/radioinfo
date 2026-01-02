package Controller;

import Model.ApiClient;
import Model.domain.Channel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

public class Controller {

    public Controller() {
        buildGUI();
    }

    private void buildGUI() {
        //MainGui mainGui = new MainGui();
        //mainGui.showGUI();

        ApiClient apiClient = new ApiClient();
        List<Channel> channels = apiClient.fetchData();

        Channel[] channelsAndPrograms = new Channel[channels.size()];
        for (int i = 0; i < channels.size(); i++) {
            channelsAndPrograms[i] = channels.get(i);
        }

        for (Channel ch : channels) {
            System.out.println("Ch name: " + ch.getChannelName());
            System.out.println("Tagline: " + ch.getTagline() + "\n");
        }

        // For the table to work i need to store the api data in arrays so that i can initialize the table right away
        // so that the updater only needs to fetch a new table

        TableModel model = new TableModel() {
        }
        JTable table = new JTable(channelsAndPrograms);

        mainGui.initTables(table);

    }

    public void getTableFromApi() {
        ApiClient apiClient = new ApiClient();
        List<Channel> channels = apiClient.fetchData();

        JTable table = new JTable();

    }


}
