package com.example.springbootexample.service;

import com.example.springbootexample.dto.BoardDTO;
import com.example.springbootexample.dto.PageRequestDTO;
import com.example.springbootexample.dto.PageResultDTO;
import com.example.springbootexample.entity.Board;
import com.example.springbootexample.entity.Member;
import com.example.springbootexample.repository.BoardRepository;
import com.example.springbootexample.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;
    private final ReplyRepository replyRepository;


    @Override
    public Long register(BoardDTO dto) {
        log.info(dto);

        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        log.info(pageRequestDTO);

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

        Page<Object[]> result = repository.getBoardWithReplyCount(pageRequestDTO.getPageRequest(Sort.by("bno").descending()));
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {
        replyRepository.deleteByBno(bno);
        repository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getById(boardDTO.getBno()); //getById는 프록시 레퍼런스를 반환한다.

        System.out.println("========= Select Query Before =========");
        board.changeTitle(boardDTO.getTitle());
        System.out.println("========= Select Query Done =========");
        board.changeContent(boardDTO.getContent());

        repository.save(board);
    }
}
