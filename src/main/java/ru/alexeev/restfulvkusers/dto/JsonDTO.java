package ru.alexeev.restfulvkusers.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class JsonDTO {
    private List<User> response;
}
