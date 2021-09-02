package com.example.springbootexample.service;

import com.example.springbootexample.dto.GuestbookDTO;
import com.example.springbootexample.dto.PageRequestDTO;
import com.example.springbootexample.dto.PageResultDTO;
import com.example.springbootexample.entity.GuestBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    GuestbookService guestbookService;

    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title....")
                .content("Sample Content....")
                .writer("User0")
                .build();
        System.out.println(guestbookService.register(guestbookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1).size(10).build();
        PageResultDTO<GuestbookDTO, GuestBook> resultDTO = guestbookService.getList(pageRequestDTO);

        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("__________________________________");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("==================================");
        resultDTO.getPageList().forEach(i-> System.out.println(i));
    }

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("김현식")
                .build();

        PageResultDTO<GuestbookDTO,GuestBook> resultDTO = guestbookService.getList(pageRequestDTO);

        System.out.println("PREV: "+ resultDTO.isPrev());
        System.out.println("NEXT: "+ resultDTO.isNext());
        System.out.println("TOTAL: "+ resultDTO.getTotalPage());

        System.out.println("________________________________________");
        for(GuestbookDTO guestbookDTO: resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("=========================================");
        resultDTO.getPageList().forEach(i ->{
            System.out.println(i);
        });
    }
}
