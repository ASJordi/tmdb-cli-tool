package dev.asjordi.request;

import dev.asjordi.Props;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class RequestManager {

    private Endpoint endpoint;

    public RequestManager(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    public Optional<HttpResponse<String>> sendRequest() {
        var API_KEY = Props.getInstance().getProperty("API_KEY");
        HttpRequest request = null;

        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(this.endpoint.getUrl()))
                    .version(HttpClient.Version.HTTP_2)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .header("Accept", "application/json")
                    .header("Authorization", API_KEY)
                    .timeout(Duration.of(60, ChronoUnit.SECONDS))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.of(20, ChronoUnit.SECONDS))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(response);
    }

}
