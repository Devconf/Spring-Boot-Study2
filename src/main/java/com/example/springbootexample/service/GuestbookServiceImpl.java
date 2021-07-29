package com.example.springbootexample.service;

import com.example.springbootexample.dto.GuestbookDTO;
import com.example.springbootexample.dto.PageRequestDTO;
import com.example.springbootexample.dto.PageResultDTO;
import com.example.springbootexample.entity.GuestBook;
import com.example.springbootexample.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
