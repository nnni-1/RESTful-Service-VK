package ru.alexeev.restfulvkusers.api;

import lombok.Data;

@Data
public class ApiRequest {
    private long userId;
    private long groupId;
}
