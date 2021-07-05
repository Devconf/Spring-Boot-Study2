package com.example.springbootexample.repository;

import com.example.springbootexample.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Long mno = (long)i;

            Optional<Memo> result = memoRepository.findById(mno);
            if(!result.isPresent()) {
                Memo memo = Memo.builder().memoText("Sample..." + i).build();
                memoRepository.save(memo);
            }
        });
    }

    @Test
    public void testSelect(){
        Long mno = 101L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("==================================");

        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
        else{
            System.out.println("해당 메모가 없습니다.");
        }
    }
}
