package com.example.usermanager.contenttemplates.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
@Data
@Builder
@Component
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private Integer id;
    private String name;
    private String surname;
    private Integer grade;
    private Integer salary;
}

