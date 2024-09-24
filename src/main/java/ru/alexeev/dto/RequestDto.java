package ru.alexeev.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * <br>DTO класс, представляющий запрос для получения информации о пользователе
 * <br>Содержит идентификаторы пользователя и группы
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    /**
     * Идентификатор пользователя VK
     */
    @Min(value = -1, message = "неверный user_id")
    @NotBlank(message = "наличие user_id обязательно в запросе")
    private long user_id;

    /**
     * Идентификатор группы VK
     */
    @Min(value = 0, message = "неверный group_id")
    @NotBlank(message = "наличие group_id обязательно в запросе")
    private long group_id;
}
