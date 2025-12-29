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

    public List<Channel> fetchChannels() {
        try {
            URI uri = URI.create("https://api.sr.se/api/v2/channels?format=json&indent=true");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Parser p = new Parser(response);
            return p.parseChannels();

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Program> fetchPrograms() {
        try {
            URI uri = URI.create("https://api.sr.se/api/v2/scheduledepisodes?format=json&indent=true");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Parser p = new Parser(response);
            return p.parsePrograms();

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
