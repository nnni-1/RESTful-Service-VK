package ru.alexeev.restfulvkusers;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/vk")
public class VKController {
    @Autowired
    private ProducerTemplate producerTemplate;

    @PostMapping("/user")
    public ResponseEntity<?> getUserInfo(@RequestHeader("vk_service_token") String token,
                                         @RequestBody Map<String, Object> payload) {
        try {
            String result = producerTemplate.requestBodyAndHeader(
                    "direct:getUserInfo",
                    payload,
                    "vk_service_token", token,
                    String.class);
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
}


