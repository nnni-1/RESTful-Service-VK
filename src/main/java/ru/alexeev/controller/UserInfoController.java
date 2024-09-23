package ru.alexeev.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.alexeev.dto.RequestDto;
import ru.alexeev.dto.ResponseDto;
import ru.alexeev.service.UserService;

/**
 * <br>REST-контроллер для обработки запросов информации о пользователях
 * <br>Предоставляет конечную точку для получения информации о пользователе и его членстве в группе
 */
@RestController
@RequestMapping("/api/v1/user-info")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService svc;

    /**
     * <br>Обрабатывает POST-запросы для получения информации о пользователе
     *
     * @param token      токен доступа VK API, передаваемый в заголовке запроса
     * @param requestDto объект {@link RequestDto}, содержащий идентификаторы пользователя и группы
     * @return объект {@link ResponseDto} с информацией о пользователе и его членстве в группе
     */
    @PostMapping
    public ResponseDto getUserInfo(
            @RequestHeader("vk_service_token") String token,
            @RequestBody RequestDto requestDto) {

        return svc.getUserInfo(requestDto, token);
    }
}