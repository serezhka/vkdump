package com.github.serezhka.vkdump.grabber;

import com.github.serezhka.vkdump.service.MessageService;
import com.github.serezhka.vkdump.service.UserService;
import com.github.serezhka.vkdump.util.converter.EntityConverter;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.messages.Dialog;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.users.UserField;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Component
public class MessageGrabber {

    private static final Logger LOGGER = Logger.getLogger(MessageGrabber.class);

    private final UserActor tokenOwner;
    private final VkApiClient vkApiClient;
    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageGrabber(UserActor tokenOwner,
                          VkApiClient vkApiClient,
                          MessageService messageService,
                          UserService userService) {
        this.tokenOwner = tokenOwner;
        this.vkApiClient = vkApiClient;
        this.messageService = messageService;
        this.userService = userService;
    }


    public void grabDialogs() throws ClientException, ApiException {

        // Save token owner info
        vkApiClient.users().get(tokenOwner).fields(UserField.PHOTO_200_ORIG).execute().stream()
                .map(EntityConverter::userToEntity)
                .forEach(userService::save);

        // Get last message id
        int dialogsProcessed = 0;
        while (true) {

            List<Dialog> dialogs = vkApiClient.messages().getDialogs(tokenOwner)
                    .offset(dialogsProcessed)
                    .count(200)
                    .execute().getItems();

            for (Dialog dialog : dialogs) {
                LOGGER.info("Processing dialog " + dialogsProcessed++);
                if (dialog.getMessage().getChatId() == null) {
                    processSimpleDialog(dialog.getMessage());
                } else {
                    processMultiDialog(dialog.getMessage());
                }
            }

            if (dialogs.size() < 200) break;
        }
    }

    private void processSimpleDialog(Message lastMessage) throws ClientException, ApiException {

        // Get last message id in dialog
        int lastMessageId = messageService.getLastMessageIdInDialog(lastMessage.getUserId());

        // There are no new messages
        if (lastMessageId == lastMessage.getId()) return;

        // Save user info
        vkApiClient.users().get(tokenOwner).userIds(String.valueOf(lastMessage.getUserId())).fields(UserField.PHOTO_200_ORIG)
                .execute().stream().map(EntityConverter::userToEntity).forEach(userService::save);

        int messagesProcessed = 0;
        while (true) {

            List<Message> messages = vkApiClient.messages().getHistory(tokenOwner)
                    .offset(messagesProcessed).count(200).userId(String.valueOf(lastMessage.getUserId())).execute().getItems();

            messagesProcessed += messages.size();
            messages.removeIf(message -> message.getId() <= lastMessageId);

            messageService.saveMessages(messages.stream().map(EntityConverter::messageToEntity).collect(Collectors.toList()));

            if (messages.size() < 200) break;
        }
    }

    private void processMultiDialog(Message lastMessage) throws ClientException, ApiException {

        // Get last message date in dialog
        long lastMessageId = messageService.getLastMessageIdInDialog(2000000000 + lastMessage.getChatId()); // FIXME

        // There are no new messages
        if (lastMessageId == lastMessage.getId()) return;

        // Save users info
        // FIXME class GetChatUsersChatIdsFieldsResponse is not implemented
        //vkApiClient.messages().getChatUsers(tokenOwner, Collections.singletonList(lastMessage.getChatId()), UserField.PHOTO_200_ORIG)
        //        .execute().stream().map(EntityConverter::userToEntity).forEach(userService::save);

        vkApiClient.users().get(tokenOwner).userIds(lastMessage.getChatActive().stream().map(String::valueOf).collect(Collectors.toList()))
                .fields(UserField.PHOTO_200_ORIG).execute().stream().map(EntityConverter::userToEntity).forEach(userService::save);

        int messagesProcessed = 0;
        while (true) {

            List<Message> messages = vkApiClient.messages().getHistory(tokenOwner)
                    .offset(messagesProcessed).count(200).userId(String.valueOf(2000000000 + lastMessage.getChatId())).execute().getItems();

            messagesProcessed += messages.size();
            messages.removeIf(message -> message.getId() <= lastMessageId);

            messageService.saveMessages(messages.stream().map(EntityConverter::messageToEntity).collect(Collectors.toList()));

            if (messages.size() < 200) break;
        }
    }
}
