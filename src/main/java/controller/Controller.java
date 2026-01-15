package controller;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import view.MainFrame;

import javax.swing.*;

public class Controller {
    private MainFrame mainFrame;
    private ProgramController programController;
    private ChannelController channelController;
    private RefreshData refresher;

    public Controller() {
        mainFrame = new MainFrame();

        programController = new ProgramController(mainFrame);
        channelController = new ChannelController(mainFrame, programController);

        channelController.start();
    }


    public static void main(String[] args) {
        FlatMacLightLaf.setup();
        SwingUtilities.invokeLater(Controller::new);
    }
}
