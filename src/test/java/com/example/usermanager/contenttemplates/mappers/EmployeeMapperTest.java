package com.example.usermanager.contenttemplates.mappers;

import com.example.usermanager.contenttemplates.dto.EmployeeDto;
import com.example.usermanager.contenttemplates.entities.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import utility.UnitTestSingle;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@UnitTestSingle
class EmployeeMapperTest {

    @InjectMocks
    private EmployeeMapper employeeMapper;

    @Test
    void mapToEmployeeDto() {
        //Given
        Employee employee = createEmployee();
        //When
        EmployeeDto employeeDto = employeeMapper.mapToEmployeeDto(employee);
        //Then
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getName(), employeeDto.getName());
        assertEquals(employee.getSurname(), employeeDto.getSurname());
        assertEquals(employee.getGrade(), employeeDto.getGrade());
        assertEquals(employee.getSalary(), employeeDto.getSalary());
    }

    @Test
    void mapToEmployee() {
        //Given
        EmployeeDto employeeDto = createEmployeeDto();
        //When
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        //Then
        assertEquals(employeeDto.getId(), employee.getId());
        assertEquals(employeeDto.getName(), employee.getName());
        assertEquals(employeeDto.getSurname(), employee.getSurname());
        assertEquals(employeeDto.getGrade(), employee.getGrade());
        assertEquals(employeeDto.getSalary(), employee.getSalary());
    }

    @Test
    void mapToEmployeeDtoList() {
        //Given
        Employee employee = createEmployee();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        //When
        List<EmployeeDto> employeeDtoList = employeeMapper.mapToEmployeeDtoList(employeeList);
        //Then
        assertEquals(employee.getId(), employeeDtoList.get(0).getId());
        assertEquals(employee.getName(), employeeDtoList.get(0).getName());
        assertEquals(employee.getSurname(), employeeDtoList.get(0).getSurname());
        assertEquals(employee.getGrade(), employeeDtoList.get(0).getGrade());
        assertEquals(employee.getSalary(), employeeDtoList.get(0).getSalary());
    }

    private Employee createEmployee() {
        return Employee.builder()
                .id(1)
                .name("testEmployeeName")
                .surname("testEmployeeSurname")
                .grade(22)
                .salary(23000)
                .build();
    }

    private EmployeeDto createEmployeeDto() {
        return EmployeeDto.builder()
                .id(1)
                .name("testEmployeeNameDto")
                .surname("testEmployeeSurnameDto")
                .grade(22)
                .salary(23000)
                .build();
    }
}