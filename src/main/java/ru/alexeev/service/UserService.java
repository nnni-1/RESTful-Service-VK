package ru.alexeev.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.alexeev.dto.RequestDto;
import ru.alexeev.dto.ResponseDto;

import java.util.List;
import java.util.Map;

/**
 * <br>Сервисный класс для обработки логики получения информации о пользователе и его членстве в группе
 * <br>Использует {@link WebClient} для взаимодействия с VK API
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "response")
public class UserService {

    /**
     * Версия VK API, используемая при запросах
     */
    private static final String version = "5.199";

    private final WebClient vkWebClient;

    /**
     * Получает информацию о пользователе и его членстве в группе VK
     *
     * @param requestDto объект {@link RequestDto}, содержащий идентификаторы пользователя и группы
     * @param token      токен доступа VK API
     * @return объект {@link ResponseDto} с информацией о пользователе и его членстве в группе
     * @throws Exception если возникает ошибка при получении информации
     */
    @SneakyThrows
    @CachePut
    public ResponseDto getUserInfo(@Valid RequestDto requestDto, String token) {
        try {
            // Получаем информацию о пользователе
             var userInfoResponse = vkWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/users.get")
                            .queryParam("access_token", token)
                            .queryParam("user_id", requestDto.getUser_id())
                            .queryParam("v", version)
                            .build())
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block();

            if (userInfoResponse == null || !userInfoResponse.containsKey("response")) {
                throw new Exception("Не удалось получить информацию о пользователе");
            }

            var responseList = (List<Map<String, Object>>) userInfoResponse.get("response");
            if (responseList.isEmpty()) {
                throw new Exception("Пользователь не найден");
            }

            var userInfo = responseList.get(0);

            // Получаем информацию о членстве в группе
            var groupMemberResponse = vkWebClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/groups.isMember")
                            .queryParam("access_token", token)
                            .queryParam("group_id", requestDto.getGroup_id())
                            .queryParam("user_id", requestDto.getUser_id())
                            .queryParam("v", version)
                            .build())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (groupMemberResponse == null || !groupMemberResponse.containsKey("response")) {
                throw new Exception("Не удалось проверить членство в группе");
            }

            var isMember = groupMemberResponse.get("response").equals(1);

            // Заполняем DTO для ответа
            var responseDto = new ResponseDto();
            responseDto.setFirstName((String) userInfo.get("first_name"));
            responseDto.setLastName((String) userInfo.get("last_name"));
            responseDto.setMember(isMember);

            return responseDto;
        } catch (Exception e) {
            throw new Exception("Ошибка при получении информации: " + e.getMessage());
        }
    }
}