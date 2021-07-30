package com.example.springbootexample.service;

import com.example.springbootexample.dto.GuestbookDTO;
import com.example.springbootexample.dto.PageRequestDTO;
import com.example.springbootexample.dto.PageResultDTO;
import com.example.springbootexample.entity.GuestBook;
import com.example.springbootexample.repository.GuestBookRepository;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestBookRepository guestBookRepository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO----------------------");
        log.info(dto);

        GuestBook entity= dtoToEntity(dto);

        log.info(entity);

        guestBookRepository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, GuestBook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageRequest(Sort.by("gno").descending());

        Page<GuestBook> result = guestBookRepository.findAll(pageable);

        Function<GuestBook,GuestbookDTO> fn = (entity-> entityToDto(entity));
        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<GuestBook> result =guestBookRepository.findById(gno);
        return result.isPresent()?entityToDto(result.get()): null;
    }

    @Override
    public void remove(Long gno) {
        guestBookRepository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<GuestBook> result = guestBookRepository.findById(dto.getGno());
        if(result.isPresent()){
            GuestBook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            guestBookRepository.save(entity);
        }
    }
}
