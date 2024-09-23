package ru.alexeev.restfulvkusers.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String firstName;
    private String lastName;
    private boolean isMember;
}
