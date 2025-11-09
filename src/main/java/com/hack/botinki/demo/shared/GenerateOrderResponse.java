package com.hack.botinki.demo.shared;

import java.util.List;

import lombok.Data;

@Data
public class GenerateOrderResponse {
    private List<TaskTO> orderedTasks;

    public void setOrderedTasks(List<TaskTO> optimizedTasks) {
        this.orderedTasks = optimizedTasks;
        
    }
}
