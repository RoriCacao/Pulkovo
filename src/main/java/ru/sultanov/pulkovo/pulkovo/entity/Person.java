package ru.sultanov.pulkovo.pulkovo.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String role;

}
