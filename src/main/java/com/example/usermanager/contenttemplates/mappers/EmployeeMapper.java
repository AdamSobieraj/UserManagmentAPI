package com.example.usermanager.contenttemplates.mappers;

import com.example.usermanager.contenttemplates.dto.EmployeeDto;
import com.example.usermanager.contenttemplates.entities.Employee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {

    public EmployeeDto mapToEmployeeDto(final Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .surname(employee.getSurname())
                .grade(employee.getGrade())
                .salary(employee.getSalary())
                .build();
    }

    public Employee mapToEmployee(final EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .name(employeeDto.getName())
                .surname(employeeDto.getSurname())
                .grade(employeeDto.getGrade())
                .salary(employeeDto.getSalary())
                .build();
    }

    public List<EmployeeDto> mapToEmployeeDtoList(final List<Employee> employeeList) {
        return employeeList.stream().map(t ->
               EmployeeDto.builder()
                       .id(t.getId())
                       .name(t.getName())
                       .surname(t.getSurname())
                       .grade(t.getGrade())
                       .salary(t.getSalary())
                       .build()
                ).collect(Collectors.toList());
    }

}
