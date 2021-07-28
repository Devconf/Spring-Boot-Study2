package com.example.springbootexample.repository;

import com.example.springbootexample.entity.BaseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BaseEntityRepositoryTests {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i ->{
            Long mno = (long)i;

            Optional<BaseEntity> result = memoRepository.findById(mno);
            if(!result.isPresent()) {
                BaseEntity baseEntity = BaseEntity.builder().memoText("Sample..." + mno).build();
                memoRepository.save(baseEntity);
            }
        });
    }

    @Test
    public void testSelect(){
        Long mno = 101L;

        Optional<BaseEntity> result = memoRepository.findById(mno);
        System.out.println("==================================");

        if(result.isPresent()){
            BaseEntity baseEntity = result.get();
            System.out.println(baseEntity);
        }
        else{
            System.out.println("해당 메모가 없습니다.");
        }
    }

    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;

        BaseEntity baseEntity = memoRepository.getById(mno);
        System.out.println("==================================");

        System.out.println(baseEntity);
    }

    @Test
    public void testUpdate(){
        BaseEntity baseEntity = BaseEntity.builder().mno(1L).memoText("Update Text").build();
        System.out.println(memoRepository.save(baseEntity));
    }

    @Test
    public void testDelete(){
        Long mno =2L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0,10);
        Page<BaseEntity> result = memoRepository.findAll(pageable);

        System.out.println(result);
        System.out.println("------------------------------------");

        System.out.println("Total Pages: "+ result.getTotalPages());
        System.out.println("Total cout: " + result.getTotalElements());
        System.out.println("Page Number: "+ result.getNumber());
        System.out.println("Page Size: "+ result.getSize());
        System.out.println("has next page?: "+result.hasNext());
        System.out.println("first page?: "+result.isFirst());

        System.out.println("------------------------------------");
        for(BaseEntity baseEntity :result.getContent()){
            System.out.println(baseEntity);
        }
    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll =sort1.and(sort2);

        Pageable pageable = PageRequest.of(0,10,sortAll);

        Page<BaseEntity> result = memoRepository.findAll(pageable);

        result.get().forEach(baseEntity -> {
            System.out.println(baseEntity);
        });
    }

    @Test
    public void testQueryMethods(){
        List<BaseEntity> list = memoRepository.findByMnoBetweenOrderByMnoDesc(60L,80L);
        for(BaseEntity baseEntity :list){
            System.out.println(baseEntity);
        }
    }

    @Test
    public void testQueryMethodWithPageable(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<BaseEntity> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(baseEntity -> {
            System.out.println(baseEntity);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethod(){
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
