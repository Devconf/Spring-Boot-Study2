package com.example.springbootexample.repository;

import com.example.springbootexample.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook,Long> {
}
