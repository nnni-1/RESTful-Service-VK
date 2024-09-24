package ru.alexeev.restfulvkusers;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.alexeev.dto.RequestDto;
import ru.alexeev.dto.ResponseDto;
import ru.alexeev.service.UserService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceTests {

    private final String token = "d7535714d7535714d7535714f7d44d9307dd753d7535714b1a3d395ee76c8f468fceb3e";
    final long rightUserId = 269866241;
    final long rightGroupId = 168131127;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnUserInfo_whenValidRequest() throws Exception {
        // Создаем объект запроса
        var userRequest = new RequestDto();
        userRequest.setUser_id(rightUserId);
        userRequest.setGroup_id(rightGroupId);

        // Преобразуем объект в JSON строку
        var requestJson = objectMapper.writeValueAsString(userRequest);

        // Выполняем POST запрос и проверяем результат
        mockMvc.perform(post("/api/v1/user-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("vk_service_token", token)
                        .content(requestJson)) // Передаем JSON запрос в теле
                .andExpect(status().isOk()) // Ожидаем статус 200
                .andExpect((jsonPath("$.firstName").value("Nikita")))// Проверяем поле "firstName"
                .andExpect(jsonPath("$.member").value(true)); // Проверяем поле "isMember"
    }

    @Test
    public void shouldReturnBadRequest_whenInvalidRequest() throws Exception {
        // Создаем некорректный объект запроса
        var invalidRequest = new RequestDto();
        // Не заполняем обязательные поля userId и groupId

        var requestJson = objectMapper.writeValueAsString(invalidRequest);

        // Выполняем POST запрос с заголовком vk_service_token
        mockMvc.perform(post("/api/v1/user-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("vk_service_token", token) // Добавляем заголовок
                        .content(requestJson))
                .andExpect(status().isBadRequest()); // Проверяем, что сервер вернет статус 400
    }
}

