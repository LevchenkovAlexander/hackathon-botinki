package com.hack.botinki.demo.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date deadline;

    private Integer priority;

    private Integer complexity;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
