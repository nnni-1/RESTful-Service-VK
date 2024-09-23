package ru.alexeev.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * <br>DTO класс, представляющий запрос для получения информации о пользователе
 * <br>Содержит идентификаторы пользователя и группы
 */
@Getter
@Setter
public class RequestDto {

    /**
     * Идентификатор пользователя VK
     */
    @Min(value = 0, message = "неверный user_id")
    @NotBlank(message = "наличие user_id обязательно в запросе")
    private long user_id;

    /**
     * Идентификатор группы VK
     */
    @Min(value = 0, message = "неверный group_id")
    @NotBlank(message = "наличие group_id обязательно в запросе")
    private long group_id;
}
