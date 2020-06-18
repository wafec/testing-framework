package robtest.stateinterfw.os.cli;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

public abstract class BaseClient {
    private String prefix;

    public BaseClient(String prefix) {
        this.prefix = prefix;
    }

    protected <T> Optional<T> request(Class<T> returnType, String path, Object body, String method) {
        T result = null;
        try {
            URI uri = URI.create(String.format("%s%s", this.prefix, path));
            var httpRequestBuilder = HttpRequest.newBuilder(uri);
            if (returnType != null)
                httpRequestBuilder = httpRequestBuilder.header("Accept", "application/json");
            if (body != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                var bodyString = objectMapper.
                        writerWithDefaultPrettyPrinter()
                        .writeValueAsString(body);
                httpRequestBuilder = httpRequestBuilder.header("Content-Type", "application/json");
                switch (method.toLowerCase()) {
                    case "post":
                        httpRequestBuilder = httpRequestBuilder.POST(HttpRequest.BodyPublishers.ofString(bodyString));
                        break;
                    case "put":
                        httpRequestBuilder = httpRequestBuilder.PUT(HttpRequest.BodyPublishers.ofString(bodyString));
                        break;
                }
            } else {
                switch (method.toLowerCase()) {
                    case "delete":
                        httpRequestBuilder = httpRequestBuilder.DELETE();
                        break;
                }
            }
            var response = HttpClient.newHttpClient()
                    .send(httpRequestBuilder.build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                if (returnType != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    result = objectMapper.readValue(response.body(), returnType);
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return Optional.ofNullable(result);
    }
}
