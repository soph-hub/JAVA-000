package org.geekbang.homework.client;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientTest {

    private static final String BASE_URL = "http://localhost:8801/";

    public static void main(String[] args) {
        HttpGet httpGet = new HttpGet(BASE_URL);
        try (
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(httpGet)){
            HttpEntity entity = response.getEntity();
            System.out.println(String.format("响应状态码：%s, 返回内容：%s", response.getStatusLine(), EntityUtils.toString(entity)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}