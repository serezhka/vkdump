package com.github.serezhka.vkdump.controller;

import com.github.serezhka.vkdump.service.FaveService;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/faves")
public class FaveController {

    private final FaveService faveService;

    @Autowired
    public FaveController(FaveService faveService) {
        this.faveService = faveService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getFaves(
            @RequestParam("type") String type,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "100") int size,
            Model model) throws ClientException, ApiException {
        model.addAttribute("faves", faveService.getFaves(type, new PageRequest(page - 1, size))); // jquery.simplePagination.js starts from "1"
        return "faves";
    }
}
