package org.jsanchez.recipe.application.http;

import com.google.gson.GsonBuilder;
import lombok.Builder;
import lombok.Getter;
import org.apache.hc.core5.http.ContentType;
import spark.utils.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.fail;

@Builder
public class ApiTestUtils {

    private String host;
    private String method;
    private String path;
    private String requestBody;
    private Type responseType;

    public TestResponse request() {
        try {
            URL url = new URL(host + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", ContentType.APPLICATION_JSON.toString());
            connection.setRequestProperty("Accept", ContentType.APPLICATION_JSON.toString());
            connection.setRequestMethod(method);

            if (Objects.nonNull(requestBody)) {
                connection.setDoOutput(true);
                connection.getOutputStream().write(
                        requestBody.getBytes(StandardCharsets.UTF_8),
                        0,
                        requestBody.getBytes(StandardCharsets.UTF_8).length
                );
            }
            connection.connect();
            int responseCode = connection.getResponseCode();
            String body = responseCode < 400 ? IOUtils.toString(connection.getInputStream()) : IOUtils.toString(connection.getErrorStream());
            return new TestResponse(responseCode, body, responseType);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    @Getter
    public static class TestResponse<T> {
        private T body;
        private int status;

        public TestResponse(int status, String body, Type type) {
            this.status = status;
            this.body = new GsonBuilder().create().fromJson(body, type);
        }
    }
}
