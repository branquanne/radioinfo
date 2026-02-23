package controller;

import model.Channel;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class RefreshData {
  private static final Logger LOGGER = Logger.getLogger(RefreshData.class.getName());

  private final ScheduledExecutorService scheduler;
  private final ProgramController programController;
  private final Supplier<List<Channel>> channelsProvider;

  public RefreshData(ProgramController programController, Supplier<List<Channel>> channelsProvider) {
    this.programController = programController;
    this.channelsProvider = channelsProvider;
    long periodSeconds = 3600L;
    this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread t = new Thread(r, "refresh-scheduler");
      t.setDaemon(true);
      return t;
    });
    scheduler.scheduleAtFixedRate(this::refresh, periodSeconds, periodSeconds, TimeUnit.SECONDS);
  }

  private void refresh() {
    try {
      List<Channel> channels = channelsProvider.get();
      if (channels == null || channels.isEmpty()) {
        return;
      }

      for (Channel ch : channels) {
        try {
          if (ch != null && ch.getPrograms() != null) {
            programController.loadProgramsForChannelAsync(ch);
          }
        } catch (Exception e) {
          LOGGER.warning("Failed to refresh channels!");
        }
      }
    } catch (Exception e) {
      LOGGER.warning("Scheduled refresh failed");
    }
  }

  public void manualRefresh() {
    scheduler.execute(this::refresh);
  }

  public void shutdown() {
    scheduler.shutdownNow();
  }
}
