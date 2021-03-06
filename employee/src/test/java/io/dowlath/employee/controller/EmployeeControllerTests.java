package io.dowlath.employee.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dowlath.employee.model.Employee;
import io.dowlath.employee.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


/***
 * Author : Dowlath Basha G
 * Date   : 2/27/2022
 * Time   : 2:03 AM
 ***/
@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Junit Test case for Save Employee")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given -> precondition or setup
        Employee employee = Employee.builder()
                .firstName("Dowlath")
                .lastName("Basha")
                .email("dowlath@mail.com")
                .build();
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when -> action or behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then -> verify the result or output using assert statements
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())));
    }

    // JUnit test for GET all employees method
    @Test
    @DisplayName("Junit Test case for Get all employees")
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        // given - precondition or setup
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("Dowlath").lastName("Basha").email("dowlath@mail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Ariz").lastName("Dowlath").email("ariz@mail.com").build());
        listOfEmployees.add(Employee.builder().firstName("Arsh").lastName("Dowlath").email("arsh@mail.com").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(listOfEmployees.size())));
    }

    // Positive scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    @DisplayName("Junit test case for GET employee by id - Positive Scenario")
    public void givenEmployeeId_whenGetEmployeeId_thenReturnEmployeeObject() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha")
                .email("dowlath@mail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    // Negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    @DisplayName("Junit test case for GET employee by id - Negative Scenario")
    public void givenInvalidEmployeeId_whenGetEmployeeId_thenReturnEmpty() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha")
                .email("dowlath@mail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }

    // JUnit test for UPDATE employee rest api - positive scenario
    @Test
    @DisplayName("Junit test case for UPDATE employee - Positive Scenario")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder().firstName("Ariz").lastName("Dowlath").email("ariz@mail.com").build();
        Employee updatedEmployee = Employee.builder().firstName("Arsh").lastName("Dowlath").email("arsh@mail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));
    }

    // JUnit test for UPDATE employee rest api - negative scenario
    @Test
    @DisplayName("Junit test case for UPDATE employee - Negative Scenario")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder().firstName("Ariz").lastName("Dowlath").email("ariz@mail.com").build();
        Employee updatedEmployee = Employee.builder().firstName("Arsh").lastName("Dowlath").email("arsh@mail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    // JUnit test for DELETE employee REST API
    @Test
    @DisplayName("Junit test case for DELETE employee")
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        // given - precondition or setup
        long employeeId = 1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}",employeeId));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
