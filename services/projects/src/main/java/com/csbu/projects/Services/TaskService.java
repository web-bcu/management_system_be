package com.csbu.projects.Services;

import com.csbu.projects.Dto.TaskDto;
import com.csbu.projects.Models.Task;
import com.csbu.projects.Repositories.TaskRepository;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<TaskDto> getTasksByEmployeeId(Integer employee_id){
        try{
            return taskRepository.findByEmployeeId(employee_id)
                    .stream()
                    .map(task -> new TaskDto(
                            task.getId(),
                            task.getTaskName(),
                            task.getManagerId(),
                            task.getEmployeeId(),
                            task.getDeadline(),
                            task.getStatus()
                    )).toList();
        }catch (Exception e){
            throw new TransactionException("Error getting status",e);
        }
    }

    public void createTask(Task task){
        try{
            taskRepository.save(task);
        }catch (Exception e){
            throw new TransactionException("Error creating status",e);
        }
    }
    public void updateTaskStatus(Integer id){
        try{
            taskRepository.updateStatusById(id);
        }catch (Exception e){
            throw new TransactionException("Error updating status",e);
        }
    }

    public void deleteTaskById(Integer id){
        try{
            taskRepository.deleteById(id);
        }catch (Exception e){
            throw new TransactionException("Error deleting status",e);
        }
    }
}

