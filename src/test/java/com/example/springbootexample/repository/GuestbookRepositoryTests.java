package com.example.springbootexample.repository;

import com.example.springbootexample.entity.GuestBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestBookRepository guestBookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i->{
            Optional<GuestBook> result= guestBookRepository.findById((long)i);
            if(!result.isPresent()) {
                GuestBook guestBook = GuestBook.builder()
                        .title("Title...." + i)
                        .content("Content...." + i)
                        .writer("user" + (i % 10))
                        .build();
                System.out.println(guestBookRepository.save(guestBook));
            }
        });
    }

    @Test
    public void updateTest(){
        Optional<GuestBook> result= guestBookRepository.findById(300L);
        if(result.isPresent()){
            GuestBook guestBook= result.get();
            guestBook.changeTitle("Change Title....");
            guestBook.changeContent("Change Content....");

            guestBookRepository.save(guestBook);
        }
    }
}
