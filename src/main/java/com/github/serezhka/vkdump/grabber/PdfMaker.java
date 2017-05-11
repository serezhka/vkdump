package com.github.serezhka.vkdump.grabber;

import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import com.github.serezhka.vkdump.dao.entity.UserEntity;
import com.github.serezhka.vkdump.service.MessageService;
import com.github.serezhka.vkdump.service.UserService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 11.05.2017
 */
@Component
public class PdfMaker {

    private final MessageService messageService;
    private final UserService userService;
    private final HttpClient httpClient;

    @Autowired
    public PdfMaker(MessageService messageService,
                    UserService userService,
                    HttpClient httpClient) throws IOException, DocumentException {
        this.messageService = messageService;
        this.userService = userService;
        this.httpClient = httpClient;

        FontFactory.register("/font/TheanoModern-Regular.ttf");
    }

    @Deprecated
    public void makePdf(int dialogId) {

        UserEntity tokenOwner = userService.getTokenOwner();

        Page<MessageEntity> messages = messageService.getMessages(dialogId, new PageRequest(0, 5));

        Document doc = new Document();
        doc.addCreationDate();
        doc.addProducer();
        doc.setPageSize(PageSize.A4);

        Font font = FontFactory.getFont("TheanoModern-Regular", BaseFont.IDENTITY_H, true);

        try (FileOutputStream fos = new FileOutputStream(tokenOwner.getFirstName() + " " + tokenOwner.getLastName() + ".pdf")) {
            PdfWriter.getInstance(doc, fos);
            doc.open();

            // Title
            Paragraph tittle = new Paragraph(tokenOwner.getFirstName() + " " + tokenOwner.getLastName() + "\n", font);
            tittle.setAlignment(Element.ALIGN_CENTER);
            doc.add(tittle);

            // Messages
            PdfPTable messagesTable = new PdfPTable(4);
            messagesTable.addCell(new PdfPCell(new Paragraph("Sender")));
            messagesTable.addCell(new PdfPCell(new Paragraph("Message")));
            messagesTable.addCell(new PdfPCell(new Paragraph("Date")));
            messagesTable.addCell(new PdfPCell(new Paragraph("Attachments")));
            for (MessageEntity messageEntity : messages.getContent()) {
                UserEntity sender = userService.findById(messageEntity.getFromId()); // FIXME Optimize with message "isOut" field
                Paragraph senderName = new Paragraph(sender.getFirstName() + " " + sender.getLastName(), font);
                Image senderImage = loadImage(sender.getPhoto200Orig()); // FIXME Cache images
                senderImage.scaleToFit(32, 32);
                PdfPCell senderCell = new PdfPCell();
                senderCell.addElement(senderImage);
                senderCell.addElement(senderName);
                messagesTable.addCell(senderCell);
                messagesTable.addCell(new PdfPCell(new Paragraph(messageEntity.getBody(), font)));
                messagesTable.addCell(new PdfPCell(new Paragraph(Date.from(Instant.ofEpochSecond(messageEntity.getDate())).toString(), font))); // FIXME Simple datetime format
                if (messageEntity.getAttachments() == null && !messageEntity.getAttachments().isEmpty()) {
                    messagesTable.addCell(new PdfPCell(new Paragraph(messageEntity.getAttachments().get(0).getType())));
                } else {
                    messagesTable.addCell(new PdfPCell(new Paragraph()));
                }
            }
            doc.add(messagesTable);

            doc.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            doc.close();
        }
    }

    private Image loadImage(String url) {
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                byte[] imgDataBa = new byte[(int) entity.getContentLength()];
                DataInputStream dataIs = new DataInputStream(entity.getContent());
                dataIs.readFully(imgDataBa);
                return Image.getInstance(imgDataBa);
            }
        } catch (IOException | BadElementException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("No image");
    }
}
