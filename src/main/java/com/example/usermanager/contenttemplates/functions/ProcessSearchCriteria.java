package com.example.usermanager.contenttemplates.functions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessSearchCriteria {
    private String id;
    private String name;
    private String surname;
    private String grade;
    private String salary;
}
