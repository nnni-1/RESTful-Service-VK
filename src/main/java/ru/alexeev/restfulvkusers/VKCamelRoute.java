package ru.alexeev.restfulvkusers;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class VKCamelRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("direct:getUserInfo")
                .process(exchange -> {
                    Map<String, Object> body = exchange.getIn().getBody(Map.class);
                    String userId = body.get("user_id").toString();
                    String groupId = body.get("group_id").toString();
                    String token = exchange.getIn().getHeader("vk_service_token", String.class);
                    String vkApiUserInfoURL = String.format("https://api.vk.com/method/users.get?user_ids=%s&access_token=%s&v=5.199",
                            userId, token);
                    String vkApiInGroupURL = String.format("https://api.vk.com/method/groups.isMember?access_token=%s&group_id=%s&user_id=%s&v=5.199", token,
                            groupId, userId);
                    exchange.getIn().setHeader("vkApiUserInfoURL", vkApiUserInfoURL);
                    exchange.getIn().setHeader("vkApiInGroupURL", vkApiInGroupURL);
                })
                .multicast()

                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to("https://api.vk.com?throwExceptionOnFailure=false") // Вызов API VK
                .unmarshal().json(JsonLibrary.Jackson)  // Парсинг ответа от VK
                .process(exchange -> {
                    Map<String, Object> response = exchange.getIn().getBody(Map.class);
                    List<Map<String, Object>> responseBody = (List<Map<String, Object>>) response.get("response");
                    if (responseBody != null && !responseBody.isEmpty()) {
                        Map<String, String> result = getStringStringMap(responseBody);

                        exchange.getIn().setBody(result);
                    } else {
                        exchange.getIn().setBody(Collections.singletonMap("error", "User not found"));
                    }
                });
    }

    private Map<String, String> getStringStringMap(List<Map<String, Object>> responseBody) {
        Map<String, Object> userInfo = responseBody.get(0);
        String firstName = (String) userInfo.get("first_name");
        String lastName = (String) userInfo.get("last_name");

        Map<String, String> result = new HashMap<>();
        result.put("first_name", firstName);
        result.put("last_name", lastName);
        return result;
    }

}

