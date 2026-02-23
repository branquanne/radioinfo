package model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiClient {
  private final HttpClient client;

  public ApiClient() {
    this.client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).connectTimeout(Duration.ofSeconds(10))
        .build();
  }

  public List<Channel> fetchChannels() {
    try {
      URI uri = URI.create("https://api.sr.se/api/v2/channels?format=json&pagination=false");
      HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      Parser p = new Parser(response);
      return p.parseChannels();
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Channel fetch interrupted", e);
    } catch (IOException e) {
      throw new RuntimeException("Failed to fetch channels: " + e.getMessage(), e);
    }

  }

  public List<Program> fetchProgramsForChannel(int channelId, LocalDate referenceDate) {
    try {
      LocalDate ref = referenceDate == null ? LocalDate.now() : referenceDate;
      List<LocalDate> dates = Arrays.asList(ref.minusDays(1), ref, ref.plusDays(1));
      List<Program> combined = new ArrayList<>();

      for (LocalDate d : dates) {
        URI uri = URI.create("https://api.sr.se/api/v2/scheduledepisodes?channelid=" + channelId
            + "&format=json&pagination=false&date=" + d.toString());
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Parser p = new Parser(response);
        List<Program> programs = p.parsePrograms();
        if (programs != null && !programs.isEmpty()) {
          combined.addAll(programs);
        }
      }
      return combined;
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException("Program fetch interrupted");
    } catch (IOException e) {
      throw new RuntimeException("Failed to fetch programs!");
    }

  }
}
