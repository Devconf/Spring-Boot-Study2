package com.example.springbootexample.service;

import com.example.springbootexample.dto.BoardDTO;
import com.example.springbootexample.entity.Board;
import com.example.springbootexample.repository.BoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class BoardServiceImpl implements BoardService {
    private BoardRepository repository;

    public BoardServiceImpl(BoardRepository boardRepository){
     this.repository=boardRepository;
    }

    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    };
}
