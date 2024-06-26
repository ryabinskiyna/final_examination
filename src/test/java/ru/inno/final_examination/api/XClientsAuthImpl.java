package ru.inno.final_examination.api;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.final_examination.model.UserInfo;

import java.io.IOException;
import java.util.Objects;

@Component
public class XClientsAuthImpl implements XClientsAuth {
    @Value("${base_url}")
    private String BASE_URL;
    @Value("${auth_path1}")
    private String AUTH_PATH_1;
    @Value("${auth_path2}")
    private String AUTH_PATH_2;
    @Value("${username_admin}")
    private String username_admin;
    @Value("${password}")
    private String password;
    @Autowired
    private XClientOkHttp client;
    @Autowired
    private Mapper mapper;
    private final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

    public XClientsAuthImpl() {
    }

    @Override
    public UserInfo auth() throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(AUTH_PATH_1).addPathSegment(AUTH_PATH_2).build();
        RequestBody body = RequestBody.create("{\"username\":\"" + username_admin + "\",\"password\":\"" + password + "\"}", APPLICATION_JSON);
        Request request = new Request.Builder().post(body).url(url).build();
        Response response = client.getClient().newCall(request).execute();
        assert response.body() != null;
        return mapper.getMapper().readValue(response.body().string(), UserInfo.class);
    }
}