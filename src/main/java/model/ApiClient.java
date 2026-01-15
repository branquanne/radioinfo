package model;

import model.domain.Channel;
import model.domain.Program;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
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

      System.out.println(response.body());

      Parser p = new Parser(response);
      return p.parseChannels();
    } catch (InterruptedException e) {
      throw new RuntimeException("Channel fetch interrupted", e);
    } catch (IOException e) {
      throw new RuntimeException("Failed to fetch channels" + e.getMessage(), e);
    }

  }

  public List<Program> fetchProgramsForChannel(int channelId) {
    try {
      URI uri = URI.create("https://api.sr.se/api/v2/scheduledepisodes?channelid=" + channelId
          + "&format=json&pagination=false");
      HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      Parser p = new Parser(response);
      return p.parsePrograms();
    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }

  }
}
