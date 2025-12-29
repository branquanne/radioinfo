package Model;

import Model.domain.Channel;
import Model.domain.Program;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {
  List<Channel> channels = new ArrayList<>();

  public ApiClient() {
  }

  public void returnInfo() {
  }

  private HttpResponse fetchChannels() {
    try {
      URI uri = URI.create("https://api.sr.se/api/v2/channels?format=json&indent=true");
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response;

    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }

  }

  private HttpResponse fetchPrograms() {
    try {
      URI uri = URI.create("https://api.sr.se/api/v2/scheduledepisodes?format=json&indent=true");
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      return response;

    } catch (InterruptedException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Channel> getChannels() {
    return channels;
  }

  public List<Program> getPrograms(Channel channel) {
    return channel.getPrograms();
  }
}
