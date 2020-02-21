package org.faucetmc.network.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static String httpGET(String url) {
        try {
            HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("User-Agent", "Mozilla/5.0");
            StringBuilder builder = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()))) {
                String line;
                while ((line = in.readLine()) != null) {
                    builder.append(line);
                }
            }
            httpClient.disconnect();
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
