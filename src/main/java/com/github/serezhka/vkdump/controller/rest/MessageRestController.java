package com.github.serezhka.vkdump.controller.rest;

import com.github.serezhka.vkdump.controller.rest.dto.JQueryDataTablesDTO;
import com.github.serezhka.vkdump.dto.DialogDTO;
import com.github.serezhka.vkdump.dto.MessageDTO;
import com.github.serezhka.vkdump.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageRestController {

    private final MessageService messageService;

    @Autowired
    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/getDialogs", method = RequestMethod.GET)
    public JQueryDataTablesDTO<DialogDTO> getDialogs(
            @RequestParam(value = "start") int start,
            @RequestParam(value = "length") int length,
            @RequestParam(value = "draw") int draw,
            @RequestParam(value = "search[value]") String search) {
        return new JQueryDataTablesDTO<>(messageService.getDialogs(search, new PageRequest(start / length, length)), draw);
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.GET)
    public JQueryDataTablesDTO<MessageDTO> getMessages(
            @RequestParam(value = "dialog_id") long dialogId,
            @RequestParam(value = "start") int start,
            @RequestParam(value = "length") int length,
            @RequestParam(value = "draw") int draw,
            @RequestParam(value = "search[value]") String search) {
        return new JQueryDataTablesDTO<>(messageService.getMessages(dialogId, search, new PageRequest(start / length, length)), draw);
    }
}
