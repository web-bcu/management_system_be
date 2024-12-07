package com.csbu.projects.Requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class AddTaskRequest {
    @NotNull
    private String taskName;
    @NotNull
    private Integer managerId;
    @NotNull
    private Integer employeeId;
    @NotNull
    private Date deadline;


    public @NotNull String getTaskName() {
        return taskName;
    }

    public @NotNull Integer getManagerId() {
        return managerId;
    }

    public @NotNull Integer getEmployeeId() {
        return employeeId;
    }

    public @NotNull Date getDeadline() {
        return deadline;
    }
}
