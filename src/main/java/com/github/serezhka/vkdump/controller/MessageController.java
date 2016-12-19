package com.github.serezhka.vkdump.controller;

import com.github.serezhka.vkdump.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Sergei Fedorov (serezhka@xakep.ru)
 */
@Controller
@RequestMapping("/messages")
public class MessageController {

    private final UserService userService;

    @Autowired
    public MessageController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getDialogs(Model model) {
        model.addAttribute("users", userService.findAll());
        return "dialogs";
    }

    @RequestMapping(value = "/{dialog_id}", method = RequestMethod.GET)
    public String getMessages(@PathVariable("dialog_id") long dialogId, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("dialog_id", dialogId);
        return "messages";
    }
}
