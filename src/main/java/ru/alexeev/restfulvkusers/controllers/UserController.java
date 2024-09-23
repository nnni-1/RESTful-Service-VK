package ru.alexeev.restfulvkusers.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import ru.alexeev.restfulvkusers.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService service;

}
