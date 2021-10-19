package com.example.springbootexample.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReplyDTO {
    private Long rno;

    private String text;

    private String replyer;

    private Long bno;// 게시글 번호

    private LocalDateTime regDate, modDate;
}
