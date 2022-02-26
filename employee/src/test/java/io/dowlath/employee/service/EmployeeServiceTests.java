package io.dowlath.employee.service;

import io.dowlath.employee.exception.ResourceNotFoundException;
import io.dowlath.employee.model.Employee;
import io.dowlath.employee.repository.EmployeeRepository;
import io.dowlath.employee.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


/***
 * Author : Dowlath Basha G
 * Date   : 2/23/2022
 * Time   : 1:01 AM
 ***/
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
        /*employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);*/
        employee = Employee.builder()
                .id(1L)
                .firstName("Dowlath")
                .lastName("Basha G").email("dowlath@mail.com").build();
    }

    // JUnit test for saving for employee
    @Test
    @DisplayName("JUnit test for saving for employee")
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                            .lastName("Basha G").email("dowlath@mail.com").build();*/
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);
        System.out.println(employeeRepository);
        System.out.println(employeeService);
        // when  - action or the behaviour that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);
        // then  - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for saving for employee throws exception
    @Test
    @DisplayName("JUnit test for saving for employee throws exception")
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

        // given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                            .lastName("Basha G").email("dowlath@mail.com").build();*/

        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        System.out.println(employeeRepository);
        System.out.println(employeeService);

        // when  - action or the behaviour that we are going test
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        // then
        verify(employeeRepository, never()).save(any(Employee.class));

    }

    // JUnit test for get all employees
    @DisplayName("JUnit test for get all employees")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Ariz")
                .lastName("Dowlath").email("arizdowlath@mail.com").build();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee1);
        given(employeeRepository.findAll()).willReturn(employeeList);

        // when  - action or the behaviour that we are going test
        List<Employee> empList = employeeService.getAllEmployees();
        // then  - verify the output
        assertThat(empList).isNotNull();
        assertThat(empList.size()).isEqualTo(2);

    }

    // JUnit test for get all employees negative scenario
    @DisplayName("JUnit test for get all employees failure (negative scenario) scenario - empty list")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Ariz")
                .lastName("Dowlath").email("arizdowlath@mail.com").build();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee);
        employeeList.add(employee1);
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        // when  - action or the behaviour that we are going test
        List<Employee> empList = employeeService.getAllEmployees();
        // then  - verify the output
        assertThat(empList).isEmpty();
        assertThat(empList.size()).isEqualTo(0);

    }

    // JUnit test for get employee by id
    @DisplayName("JUnit test for get employee by id")
    @Test
    public void givenEmployeeId_whenGetEmployeeId_thenReturnEmployeeObject() {
        // given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when  - action or the behaviour that we are going test
        //Optional<Employee> emp = employeeService.getEmployeeById(1L);
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();
        // then  - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for updated employee
    @DisplayName("Junit test case for updated employee")
    @Test
    public void giveEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("arsh@mail.com");
        employee.setFirstName("ariz");
        // when  - action or the behaviour that we are going test
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        // then  - verify the output
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getEmail()).isEqualTo("arsh@mail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("ariz");
    }

    // JUnit test for delete by id (employee details)
    @Test
    @DisplayName("Junit test case for delete employee by id")
    public void givenEmployeeObject_whenEmployeeObjectDelete_thenNothing() {
        long employeeId = 1L;
        // given - precondition or setup
        // Note:
        // if void nothing is return - for BDD should use : " willDoNothing() "
        willDoNothing().given(employeeRepository).deleteById(employeeId);
        // when  - action or the behaviour that we are going test
        employeeService.deleteEmployee(employeeId);
        // then  - verify the output
        //Note :
        // deleteby id - return type is void,so we can't verify,
        // but will do how many times deletebyid called.in our case only one time its called.
        verify(employeeRepository,times(1)).deleteById(employeeId);

    }


}
