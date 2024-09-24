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

//    @Test
//    public void testPostMethod() {
//        String jsonBody = "{\"user_id\":269866241,\"group_id\":168131127}";
//        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
//        headers.put("Content-Type", List.of(MediaType.APPLICATION_JSON));
//        headers.put("vk_access_token", List.of(token));
//
//        Response response = ClientBuilder.newClient().target("http://localhost:8080/api/v1/user-info")
//                .request(String.valueOf(MediaType.APPLICATION_JSON))
//                .headers(headers)
//                .post(Entity.json(jsonBody));
//
//        assertEquals(200, response.getStatus());
//        assertNotNull(response.readEntity(String.class));
//    }
}

//    @Test
//    public void testGetFirstName() throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth("vk_service_token");
//        Mono<String> responseBody = webTestClient.get()
//                .uri("/v1/users/269866241")
//                .headers(h -> h.putAll(headers))
//                .accept(MediaType.APPLICATION_JSON).exchange()
//                .flatMap(response -> response.bodyToMono(String.class));
//        assertEquals("Никита", responseBody.block());
//    }
//
//    @Test
//    public void testIsMember() throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth("vk_service_token");
//        Mono<Boolean> responseBody = webTestClient.get().uri("https://api.vk.com/method/groups.isMember?access_token=d7535714d7535714d7535714f7d44d9307dd753d7535714b1a3d395ee76c8f468fceb3e&group_id=168131127&user_id=269866241&v=5.199")
//                .headers(h -> h.putAll(headers))
//                .accept(MediaType.APPLICATION_JSON).exchange()
//                .flatMap(response -> response.bodyToMono(Boolean.class));
//        assertTrue(responseBody.block());
//    }
//
//    @Test
//    public void testFullNameResponse() throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setBearerAuth("vk_service_token");
//        String fullNameJson = "{\"user_id\":\"18391\", \"group_id\":\"30131\"}";
//        Mono<String> responseBody = webTestClient.post().uri("/v1/full_name")
//                .headers(h -> h.putAll(headers))
//                .contentType(MediaType.APPLICATION_JSON)
//                .syncBody(fullNameJson)
//                .exchange()
//                .flatMap(response -> response.bodyToMono(String.class));
//        assertEquals("{\"first_name\":\"Никита\",\"is_member\":true}", responseBody.block());
//    }

