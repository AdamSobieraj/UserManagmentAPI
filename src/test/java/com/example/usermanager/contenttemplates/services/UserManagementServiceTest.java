package com.example.usermanager.contenttemplates.services;

import com.example.usermanager.contenttemplates.dto.EmployeeDto;
import com.example.usermanager.contenttemplates.entities.Employee;
import com.example.usermanager.contenttemplates.exceptions.DataNotFoundException;
import com.example.usermanager.contenttemplates.functions.ProcessSearchCriteria;
import com.example.usermanager.contenttemplates.functions.ProcessSortCriteria;
import com.example.usermanager.contenttemplates.functions.SearchCriteria;
import com.example.usermanager.contenttemplates.functions.SearchEngine;
import com.example.usermanager.contenttemplates.mappers.EmployeeMapper;
import com.example.usermanager.contenttemplates.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import utility.RepositoryTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RepositoryTest
class UserManagementServiceTest {

    private final static String TEST_ERROR_MESSAGE = "User with id 0 not found";
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private SearchEngine searchEngine;

    @BeforeEach
    public void init() {
        employeeRepository.deleteAll();
    }

    @Test
    void findUserById() {
        //Given
        Employee employeeOne   = createEmployee();
        Employee employeeTwo   = createEmployee();
        Employee employeeThree = createEmployee();
        //When
        employeeRepository.save(employeeOne);
        Employee empAfterSave = employeeRepository.save(employeeTwo);
        employeeRepository.save(employeeThree);
        Long repoCount = employeeRepository.count();
        EmployeeDto resultEmp = userManagementService.findUserById(empAfterSave.getId());
        //Then
        assertEquals(3, repoCount);
        assertEquals(empAfterSave.getId(), resultEmp.getId());
    }

    @Test
    void findUserByIdException() {
        //Given
        Employee employeeOne   = createEmployee();
        Employee employeeTwo   = createEmployee();
        Employee employeeThree = createEmployee();
        //When
        employeeRepository.save(employeeOne);
        Employee empAfterSave = employeeRepository.save(employeeTwo);
        employeeRepository.save(employeeThree);
        Long repoCount = employeeRepository.count();
        userManagementService.findUserById(empAfterSave.getId());
        DataNotFoundException dataNotFoundException = assertThrows(DataNotFoundException.class, () -> userManagementService.findUserById(0));
        //Then
        assertEquals(3, repoCount);
        assertEquals(dataNotFoundException.getMessage(), TEST_ERROR_MESSAGE);
    }

    @Test
    void deleteUser() {
        //Given
        Employee employeeOne   = createEmployee();
        Employee employeeTwo   = createEmployee();
        Employee employeeThree = createEmployee();
        //When
        employeeRepository.save(employeeOne);
        Employee empAfterSave = employeeRepository.save(employeeTwo);
        employeeRepository.save(employeeThree);
        Long repoCount = employeeRepository.count();
        userManagementService.deleteUser(empAfterSave.getId());
        Long repoCountAfterDel = employeeRepository.count();
        //Then
        assertEquals(3, repoCount);
        assertEquals(2, repoCountAfterDel);
    }

    @Test
    void addEmployee() {
        //Given
        Employee employeeOne   = createEmployee();
        Employee employeeTwo   = createEmployee();
        Employee employeeThree = createEmployee();
        //When
        employeeRepository.save(employeeOne);
        employeeRepository.save(employeeTwo);
        employeeRepository.save(employeeThree);
        Long repoCount = employeeRepository.count();
        userManagementService.addEmployee(createEmployeeDto());
        Long repoCountAfterAfterAdd = employeeRepository.count();
        //Then
        assertEquals(3, repoCount);
        assertEquals(4, repoCountAfterAfterAdd);
    }

    @Test
    void updateUser() {
        //Given
        Employee employeeOne   = createEmployee();
        Employee employeeTwo   = createEmployee();
        Employee employeeThree = createEmployee();
        //When
        employeeRepository.save(employeeOne);
        Employee empAfterSave = employeeRepository.save(employeeTwo);
        employeeRepository.save(employeeThree);
        Long repoCount = employeeRepository.count();

        empAfterSave.setName("NewTestName");
        empAfterSave.setSurname("NewTestSurname");
        empAfterSave.setGrade(33);
        empAfterSave.setSalary(333333);
        EmployeeDto modifiedEmp = employeeMapper.mapToEmployeeDto(empAfterSave);
        userManagementService.updateUser(modifiedEmp);
        Optional<Employee> result = employeeRepository.findById(empAfterSave.getId());
        Long repoCountAfterAfterAdd = employeeRepository.count();
        //Then
        assertEquals(3, repoCount);
        assertEquals(3, repoCountAfterAfterAdd);
        assertEquals(empAfterSave.getId(), result.get().getId());
        assertEquals(empAfterSave.getName(), result.get().getName());
        assertEquals(empAfterSave.getSurname(), result.get().getSurname());
        assertEquals(empAfterSave.getGrade(), result.get().getGrade());
        assertEquals(empAfterSave.getSalary(), result.get().getSalary());
    }

    @Test
    void findUsersByCriteria() {
        //Given
        Employee employeeOne   = createEmployee();
        Employee employeeTwo   = createEmployee();
        Employee employeeThree = createEmployee();
        //When
        employeeRepository.save(employeeOne);
        employeeTwo.setName("Myname");
        employeeTwo.setSurname("mySurname");
        employeeRepository.save(employeeTwo);
        employeeRepository.save(employeeThree);
        Long repoCount = employeeRepository.count();
        List<EmployeeDto> result = userManagementService.findUsersByCriteria(createSearchCriteria());
        //Then
        assertEquals(3, repoCount);
        assertEquals(employeeTwo.getName(), result.get(0).getName());
        assertEquals(employeeTwo.getSurname(), result.get(0).getSurname());
        assertEquals(employeeTwo.getGrade(), result.get(0).getGrade());
        assertEquals(employeeTwo.getSalary(), result.get(0).getSalary());
    }

    private Employee createEmployee() {
        return Employee.builder()
                .name("testEmployeeName")
                .surname("testEmployeeSurname")
                .grade(22)
                .salary(23000)
                .build();
    }

    private EmployeeDto createEmployeeDto() {
        return EmployeeDto.builder()
                .name("testEmployeeName")
                .surname("testEmployeeSurname")
                .grade(22)
                .salary(23000)
                .build();
    }

    private SearchCriteria createSearchCriteria() {
        return SearchCriteria.builder()
                .processSearchCriteria(createProcessSearchCriteria())
                .processSortCriteria(createProcessSortCriteria())
                .build();
    }

    private ProcessSearchCriteria createProcessSearchCriteria() {
        return ProcessSearchCriteria.builder()
                .name("Myname")
                .surname("mySurname")
                .build();
    }

    private ProcessSortCriteria createProcessSortCriteria() {
        return ProcessSortCriteria.builder().build();
    }

}