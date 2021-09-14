package com.example.springbootexample.repository.search;

import com.example.springbootexample.entity.Board;
import com.example.springbootexample.entity.QBoard;
import com.example.springbootexample.entity.QMember;
import com.example.springbootexample.entity.QReply;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1.........................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;


        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        jpqlQuery.select(board, member.email, reply.count()).groupBy(board);

        log.info("----------------------------");
        log.info(jpqlQuery);
        log.info("----------------------------");

        List<Board> result = jpqlQuery.fetch();
        for (Object tmp : result) {
            log.info(tmp);
        }
        log.info("------------------------------");
        log.info(result);

        return null;
    }
}
