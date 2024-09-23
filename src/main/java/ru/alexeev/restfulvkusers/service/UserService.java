package ru.alexeev.restfulvkusers.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.alexeev.restfulvkusers.api.ApiRequest;
import ru.alexeev.restfulvkusers.api.ApiResponce;
import ru.alexeev.restfulvkusers.models.UserRequest;
import ru.alexeev.restfulvkusers.models.UserResponse;

@Service

public class UserService {
    @Value("${service.token}")
    private String token;
    @Value("${service.urlString}")
    private String urlString;
    public ApiResponce parse(ApiRequest userRequest) {

    }
}
