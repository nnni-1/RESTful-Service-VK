package ru.alexeev.restfulvkusers.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRequest {
    private long userId;
    private long groupId;
}
