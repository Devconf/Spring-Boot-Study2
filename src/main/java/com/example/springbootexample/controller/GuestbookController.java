package com.example.springbootexample.controller;

import com.example.springbootexample.dto.SampleDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/guestbook")
@Log4j2
public class GuestbookController {
    @GetMapping({"/","/list"})
    public String list(){
        log.info("list............");
        return "/guestbook/list";
    }
}
