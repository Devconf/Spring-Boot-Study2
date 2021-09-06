package com.example.springbootexample.repository;

import com.example.springbootexample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
}
