package ru.inno.final_examination.api;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;

@Component
public class XClientOkHttp {
    private final OkHttpClient client;

    public XClientOkHttp() {
        client = new OkHttpClient().newBuilder().addNetworkInterceptor(new MyInterceptor()).build();
    }

    public OkHttpClient getClient() {
        return client;
    }
}