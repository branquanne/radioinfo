package Model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class ApiClient {
    List<String> channels;
    List<String> programs;

    public ApiClient() {
    }

    public void returnInfo() {
    }


    private void fetchChannels() {
        try {
            URI uri = URI.create("https://api.sr.se/api/v2/channels?format=json&indent=true");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fetchPrograms() {
        try {
            URI uri = URI.create("https://api.sr.se/api/v2/scheduledepisodes?format=json&indent=true");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
