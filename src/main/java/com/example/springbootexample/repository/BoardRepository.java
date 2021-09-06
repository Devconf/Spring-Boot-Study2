package com.example.springbootexample.repository;

import com.example.springbootexample.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
