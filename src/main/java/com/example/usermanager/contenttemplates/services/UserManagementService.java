package com.example.usermanager.contenttemplates.services;

import com.example.usermanager.contenttemplates.dto.EmployeeDto;
import com.example.usermanager.contenttemplates.entities.Employee;
import com.example.usermanager.contenttemplates.exceptions.DataNotFoundException;
import com.example.usermanager.contenttemplates.functions.SearchCriteria;
import com.example.usermanager.contenttemplates.functions.SearchEngine;
import com.example.usermanager.contenttemplates.mappers.EmployeeMapper;
import com.example.usermanager.contenttemplates.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserManagementService {

    private final static String GET_USER_NOT_FOUND = "User with id %s not found";

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    private final SearchEngine searchEngine;

    public EmployeeDto findUserById(Integer userId) throws DataNotFoundException {
        Optional<Employee> employee = Optional.ofNullable((employeeRepository.findById(userId))
                .orElseThrow(() -> new DataNotFoundException(String.format(GET_USER_NOT_FOUND, userId))));
        return employeeMapper.mapToEmployeeDto(employee.get());
    }

    public void deleteUser(Integer userId) {
        employeeRepository.deleteById(userId);
    }

    public void addEmployee(EmployeeDto employeeDto) {
        Employee employee = employeeMapper.mapToEmployee(employeeDto);
        employeeRepository.save(employee);
    }

    public void updateUser(EmployeeDto employeeDto) {
        employeeRepository.updateUser(  employeeDto.getId(),
                                        employeeDto.getName(),
                                        employeeDto.getSurname(),
                                        employeeDto.getGrade(),
                                        employeeDto.getSalary());
    }

    public List<EmployeeDto> findUsersByCriteria(SearchCriteria searchCriteria) {
        return employeeMapper.mapToEmployeeDtoList(searchEngine.findByCriteria(searchCriteria));
    }

}
