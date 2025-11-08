package com.hack.botinki.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tasks")
@Getter
@Setter
public class Task {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false)
    private Integer priority;

    @Column(nullable = false)
    private Integer complexity;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}
	
	public LocalDate getData() {
		// TODO Auto-generated method stub
		return deadline;
	}
	
	public Integer getComplexity() {
		// TODO Auto-generated method stub
		return complexity;
	}
    
}
