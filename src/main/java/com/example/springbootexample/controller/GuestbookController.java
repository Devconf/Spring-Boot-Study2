package com.example.springbootexample.controller;

import com.example.springbootexample.dto.PageRequestDTO;
import com.example.springbootexample.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService service;

    @GetMapping(value = "/")
    public String index(){
        return "redirect:/guestbook/list";
    }


    @GetMapping(value = "/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list............" + pageRequestDTO);

        model.addAttribute("result",service.getList(pageRequestDTO));
    }
}
