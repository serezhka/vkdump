package com.github.serezhka.vkdump.controller;

import com.github.serezhka.vkdump.dao.entity.AttachmentEntity;
import com.github.serezhka.vkdump.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Controller
@RequestMapping("/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getAttachments(
            @RequestParam(value = "dialogId", required = false) Integer dialogId,
            @RequestParam("type") String type,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "1000") int size,
            Model model) {
        Page<AttachmentEntity> attachments;
        if (dialogId == null) {
            attachments = attachmentService.getAttachments(type, new PageRequest(page - 1, size)); // jquery.simplePagination.js starts from "1"
        } else {
            attachments = attachmentService.getAttachments(dialogId, type, new PageRequest(page - 1, size)); // jquery.simplePagination.js starts from "1"
        }
        model.addAttribute("attachments", attachments);
        return "attachments";
    }
}
