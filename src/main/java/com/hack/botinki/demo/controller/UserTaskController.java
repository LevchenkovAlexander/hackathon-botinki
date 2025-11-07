package com.hack.botinki.demo.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack.botinki.demo.entity.Task;
import com.hack.botinki.demo.service.TaskService;

@RestController
@RequestMapping("/users")
public class UserTaskController {

    private final TaskService taskService;

    public UserTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getTasksByUserId(@PathVariable Long id) {
        return taskService.getTasksByUserId(id);
    }
}