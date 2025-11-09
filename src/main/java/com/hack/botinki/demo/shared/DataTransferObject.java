package com.hack.botinki.demo.shared;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


import lombok.Data;
import lombok.Getter;

import java.util.List;
public class DataTransferObject {


	@Data
	@Getter
	public class TaskTO {
	    private Long Uid;
	    private String name;
	    private String deadline;
	    private Integer complexityHours;
	    
	    public TaskTO(Long Uid, String name, String deadline, Integer complexityHours) {
	    	this.Uid = Uid;
	    	this.name = name;
	    	this.deadline = deadline;
	    	this.complexityHours = complexityHours;
	    }
	    public Long getUid() {
			return Uid;
		}
	    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		public LocalDate getDeadline() {
			// TODO Auto-generated method stub
			return LocalDate.parse(deadline, formatter);
		}
		public Integer getComplexityHours() {
			// TODO Auto-generated method stub
			return complexityHours;
		}
		public String getName() {
			// TODO Auto-generated method stub
			return name;
		}
	}
	
	@Data
	public class FreeHoursRequest {
	    private Integer freeHours;
	    private Long Uid;
	}
	
	@Data
	public class GenerateOrderRequest {
	    private List<TaskTO> tasks;
	    private Long Uid;
		public Long getUid() {
			return Uid;
		}
	}

	@Data
	public class GenerateOrderResponse {
	    private List<TaskTO> orderedTasks;

		public void setOrderedTasks(List<TaskTO> optimizedTasks) {
			this.orderedTasks = optimizedTasks;
			
		}
	}

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

	@Data
	public class ResultRequest {
	    private Long Uid;
	    private Integer number;
	    private Integer percent;
	}
	
	@Data
	public class FreeHoursRequest {
		private Long Uid;
		private Integer hours;
	}
}
