package controller;

import model.ApiClient;
import model.Channel;
import model.ImageLoader;
import view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ChannelController {
  private MainFrame mainFrame;
  private ProgramController programController;
  private ApiClient apiClient = new ApiClient();
  private List<Channel> channels;

  public ChannelController(MainFrame mainFrame, ProgramController programController) {
    this.mainFrame = mainFrame;
    this.programController = programController;
  }

  public void start() {
    loadChannelsAsync();
  }

  private void loadChannelsAsync() {
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
          mainFrame.setChannelsTableModel(model);

          mainFrame.setChannelsTableRowHeight(36);
          mainFrame.setChannelsTableColumnWidth(new int[] { 32, 240, 480 });

          mainFrame.updateMenu(channels, selectedChannel -> {
            programController.loadProgramsForChannelAsync(selectedChannel);
          });
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

  private DefaultTableModel createChannelsModel(List<Channel> channels) {
    String[] columnNames = { "Thumbnail", "Channel", "Description" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0) {

      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }

      @Override
      public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
          return Icon.class;
        }
        return String.class;
      }
    };

    ImageLoader loader = new ImageLoader();
    for (Channel ch : channels) {
      ImageIcon icon = null;
      try {
        icon = loader.loadAndScaleImage(ch.getThumbnailLink(), 32, 32);
      } catch (Exception e) {
        throw new RuntimeException("Failed to load image");
      }
      model.addRow(new Object[] { icon, ch.getChannelName(), ch.getTagline() });
    }

    return model;
  }

  public List<Channel> getChannels() {
    return channels;
  }
}
