package com.github.serezhka.vkdump.vkapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.serezhka.vkdump.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Component
public class UsersApi {

    private final VkApiHttpsClient vkApiHttpsClient;
    private final ObjectMapper objectMapper;

    @Value("${vk.api.users.get}")
    private String getMethod;

    @Autowired
    public UsersApi(VkApiHttpsClient vkApiHttpsClient, ObjectMapper objectMapper) {
        this.vkApiHttpsClient = vkApiHttpsClient;
        this.objectMapper = objectMapper;
    }

    public UserDTO getUser(long id) throws IOException {
        Map<String, String> args = new HashMap<>();
        if (id > 0) args.put("user_ids", Long.toString(id));
        args.put("fields", "photo_200_orig,screen_name,sex");
        String json = vkApiHttpsClient.execVkApiMethod(getMethod, args);
        ResponseWrapper response = objectMapper.readValue(json, ResponseWrapper.class);
        return response.users.get(0);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
    private static class ResponseWrapper {

        @JsonProperty("response")
        private List<UserDTO> users;
    }
}
