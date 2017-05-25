package com.github.serezhka.vkdump.controller;

import com.github.serezhka.vkdump.dao.entity.AttachmentEntity;
import com.github.serezhka.vkdump.dao.entity.MessageEntity;
import com.github.serezhka.vkdump.service.AttachmentService;
import com.github.serezhka.vkdump.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 * @since 12.05.2017
 */
@Controller
@RequestMapping("/staticPages")
public class StaticPagesController {

    private final MessageService messageService;
    private final AttachmentService attachmentService;

    @Autowired
    public StaticPagesController(MessageService messageService,
                                 AttachmentService attachmentService) {
        this.messageService = messageService;
        this.attachmentService = attachmentService;
    }

    @RequestMapping(value = "/dialogs", method = RequestMethod.GET)
    public String staticDialogs(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "20") int size,
                                Model model, HttpServletResponse response) {
        Page<MessageEntity> dialogs = messageService.getDialogs(new PageRequest(page - 1, size));
        if (dialogs.getContent().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        model.addAttribute("dialogs", dialogs);
        return "static-dialogs";
    }

    @RequestMapping(value = "/dialogs/{dialogId}", method = RequestMethod.GET)
    public String getMessages(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(required = false, defaultValue = "50") int size,
                              @PathVariable("dialogId") int dialogId,
                              Model model, HttpServletResponse response) {
        Page<MessageEntity> messages = messageService.getMessages(dialogId, new PageRequest(page - 1, size));
        if (messages.getContent().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        model.addAttribute("messages", messages);
        model.addAttribute("dialogId", dialogId);
        return "static-messages";
    }

    @RequestMapping(value = "/photos", method = RequestMethod.GET)
    public String getPhotos(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "1000") int size,
                            Model model, HttpServletResponse response) {
        Page<AttachmentEntity> photos = attachmentService.getFilteredPhotos(new PageRequest(page - 1, size));
        if (photos.getContent().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        model.addAttribute("attachments", photos);
        return "static-photos";
    }

    @RequestMapping(value = "/photos/{dialogId}", method = RequestMethod.GET)
    public String getPhotos(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "500") int size,
                            @PathVariable("dialogId") int dialogId,
                            Model model, HttpServletResponse response) {
        Page<AttachmentEntity> photos = attachmentService.getAttachments(dialogId, "photo", new PageRequest(page - 1, size));
        if (photos.getContent().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        model.addAttribute("attachments", photos);
        model.addAttribute("dialogId", dialogId);
        return "static-photos";
    }

    @Transactional
    @RequestMapping(value = "/voices", method = RequestMethod.GET)
    public String getVoices(Model model) {
        List<AttachmentEntity> voices = attachmentService.getAttachments("doc")
                .filter(attachment -> attachment.getType().equalsIgnoreCase("doc"))
                .filter(attachment -> attachment.getDoc().getExt().equalsIgnoreCase("ogg"))
                .collect(Collectors.toList());
        model.addAttribute("attachments", voices);
        return "static-voices";
    }
}
