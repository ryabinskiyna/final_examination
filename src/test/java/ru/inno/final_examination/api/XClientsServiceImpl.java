package ru.inno.final_examination.api;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.inno.final_examination.model.Company;
import ru.inno.final_examination.model.Employee;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class XClientsServiceImpl implements XClientsService {
    @Value("${base_url}")
    private String BASE_URL;
    @Value("${company_path1}")
    private String COMPANY_PATH1;
    @Value("${company_path2}")
    private String COMPANY_PATH2;
    @Value("${employee_path}")
    private String EMPLOYEE_PATH;
    @Autowired
    private XClientOkHttp client;
    @Autowired
    private Mapper mapper;
    @Autowired
    private XClientsAuthImpl auth;
    private final MediaType APPLICATION_JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    public List<Company> getAllCompanyList() throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(COMPANY_PATH1).build();
        Request request = new Request.Builder().get().url(url).build();
        Response response = client.getClient().newCall(request).execute();
        assert response.body() != null;
        return mapper.getMapper().readValue(response.body().string(), new TypeReference<List<Company>>() {
        });
    }

    @Override
    public List<Company> getActiveCopmanyList() throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(COMPANY_PATH1).addQueryParameter("active", "true").build();
        Request request = new Request.Builder().get().url(url).build();
        Response response = client.getClient().newCall(request).execute();
        assert response.body() != null;
        return mapper.getMapper().readValue(response.body().string(), new TypeReference<List<Company>>() {
        });
    }

    @Override
    public int addCompany() throws IOException {
//        авторизация
        String userToken = auth.auth().getUserToken();
//        добавление новой компании авторизованным пользователем (админом)
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(COMPANY_PATH1).build();
        String company_name = "Рога и копыта";
        String company_desc = "Копыта и рога";
        RequestBody body = RequestBody.create("{\"name\":\"" + company_name + "\",\"description\":\"" + company_desc + "\"}", APPLICATION_JSON);
        Request request = new Request.Builder().post(body).url(url).addHeader("x-client-token", userToken).build();
        Response response = client.getClient().newCall(request).execute();
        assert response.body() != null;
        Company company = mapper.getMapper().readValue(response.body().string(), Company.class);
        return company.getId();
    }

    @Override
    public int addEmployee(int companyId) throws IOException {
//        авторизация
        String userToken = auth.auth().getUserToken();
//        добавление нового сотрудника авторизованным пользователем (админом)
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(EMPLOYEE_PATH).build();
        String firstName = "Остап";
        String lastName = "Бендер";
        String phone = "1234567890";
        RequestBody body = RequestBody.create("{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName + "\",\"companyId\":" + companyId + ",\"phone\":\"" + phone + "\"}", APPLICATION_JSON);
        Request request = new Request.Builder().post(body).url(url).addHeader("x-client-token", userToken).build();
        Response response = client.getClient().newCall(request).execute();
        assert response.body() != null;
        Employee employee = mapper.getMapper().readValue(response.body().string(), Employee.class);
        return employee.getId();
    }

    @Override
    public List<Employee> getEmployeeList() {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByCompanyId(int companyId) throws IOException {
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(EMPLOYEE_PATH).addQueryParameter("company", String.valueOf(companyId)).build();
        Request request = new Request.Builder().get().url(url).build();
        Response response = client.getClient().newCall(request).execute();
        assert response.body() != null;
        return mapper.getMapper().readValue(response.body().string(), new TypeReference<List<Employee>>() {
        });
    }

    @Override
    public void deleteCompany(int companyId) throws IOException {
//        авторизация
        String userToken = auth.auth().getUserToken();
//        удаление компании
        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL)).newBuilder().addPathSegment(COMPANY_PATH1).addPathSegment(COMPANY_PATH2).addPathSegment(String.valueOf(companyId)).build();
        Request request = new Request.Builder().get().url(url).addHeader("x-client-token", userToken).build();
        client.getClient().newCall(request).execute();
    }
}