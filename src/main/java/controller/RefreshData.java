package controller;

import model.Channel;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class RefreshData {
  private final ScheduledExecutorService scheduler;
  private final ProgramController programController;
  private final Supplier<List<Channel>> channelsProvider;
  private final long periodSeconds;

  public RefreshData(ProgramController programController, Supplier<List<Channel>> channelsProvider) {
    this.programController = programController;
    this.channelsProvider = channelsProvider;
    this.periodSeconds = 3600L;
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
          throw new RuntimeException(e);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public void manualRefresh() {
    scheduler.execute(this::refresh);
  }

  public void shutdown() {
    scheduler.shutdownNow();
  }
}
