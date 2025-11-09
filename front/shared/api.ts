/**
 * Shared code between client and server
 * Useful to share types between client and server
 * and/or small pure JS functions that can be used on both client and server
 */

/**
 * Example response type for /api/demo
 */
export interface DemoResponse {
  message: string;
}

export interface Task {
  id?: string;
  name?: string;
  number?: string;
  hours?: number;
  percent?: number; // percent completed 0-100
  deadline?: string; // string date, can be DD:MM:YYYY or ISO
  complexityHours?: number;
}

export interface GenerateOrderRequest {
  tasks: Task[];
  freeHoursToday?: number;
}

export interface GenerateOrderResponse {
  orderedTasks: Task[];
  message?: string;
}

export interface SubmitTaskRequest {
  task: Task;
}

export interface SubmitTaskResponse {
  ok: boolean;
  taskId?: string;
}
