package Controller;

import Model.ApiClient;
import Model.domain.Channel;
import Model.domain.Program;

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

        for (Channel ch : channels) {
            System.out.println("Channel name (id): " + ch.getChannelName() + "(" + ch.getChannelId() + ")");
            for (Program pr : ch.getPrograms()) {
                System.out.println("\tProgram name (id): " + pr.getProgramTitle() + "(" + pr.getProgramId() + ")");
                System.out.println("\tImage link: " + pr.getThumbnailLink());
            }
            System.out.println("\n\n");
        }

    }


}
