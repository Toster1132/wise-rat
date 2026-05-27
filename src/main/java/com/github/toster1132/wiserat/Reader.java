package com.github.toster1132.wiserat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Reader {
    private final String[] args;
    private final InputStream in;

    public Reader(String[] args, InputStream in) {
        this.args = args;
        this.in = in;
    }

    public String read() throws IOException, InterruptedException {
        if (args.length > 0) {
            return String.join(" ", args);
        }

        if (in.available() > 0) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString().trim();
            }
        }
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://uselessfacts.jsph.pl/api/v2/facts/today?language=en"))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return parseUselessFact(response.body());
        } catch (ConnectException e) {
            return "The wise rat doesn't seem so wise when there's no internet connection";
        }

    }

    private String parseUselessFact(String json) {
        int start = json.indexOf("\"text\":\"") + 8;
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
