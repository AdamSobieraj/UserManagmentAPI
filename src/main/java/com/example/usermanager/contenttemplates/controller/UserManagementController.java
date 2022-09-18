package com.example.usermanager.contenttemplates.controller;

import com.example.usermanager.contenttemplates.dto.EmployeeDto;
import com.example.usermanager.contenttemplates.functions.SearchCriteria;
import com.example.usermanager.contenttemplates.services.UserManagementService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(
        tags = { "User Management Controller" },
        description = "Manage BPM Processes")
@RestController
@RequestMapping(path = "/api/usermanagement")
@AllArgsConstructor
public class UserManagementController {

    private UserManagementService userManagementService;


    @ApiOperation(
            value = "Returns Employee for specified id",
            tags = { "User Management Controller" }
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User found successfully"),
            @ApiResponse(code = 404, message = "Indicates the requested process instance was not found.")
    })
    @GetMapping(value = "/finduserbyid")
    ResponseEntity<?> findUserById(@ApiParam(required = true, name = "userId", example = "1") @RequestParam Integer userId) {
        EmployeeDto employeeDto = userManagementService.findUserById(userId);
        return ResponseEntity.ok(employeeDto);

    }

    @ApiOperation(
            value = "Delete employee by id",
            tags = { "User Management Controller" }
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee deleted successfully"),
            @ApiResponse(code = 404, message = "Indicates the requested process instance was not found.")
    })
    @DeleteMapping(value = "/deleteuser")
    ResponseEntity<?> deleteById(@ApiParam(required = true, name = "userId", example = "1") @RequestParam Integer userId) {
        userManagementService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "Adding employee to h2 DB",
            tags = { "User Management Controller" }
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee added successfully"),
            @ApiResponse(code = 404, message = "Indicates the requested process instance was not found.")
    })
    @PostMapping(value = "/addemployee")
    ResponseEntity<?> AddEmployee(@ApiParam(required = true, name = "EmployeeDto") @RequestBody EmployeeDto employeeDto) {
        userManagementService.addEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "Update employee data",
            tags = { "User Management Controller" }
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee updated successfully"),
            @ApiResponse(code = 404, message = "Indicates the requested process instance was not found.")
    })
    @PutMapping(value = "/updateuser")
    ResponseEntity<?> updateEmployee(@ApiParam(required = true, name = "EmployeeDto") @RequestBody EmployeeDto employeeDto) {
        userManagementService.updateUser(employeeDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "Search for employees",
            tags = { "User Management Controller" }
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees found successfully"),
            @ApiResponse(code = 404, message = "Indicates the requested process instance was not found.")
    })
    @GetMapping(value = "/finduserbycriteria")
    ResponseEntity<?> findUserByCriteria(@RequestBody SearchCriteria searchCriteria) {
        List<EmployeeDto> employeeDtoList = userManagementService.findUsersByCriteria(searchCriteria);
        return ResponseEntity.ok(employeeDtoList);
    }


}
