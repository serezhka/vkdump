package com.github.serezhka.vkdump.grabber;

import com.github.serezhka.vkdump.service.AttachmentService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Component
public class AttachmentsDownloader {

    private final HttpClient httpClient;
    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentsDownloader(HttpClient httpClient,
                                 AttachmentService attachmentService) {
        this.httpClient = httpClient;
        this.attachmentService = attachmentService;
    }

    @Transactional
    public void downloadPhotoAttachments() throws IOException {
        String path = "photos";
        Files.createDirectory(Paths.get(path));
        attachmentService.getAttachments("photo")
                .parallel()
                .forEach(attachment -> {
                    String imageName = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_").format(LocalDateTime.ofEpochSecond(attachment.getMessage().getDate(), 0, ZoneOffset.UTC))
                            + attachment.getMessage().getDialogId() + "_" + attachment.getPhoto().getPhotoId();
                    String imageURL = Stream.of(
                            attachment.getPhoto().getPhoto2560(),
                            attachment.getPhoto().getPhoto1280(),
                            attachment.getPhoto().getPhoto807(),
                            attachment.getPhoto().getPhoto604(),
                            attachment.getPhoto().getPhoto130(),
                            attachment.getPhoto().getPhoto75()
                    ).filter(Objects::nonNull).findFirst().orElseThrow(RuntimeException::new);

                    try {
                        HttpGet httpget = new HttpGet(imageURL);
                        HttpResponse response = httpClient.execute(httpget);
                        HttpEntity entity = response.getEntity();
                        if (entity != null) {
                            try (FileOutputStream outstream = new FileOutputStream(".\\" + path + "\\" + imageName + ".jpg")) {
                                System.out.println("Downloading " + imageName);
                                entity.writeTo(outstream);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Transactional
    public void downloadVoiceAttachments() throws IOException {
        String path = "voice";
        Files.createDirectory(Paths.get(path));
        attachmentService.getAttachments("doc")
                .parallel()
                .forEach(attachment -> {
                    if (attachment.getDoc().getExt().equalsIgnoreCase("ogg")) {
                        try {
                            HttpGet httpget = new HttpGet(attachment.getDoc().getUrl());
                            HttpResponse response = httpClient.execute(httpget);
                            HttpEntity entity = response.getEntity();
                            if (entity != null) {
                                try (FileOutputStream outstream = new FileOutputStream(".\\" + path + "\\" + attachment.getDoc().getDocId() + ".ogg")) {
                                    System.out.println("Downloading " + attachment.getDoc().getDocId());
                                    entity.writeTo(outstream);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
