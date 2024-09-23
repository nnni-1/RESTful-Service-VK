package ru.alexeev.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <br>DTO класс, представляющий ответ на запрос информации о пользователе
 * <br>Содержит личные данные пользователя и информацию о его членстве в группе
 */
@Getter
@Setter
public class ResponseDto {

    /**
     * Фамилия пользователя
     */
    private String lastName;

    /**
     * Имя пользователя
     */
    private String firstName;


    /**
     * <br>Информация о членстве пользователя в группе
     * <br>{@code true} если пользователь является членом группы, {@code false} иначе
     */
    private Boolean member;
}
