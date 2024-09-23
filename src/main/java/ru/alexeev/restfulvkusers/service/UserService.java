package ru.alexeev.restfulvkusers.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;
import ru.alexeev.restfulvkusers.api.ApiRequest;
import ru.alexeev.restfulvkusers.api.ApiResponse;
import ru.alexeev.restfulvkusers.dto.JsonDTO;
import ru.alexeev.restfulvkusers.dto.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
@Service
public class UserService {
    private final BuildProperties buildProperties;
    @Value("${service.token}")
    private String token;
    @Value("${service.apiUrl}")
    private String apiUrl;
    @Value("${service.parameterUserId}")
    private String parameterUserId;
    @Value("${service.version}")
    private String version;
    @Value("${service.parameterGroupId}")
    private String parameterGroupId;

    public UserService(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public ApiResponse parse(ApiRequest userRequest) {
        String fullRequestURL = apiUrl +
                token +
                parameterUserId +
                userRequest.getUserId() +
                parameterGroupId +
                userRequest.getGroupId() +
                version;
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = null;
        StringBuilder builder = new StringBuilder();
        try {
            url = new URL(fullRequestURL);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                builder = new StringBuilder();
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    builder.append(inputLine);
                }
            }
            JsonDTO jsonDTO = objectMapper.readValue(builder.toString(), JsonDTO.class);
            User user = jsonDTO.getResponse().get(0);
            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setFirstName(user.);
//            apiResponse.setLastName(user.getLast_name());

        } catch (IOException e) {
            //TODO обработка исключений в т.ч. неправильного запроса
            System.out.println(e.getMessage());
        }

        return apiResponse;
    }
}
