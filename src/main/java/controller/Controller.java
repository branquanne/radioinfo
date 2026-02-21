package controller;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import view.MainFrame;

import javax.swing.*;

public class Controller {

  public Controller() {
    MainFrame mainFrame = new MainFrame();

    ProgramController programController = new ProgramController(mainFrame);
    ChannelController channelController = new ChannelController(mainFrame, programController);

    RefreshData refresher = new RefreshData(
        programController,
        channelController::getChannels
    );
    mainFrame.setRefreshAction(refresher::manualRefresh);

    channelController.start();
    mainFrame.show();
  }

  public static void main(String[] args) {
    FlatMacLightLaf.setup();
    SwingUtilities.invokeLater(Controller::new);
  }
}
