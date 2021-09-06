package com.example.springbootexample.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
public class Member extends BaseEntity{
    @Id
    private String email;

    private String password;

    private String name;
}
