package Controller;

import java.net.http.HttpResponse;

import View.MainGui;

public class Controller {

  public Controller() {
    buildGUI();
  }

  private void buildGUI() {
    MainGui mainGui = new MainGui();
    mainGui.showGUI();
  }

  private void fetchData() {
  }

}
