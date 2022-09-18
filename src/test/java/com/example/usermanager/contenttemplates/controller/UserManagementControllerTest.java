package com.example.usermanager.contenttemplates.controller;

import com.example.usermanager.contenttemplates.dto.EmployeeDto;
import com.example.usermanager.contenttemplates.functions.ProcessSearchCriteria;
import com.example.usermanager.contenttemplates.functions.ProcessSortCriteria;
import com.example.usermanager.contenttemplates.functions.SearchCriteria;
import com.example.usermanager.contenttemplates.services.UserManagementService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import utility.ControllerTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
@ContextConfiguration(classes = { UserManagementController.class})
class UserManagementControllerTest {

    private static final String CONTENT_DATA_URL = "/api/usermanagement";

    @MockBean
    private UserManagementService userManagementService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void findUserById() {
        //Given
        String endpointUrl = "/finduserbyid";
        String userId = "1";
        EmployeeDto employeeDto = createEmployeeDto();
        when(userManagementService.findUserById(anyInt())).thenReturn(employeeDto);
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTENT_DATA_URL + endpointUrl)
                        .param("userId", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper convertFromContent = new ObjectMapper();
        EmployeeDto returnPayload = convertFromContent.readValue(result.getResponse().getContentAsString(), new TypeReference<EmployeeDto>() {
        });
        //Then
        assertEquals(employeeDto.getId(), returnPayload.getId());
        assertEquals(employeeDto.getName(), returnPayload.getName());
        assertEquals(employeeDto.getSurname(), returnPayload.getSurname());
        assertEquals(employeeDto.getGrade(), returnPayload.getGrade());
        assertEquals(employeeDto.getSalary(), returnPayload.getSalary());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @SneakyThrows
    void deleteById() {
        //Given
        String endpointUrl = "/deleteuser";
        String userId = "1";
        doNothing().when(userManagementService).deleteUser(anyInt());
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(CONTENT_DATA_URL + endpointUrl)
                        .param("userId", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @SneakyThrows
    void AddEmployee() {
        //Given
        String endpointUrl = "/addemployee";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EmployeeDto employeeDto = createEmployeeDto();
        doNothing().when(userManagementService).addEmployee(any(EmployeeDto.class));
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CONTENT_DATA_URL + endpointUrl)
                        .content(objectMapper.writeValueAsString(employeeDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @SneakyThrows
    void updateEmployee() {
        //Given
        String endpointUrl = "/updateuser";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EmployeeDto employeeDto = createEmployeeDto();
        doNothing().when(userManagementService).updateUser(any(EmployeeDto.class));
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(CONTENT_DATA_URL + endpointUrl)
                        .content(objectMapper.writeValueAsString(employeeDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //Then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    @SneakyThrows
    void findUserByCriteria() {
        //Given
        String endpointUrl = "/finduserbycriteria";
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        EmployeeDto employeeDto = createEmployeeDto();
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        employeeDtoList.add(employeeDto);
        when( userManagementService.findUsersByCriteria(any(SearchCriteria.class))).thenReturn(employeeDtoList);
        //When
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(CONTENT_DATA_URL + endpointUrl)
                        .content(objectMapper.writeValueAsString(createSearchCriteria()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper convertFromContent = new ObjectMapper();
        List<EmployeeDto> returnPayload = convertFromContent.readValue(result.getResponse().getContentAsString(), new TypeReference<List<EmployeeDto>>() {
        });
        //Then
        assertEquals(employeeDto.getId(), returnPayload.get(0).getId());
        assertEquals(employeeDto.getName(), returnPayload.get(0).getName());
        assertEquals(employeeDto.getSurname(), returnPayload.get(0).getSurname());
        assertEquals(employeeDto.getGrade(), returnPayload.get(0).getGrade());
        assertEquals(employeeDto.getSalary(), returnPayload.get(0).getSalary());
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
    private EmployeeDto createEmployeeDto() {
        return EmployeeDto.builder()
                .id(1)
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