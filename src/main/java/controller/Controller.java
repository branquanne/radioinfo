package controller;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import model.ApiClient;
import model.domain.Channel;
import model.domain.Program;
import view.Gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Controller {

  private List<Channel> channels;
  private Gui gui;
  private final ApiClient apiClient = new ApiClient();
  private Channel currentlyShowingChannel;

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
        return apiClient.fetchChannels();
      }

      @Override
      protected void done() {
        try {
          channels = get();
          DefaultTableModel model = createChannelsModel(channels);
          gui.setChannelsTableModel(model);
          gui.updateChannelsMenu(channels, selectedChannel -> {
            currentlyShowingChannel = selectedChannel;
            loadProgramsForChannelAsynchronously(selectedChannel);
          });
          gui.showChannelsTable();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          JOptionPane.showMessageDialog(
              null,
              "Operation interrupted",
              "Error",
              JOptionPane.ERROR_MESSAGE);
        } catch (ExecutionException e) {
          String message = e.getCause() != null
              ? e.getCause().getMessage()
              : e.getMessage();
          JOptionPane.showMessageDialog(
              null,
              "Failed to fetch data: " + message,
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    worker.execute();
  }

  private void loadProgramsForChannelAsynchronously(Channel channel) {
    if (channel == null) {
      gui.setProgramsTableModel(createEmptyProgramsModel());
      gui.showProgramsTable();
      return;
    }

    if (channel.getPrograms() != null && !channel.getPrograms().isEmpty()) {
      gui.setProgramsTableModel(createProgramsModel(channel));
      gui.showProgramsTable();
      return;
    }

    gui.setProgramsTableModel(createLoadingModel());
    gui.showProgramsTable();

    SwingWorker<List<Program>, Void> worker = new SwingWorker<>() {
      @Override
      protected List<Program> doInBackground() {
        return apiClient.fetchProgramsForChannel(channel.getChannelId());
      }

      @Override
      protected void done() {
        try {
          List<Program> programs = get();
          channel.setPrograms(programs);

          if (channel == currentlyShowingChannel) {
            gui.setProgramsTableModel(createProgramsModel(channel));
            gui.showProgramsTable();
          }
        } catch (ExecutionException e) {
          JOptionPane.showMessageDialog(null, "Failed to fetch programs: " + e.getCause(), "Error",
              JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          JOptionPane.showMessageDialog(null, "Operation interrupted", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    };
    worker.execute();
  }

  private DefaultTableModel createLoadingModel() {
    String[] columnNames = { "Program", "Start", "End" };
    DefaultTableModel loadingModel = new DefaultTableModel(columnNames, 0);
    loadingModel.addRow(new Object[] { "Loading...", "", "" });
    return loadingModel;
  }

  private DefaultTableModel createEmptyProgramsModel() {
    String[] columnNames = { "Program", "Start", "End" };
    return new DefaultTableModel(columnNames, 0);
  }

  private DefaultTableModel createChannelsModel(List<Channel> channels) {
    String[] columnNames = { "Thumbnail", "Channel", "Description" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
      @Override
      public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
          return Icon.class;
        }
        return String.class;
      }
    };
    for (Channel ch : channels) {
      ImageIcon icon = null;
      if (ch.getThumbnailLink() != null && !ch.getThumbnailLink().isBlank()) {
        try {
          BufferedImage image = ImageIO.read(URI.create(ch.getThumbnailLink()).toURL());
          if (image != null) {
            icon = new ImageIcon(image);
          }
        } catch (MalformedURLException e) {
          throw new RuntimeException(e);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      model.addRow(new Object[] { icon, ch.getChannelName(), ch.getTagline() });
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
        .forEach(program -> model.addRow(
            new Object[] {
                program.getProgramTitle(),
                program.getStartTimeString(),
                program.getEndTimeString(),
            }));

    return model;
  }

  public static void main(String[] args) {
    FlatMacLightLaf.setup();
    SwingUtilities.invokeLater(Controller::new);
  }
}
