package com.github.serezhka.vkdump.grabber;

import com.github.serezhka.vkdump.dto.ChatDTO;
import com.github.serezhka.vkdump.dto.DialogDTO;
import com.github.serezhka.vkdump.dto.MessageDTO;
import com.github.serezhka.vkdump.dto.UserDTO;
import com.github.serezhka.vkdump.service.MessageService;
import com.github.serezhka.vkdump.service.UserService;
import com.github.serezhka.vkdump.vkapi.MessagesApi;
import com.github.serezhka.vkdump.vkapi.UsersApi;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@SuppressWarnings("Duplicates")
@Service
public class MessageGrabber {

    private static final Logger LOGGER = Logger.getLogger(MessageGrabber.class);

    private final UsersApi usersApi;
    private final MessagesApi messagesApi;
    private final UserService userService;
    private final MessageService messageService;

    @Autowired
    public MessageGrabber(UsersApi usersApi, MessagesApi messagesApi, UserService userService, MessageService messageService) {
        this.usersApi = usersApi;
        this.messagesApi = messagesApi;
        this.userService = userService;
        this.messageService = messageService;
    }

    public void grabDialogs() throws IOException {

        long lastMessageDate = messageService.getLastMessageDate();

        // Save token owner info
        userService.save(usersApi.getUser(0));

        int dialogsProcessed = 0;
        GRABBER:
        while (true) {

            List<DialogDTO> dialogs = messagesApi.getDialogs(dialogsProcessed, 200);
            if (dialogs.size() == 0) break;

            for (DialogDTO dialog : dialogs) {

                if (dialog.message.getDate() < lastMessageDate) {
                    LOGGER.info("No more new messages!");
                    break GRABBER;
                }

                LOGGER.info("Processing dialog " + dialogsProcessed++);

                if (dialog.message.getChatId() == null) {
                    processSimpleDialog(dialog.message);
                } else {
                    processMultiDialog(dialog.message);
                }
            }
        }
    }

    private void processSimpleDialog(MessageDTO lastMessage) throws IOException {

        // Get last message date in dialog
        long lastMessageDate = messageService.getLastMessageDateInDialog(lastMessage.getUserId());

        // There are no new messages
        if (lastMessageDate == lastMessage.getDate()) return;

        // Save user info
        userService.save(usersApi.getUser(lastMessage.getUserId()));

        int messagesProcessed = 0;
        while (true) {
            List<MessageDTO> messages = messagesApi.getHistory(messagesProcessed, 200, lastMessage.getUserId());
            messagesProcessed += messages.size();
            messages.removeIf(message -> message.getDate() <= lastMessageDate);
            if (messages.size() == 0) break;
            messageService.saveMessages(messages);
        }
    }

    private void processMultiDialog(MessageDTO lastMessage) throws IOException {

        // Get last message date in dialog
        long lastMessageDate = messageService.getLastMessageDateInDialog(2000000000 + lastMessage.getChatId()); // FIXME

        // There are no new messages
        if (lastMessageDate == lastMessage.getDate()) return;

        // Save users info
        ChatDTO chat = messagesApi.getChat(lastMessage.getChatId());
        for (UserDTO user : chat.getUsers()) {
            userService.save(user);
        }

        int messagesProcessed = 0;
        while (true) {
            List<MessageDTO> messages = messagesApi.getHistory(messagesProcessed, 200, 2000000000 + lastMessage.getChatId()); // FIXME
            messagesProcessed += messages.size();
            messages.removeIf(message -> message.getDate() <= lastMessageDate);
            if (messages.size() == 0) break;
            messageService.saveMessages(messages);
        }
    }
}
