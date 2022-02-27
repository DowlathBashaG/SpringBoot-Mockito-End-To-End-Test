package io.dowlath.employee.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dowlath.employee.model.Employee;
import io.dowlath.employee.repository.EmployeeRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/***
 * Author : Dowlath Basha G
 * Date   : 2/27/2022
 * Time   : 2:16 PM
 ***/

// Mocking is not used in integration test. Directly dealing with database.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerIntegration_Using_Test_ContainersTests extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    @Test
    @DisplayName("Junit Test case for Save Employee")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given -> precondition or setup
        Employee employee = Employee.builder()
                           .firstName("Dowlath")
                           .lastName("Basha")
                           .email("dowlath@mail.com")
                           .build();

        /* Mocking is not used in integration test. so remove mocking here, thats what its comments
        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));*/

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
        employeeRepository.saveAll(listOfEmployees);

        /* remove mocking statements
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);*/

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
        Employee employee = Employee.builder()
                            .firstName("Dowlath")
                            .lastName("Basha")
                            .email("dowlath@mail.com")
                            .build();
        employeeRepository.save(employee);
        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

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
        Employee employee = Employee.builder()
                            .firstName("Dowlath")
                            .lastName("Basha")
                            .email("dowlath@mail.com")
                            .build();
        employeeRepository.save(employee);
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

        Employee savedEmployee = Employee.builder()
                                 .firstName("Ariz")
                                 .lastName("Dowlath")
                                 .email("ariz@mail.com")
                                 .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                                   .firstName("Arsh")
                                   .lastName("Dowlath")
                                   .email("arsh@mail.com")
                                   .build();

        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
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
        Employee savedEmployee = Employee.builder()
                                 .firstName("Ariz")
                                 .lastName("Dowlath")
                                 .email("ariz@mail.com")
                                 .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder().firstName("Arsh").lastName("Dowlath").email("arsh@mail.com").build();


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

        Employee savedEmployee = Employee.builder()
                .firstName("Ariz")
                .lastName("Dowlath")
                .email("ariz@mail.com")
                .build();
        employeeRepository.save(savedEmployee);


        // when  - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}",savedEmployee.getId()));

        // then  - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }



}
