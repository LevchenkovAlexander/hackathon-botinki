package com.hack.botinki.demo.controller;

import com.hack.botinki.demo.entity.Task;
import com.hack.botinki.demo.entity.User;
import com.hack.botinki.demo.service.ModelService;
import com.hack.botinki.demo.service.TaskService;
import com.hack.botinki.demo.service.UserService;
import com.hack.botinki.demo.shared.DataTransferObject.FreeHoursRequest;
import com.hack.botinki.demo.shared.DataTransferObject.GenerateOrderRequest;
import com.hack.botinki.demo.shared.DataTransferObject.GenerateOrderResponse;
import com.hack.botinki.demo.shared.DataTransferObject.SubmitTaskRequest;
import com.hack.botinki.demo.shared.DataTransferObject.TaskTO;

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
            	Task task = taskService.getTask(Uid);
            	TaskTO taskToList = new TaskTO(Uid, task.getName(), task.get().toString(), task.getEstimatedComplexity());
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
        	taskToDB.setName(taskRequest.getName()).setDeadline(taskRequest.getDeadline()).setComplexity(taskRequest.getComplexityHours()).setUid(Uid);
            taskService.addTask(taskToDB);
         
        } catch (Exception e) {

        }
    }

    @PostMapping("/free-hours")
    public ResponseEntity<Void> submitFreeHours(@RequestBody FreeHoursRequest freeHoursRequest) {
        
        try {
        	Long Uid = freeHoursRequest.getUid();
        	Integer freeHours = freeHoursRequest.getFreeHours();
            User user = userService.getUser(Uid);
            user.setFreeHours(freeHours);
        } catch (Exception e) {
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
