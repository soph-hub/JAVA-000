package org.geekbang.homework.client;

import java.io.IOException;
import java.util.Objects;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpTest {

    private static final String BASE_URL = "http://localhost:8801";

    private static final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        Request request = new Request.Builder()
            .url(BASE_URL)
            .build();
        Call call = client.newCall(request);
        try (Response response = call.execute()){
            System.out.println(Objects.requireNonNull(response.body()).string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
