package com.example.springbootexample.controller;

import com.example.springbootexample.dto.GuestbookDTO;
import com.example.springbootexample.dto.PageRequestDTO;
import com.example.springbootexample.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping(value = "/register")
    public void register(){
        log.info("register get...");
    }

    @PostMapping(value = "/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto..." + dto);

        Long gno = service.register(dto);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @GetMapping(value = {"/read","/modify"} )
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model){
        log.info("gno: "+gno);
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto",dto);
    }

    @PostMapping(value = "/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno: "+gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg",gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping(value = "/modify")
    public String modify(GuestbookDTO dto,@ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
                         RedirectAttributes redirectAttributes){
        log.info("post modify.............................");
        log.info("dto: "+ dto);

        service.modify(dto);

        redirectAttributes.addAttribute("page",pageRequestDTO.getPage());
        redirectAttributes.addAttribute("gno",dto.getGno());

        return "redirect:/guestbook/read";
    }
}
