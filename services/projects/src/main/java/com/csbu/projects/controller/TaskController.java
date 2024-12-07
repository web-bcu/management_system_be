package com.csbu.projects.controller;

import com.csbu.projects.Dto.TaskDto;
import com.csbu.projects.Models.Task;
import com.csbu.projects.Requests.AddTaskRequest;
import com.csbu.projects.Services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    @Autowired
    private TaskService taskService;

//    @GetMapping
//    public String testTask() {
//        return "Task đây nha chú em - Port 8070 - Good luck";
//    }
    @PostMapping("/task")
    public ResponseEntity<String> createTask(@RequestBody @Valid AddTaskRequest request)
    {
        Task task = new Task();
        task.setTaskName(request.getTaskName());
        task.setManagerId(request.getManagerId());
        task.setEmployeeId(request.getEmployeeId());
        task.setDeadline(request.getDeadline());
        task.setStatus(false);
        taskService.createTask(task);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Task has been created!");
    }

    @PutMapping("/task/{id}/status")
    public ResponseEntity<String> updateStatusById(@PathVariable(name = "id") Integer id){
        taskService.updateTaskStatus(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Task's status has been updated");
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name="id") Integer id){
        taskService.deleteTaskById(id);
        return  ResponseEntity.status(HttpStatus.OK).body("Task has been deleted!");
    }

    @GetMapping("/task/{employee_id}")
    public ResponseEntity<List<TaskDto>> getTaskByEmployeeId(@PathVariable(name="employee_id") Integer employee_id){
        return  ResponseEntity.status(HttpStatus.OK).body(
            taskService.getTasksByEmployeeId(employee_id)
        );
    }
}
