package io.dowlath.employee.service;

import io.dowlath.employee.model.Employee;
import io.dowlath.employee.repository.EmployeeRepository;
import io.dowlath.employee.service.Impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThat;
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
        employee = Employee.builder().firstName("Dowlath")
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
        Employee savedEmployee= employeeService.saveEmployee(employee);
        System.out.println(savedEmployee);
        // then  - verify the output
        assertThat(savedEmployee).isNotNull();
    }


}
