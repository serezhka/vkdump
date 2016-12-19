package com.github.serezhka.vkdump.vkapi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.serezhka.vkdump.dto.ChatDTO;
import com.github.serezhka.vkdump.dto.DialogDTO;
import com.github.serezhka.vkdump.dto.MessageDTO;
import org.apache.log4j.Logger;
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
public class MessagesApi {

    private static final Logger LOGGER = Logger.getLogger(MessagesApi.class);

    private final VkApiHttpsClient vkApiHttpsClient;
    private final ObjectMapper objectMapper;
    private final ObjectMapper unwrappingObjectMapper;

    @Value("${vk.api.messages.getDialogs}")
    private String getDialogsMethod;

    @Value("${vk.api.messages.getHistory}")
    private String getHistoryMethod;

    @Value("${vk.api.messages.getChat}")
    private String getChatMethod;

    @Autowired
    public MessagesApi(VkApiHttpsClient vkApiHttpsClient, ObjectMapper objectMapper, ObjectMapper unwrappingObjectMapper) {
        this.vkApiHttpsClient = vkApiHttpsClient;
        this.objectMapper = objectMapper;
        this.unwrappingObjectMapper = unwrappingObjectMapper;
    }

    public List<DialogDTO> getDialogs(int offset, int count) throws IOException {
        Map<String, String> args = new HashMap<>();
        args.put("offset", Integer.toString(offset));
        args.put("count", Integer.toString(count));
        String json = vkApiHttpsClient.execVkApiMethod(getDialogsMethod, args);
        ResponseWrapper<DialogDTO> responseWrapper = unwrappingObjectMapper.readValue(json, new TypeReference<ResponseWrapper<DialogDTO>>() {
        });
        LOGGER.debug("Received dialogs from " + offset + " to " + (offset + count) + ". Total dialogs: " + responseWrapper.count);
        return responseWrapper.items;
    }

    public List<MessageDTO> getHistory(int offset, int count, long userId) throws IOException {
        Map<String, String> args = new HashMap<>();
        args.put("offset", Integer.toString(offset));
        args.put("count", Integer.toString(count));
        args.put("user_id", Long.toString(userId));
        String json = vkApiHttpsClient.execVkApiMethod(getHistoryMethod, args);
        ResponseWrapper<MessageDTO> responseWrapper = unwrappingObjectMapper.readValue(json, new TypeReference<ResponseWrapper<MessageDTO>>() {
        });
        LOGGER.debug("Received messages from " + offset + " to " + (offset + count) + ". Total messages: " + responseWrapper.count);
        return responseWrapper.items;
    }

    public ChatDTO getChat(long chatId) throws IOException {
        Map<String, String> args = new HashMap<>();
        args.put("chat_id", Long.toString(chatId));
        args.put("fields", "photo_200_orig,screen_name,sex");
        String json = vkApiHttpsClient.execVkApiMethod(getChatMethod, args);
        Response2Wrapper response = objectMapper.readValue(json, Response2Wrapper.class);
        return response.chat;
    }

    @JsonRootName("response")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
    private static class ResponseWrapper<T> {

        @JsonProperty("count")
        private int count;

        @JsonProperty("items")
        private List<T> items;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE,
            isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
    private static class Response2Wrapper {

        @JsonProperty("response")
        private ChatDTO chat;
    }
}
