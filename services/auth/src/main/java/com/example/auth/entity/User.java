package com.example.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document
public class User {
    @Id
    private String id;
    private String full_name;
    private String dob;
    private String department_id;
    private Integer yoe;
    private Boolean approved;
    private String role;
}
