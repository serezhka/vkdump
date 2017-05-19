package com.github.serezhka.vkdump;

import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import com.github.serezhka.vkdump.service.MessageService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaticPagesSaver {

    private static final Logger LOGGER = Logger.getLogger(StaticPagesSaver.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MessageService messageService;

    @Test
    public void saveStaticPages() throws Exception {

        int idx = 1;
        ResponseEntity<String> dialogsResponse;
        while ((dialogsResponse = restTemplate.getForEntity("/staticPages/dialogs?page=" + idx, String.class)).getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Saving " + idx + " dialogs page");
            Files.createDirectories(Paths.get("dialogs"));
            Files.write(Paths.get("dialogs/dialogs-" + idx++ + ".html"), dialogsResponse.getBody().getBytes());
        }

        List<Integer> dialogsToSave = new ArrayList<>();
        // Filter dialogs with <= 30 messages
        int k = 0;
        Page<MessageEntity> dialogs;
        while (!(dialogs = messageService.getDialogs(new PageRequest(k++, 20))).getContent().isEmpty()) {
            LOGGER.info("Checking " + k + " dialogs page.");
            for (MessageEntity dialog : dialogs) {
                if (messageService.getMessages(dialog.getDialogId(), new PageRequest(0, 50)).getContent().size() > 30) {
                    dialogsToSave.add(dialog.getDialogId());
                }
            }
        }

        dialogsToSave.removeIf(i -> i == 31750875 || i == 1534514);
        LOGGER.info(dialogsToSave.size() + " dialogs to save.");
        System.err.println(dialogsToSave);

        dialogsToSave.forEach(dialogId -> {
            int i = 1;
            ResponseEntity<String> messagesResponse;
            while ((messagesResponse = restTemplate.getForEntity("/staticPages/dialogs/" + dialogId + "?page=" + i, String.class)).getStatusCode() == HttpStatus.OK) {
                LOGGER.info("Saving " + i + " messages page");
                try {
                    Files.createDirectories(Paths.get("messages/" + dialogId));
                    Files.write(Paths.get("messages/" + dialogId + "/messages" + dialogId + "-" + i++ + ".html"), messagesResponse.getBody().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            int j = 1;
            ResponseEntity<String> attachmentsResponse;
            while ((attachmentsResponse = restTemplate.getForEntity("/staticPages/photos/" + dialogId + "?page=" + j, String.class)).getStatusCode() == HttpStatus.OK) {
                LOGGER.info("Saving " + j + " photos page");
                try {
                    Files.createDirectories(Paths.get("photos/" + dialogId));
                    Files.write(Paths.get("photos/" + dialogId + "/photos" + dialogId + "-" + j++ + ".html"), attachmentsResponse.getBody().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
