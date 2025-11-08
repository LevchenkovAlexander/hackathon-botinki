package com.hack.botinki.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hack.botinki.demo.entity.Task;
import com.hack.botinki.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	List<Task> findByUserId(Long userId);
}
