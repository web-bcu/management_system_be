package com.csbu.projects.Repositories;

import com.csbu.projects.Dto.TaskDto;
import com.csbu.projects.Models.Task;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE Task a SET  a.status = true WHERE a.id = :id")
    void updateStatusById(@Param("id") Integer id);
    List<Task> findByEmployeeId(Integer employeeId);


}
