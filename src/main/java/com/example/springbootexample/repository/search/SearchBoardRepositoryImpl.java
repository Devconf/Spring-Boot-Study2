package com.example.springbootexample.repository.search;

import com.example.springbootexample.entity.Board;
import com.example.springbootexample.entity.QBoard;
import com.example.springbootexample.entity.QMember;
import com.example.springbootexample.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override //PageRequestDTO를 파라미터로 받지 않는 이유는 DTO를 가능하면 Repository 영역에서 다루지 않기 위해서이다.
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage.....................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        //from 만들기
        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //select 만들기
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        //where 만들기
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);
        tuple.groupBy(board);
        //쿼리 날리기
        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }
}
