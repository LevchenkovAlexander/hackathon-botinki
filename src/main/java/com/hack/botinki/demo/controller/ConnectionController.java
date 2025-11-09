package com.hack.botinki.demo.controller;

import com.hack.botinki.demo.entity.Task;
import com.hack.botinki.demo.entity.User;
import com.hack.botinki.demo.service.ModelService;
import com.hack.botinki.demo.service.TaskService;
import com.hack.botinki.demo.service.UserService;
import com.hack.botinki.demo.shared.FreeHoursRequest;
import com.hack.botinki.demo.shared.GenerateOrderRequest;
import com.hack.botinki.demo.shared.GenerateOrderResponse;
import com.hack.botinki.demo.shared.ResultRequest;
import com.hack.botinki.demo.shared.SubmitTaskRequest;
import com.hack.botinki.demo.shared.TaskTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Для разработки, в продакшене указать конкретный origin
public class ConnectionController {

	private ModelService modelService; 
	private TaskService taskService; 
	private UserService userService; 
	
    @PostMapping("/generate-order")
    public ResponseEntity<GenerateOrderResponse> generateOrder(@RequestBody GenerateOrderRequest request) {
        
        try {
            
            Long Uid = request.getUid();
            long[] taskIds = modelService.execute(Uid);
            List<TaskTO> optimizedTasks = new ArrayList<>();
            for (long id : taskIds) {
            	Task task = taskService.getTask(id);
            	TaskTO taskToList = new TaskTO(Uid, task.getName(), task.getDeadline().toString(), task.getComplexity());
            	optimizedTasks.add(taskToList);
            }
            GenerateOrderResponse response = new GenerateOrderResponse();
            response.setOrderedTasks(optimizedTasks.reversed());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/task")
    public ResponseEntity<Void> submitTask(@RequestBody TaskTO taskRequest) {
       
        try {
        	Long Uid = taskRequest.getUid();
        	Task taskToDB = new Task();
        	taskToDB.setName(taskRequest.getName());
            taskToDB.setDeadline(taskRequest.getDeadline());
            taskToDB.setComplexity(taskRequest.getComplexityHours());
            taskToDB.setUserId(Uid);
            taskService.addTask(taskToDB);
         
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/free-hours")
    public ResponseEntity<Void> submitFreeHours(@RequestBody FreeHoursRequest freeHoursRequest) {
        
        try {
        	Long Uid = freeHoursRequest.getUid();
        	Integer freeHours = freeHoursRequest.getFreeHours();
            User user = userService.getUser(Uid);
            user.setFreeTime(freeHours);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/result")
    public ResponseEntity<Void> submitResult(@RequestBody ResultRequest result) {
        log.info("Received result - task {}: {}% completed", 
                result.getNumber(), result.getPercent());
        
        try {
            // Сохранение результата выполнения задачи
            saveResult(result);
            return ResponseEntity.ok().build();
            
        } catch (Exception e) {
            log.error("Error saving result", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}