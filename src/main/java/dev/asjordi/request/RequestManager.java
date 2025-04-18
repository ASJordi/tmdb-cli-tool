package dev.asjordi.request;

import dev.asjordi.Props;
import dev.asjordi.logging.LoggerConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequestManager {

    private static final Logger LOGGER = LoggerConfig.getLogger();
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
            LOGGER.log(Level.INFO, () -> String.format("Sending request to %s", this.endpoint.getUrl()));
        } catch (URISyntaxException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.of(20, ChronoUnit.SECONDS))
                .build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                String body = response.body();
                LOGGER.log(Level.SEVERE, () -> String.format("Error: %s", body));
            }

        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        return Optional.ofNullable(response);
    }

}
