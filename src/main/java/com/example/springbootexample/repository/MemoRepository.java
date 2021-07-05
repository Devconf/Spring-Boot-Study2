package com.example.springbootexample.repository;

import com.example.springbootexample.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {
}
