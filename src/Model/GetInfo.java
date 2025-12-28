package Model;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetInfo {

    public GetInfo() {
    }

    public void returnInfo() {
    }


    public void getChannels() {
        try {
            URI uri = URI.create("http://api.sr.se/api/v2/channels");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
