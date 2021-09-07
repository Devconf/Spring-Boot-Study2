package com.example.springbootexample.service;

import com.example.springbootexample.dto.BoardDTO;
import com.example.springbootexample.entity.Board;
import com.example.springbootexample.entity.Member;

public interface BoardService {
    Long register(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto){
        Member member = Member.builder()
                .email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno()).title(dto.getTitle()).content(dto.getContent()).writer(member).build();
        return board;
    }
}
