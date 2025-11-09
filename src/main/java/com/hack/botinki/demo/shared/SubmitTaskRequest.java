package com.hack.botinki.demo.shared;

import lombok.Data;

@Data
public class SubmitTaskRequest {
    private TaskTO task;
    private Long Uid;
    public TaskTO getTask() {
        return task;
    }
    public Long getUid() {
        return Uid;
    }
}