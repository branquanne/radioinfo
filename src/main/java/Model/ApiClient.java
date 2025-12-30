package Model;

import Model.domain.Channel;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {
    List<Channel> channels = new ArrayList<>();

    private void fetchChannels() {
        try {
            URI uri = URI.create("https://api.sr.se/api/v2/channels?format=json&indent=true&pagination=false");
            Parser p = createHttpClient(uri);
            this.channels = p.parseChannels();

        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void fetchPrograms(List<Channel> channels) {
        for (Channel ch : channels) {
            try {
                URI uri = URI.create("https://api.sr.se/api/v2/scheduledepisodes?channelid=" + ch.getChannelId() + "&format=json&indent=true&pagination=false");
                Parser p = createHttpClient(uri);
                ch.setPrograms(p.parsePrograms());

            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Parser createHttpClient(URI uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new Parser(response);
    }

    public List<Channel> fetchData() {
        fetchChannels();
        fetchPrograms(channels);

        return channels;
    }
}
