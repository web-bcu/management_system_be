package com.csbu.projects.Dto;

import java.util.Date;

public record TaskDto(
        Integer id,
        String taskName,
        Integer managerId,
        Integer employeeId,
        Date deadline,
        boolean status
) {
}
