package io.dowlath.employee.repository;

import io.dowlath.employee.model.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/***
 * Author : Dowlath Basha G
 * Date   : 2/21/2022
 * Time   : 10:35 PM
 ***/
@DataJpaTest
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryITTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("a@email.comn").build();
    }

    // Junit test for save employee operation
    @DisplayName("Junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //Given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("a@email.comn").build();*/
        //When ( Action or Behaviour are going test )
        Employee savedEmployee = employeeRepository.save(employee);
        //Then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    // JUnit test for get all employees operation
    @Test
    @DisplayName("JUnit test for get all employees operation")
    public void givenEmployeeList_whenFindAll_thenEmployeesList() {
        // given - precondition or setup
        Employee employee1 = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);


        // when  - action or the behaviour that we are going test
        List<Employee> employeeList = employeeRepository.findAll();

        // then  - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(3);

    }

    // JUnit test for get employee by id operation
    @Test
    @DisplayName("JUnit test for get employee by id operation ")
    public void givenEmployeeObject_whenFindById_thenExmployeeObject() {
        // given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/

        employeeRepository.save(employee);

        // when  - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then  - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for get employee by email operation
    @Test
    @DisplayName("JUnit test for get employee by email operation")
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {
        // given - precondition or setup
       /* Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/
        employeeRepository.save(employee);
        // when  - action or the behaviour that we are going test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();
        // then  - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test for update employee operation
    @Test
    @DisplayName("JUnit test for update employee operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        // given - precondition or setup
       /* Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/
        employeeRepository.save(employee);
        // when  - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("dowlathbasha@gmail.com");
        savedEmployee.setLastName("Basha");
        Employee updateEmployee = employeeRepository.save(savedEmployee);

        // then  - verify the output
        assertThat(updateEmployee.getEmail()).isEqualTo("dowlathbasha@gmail.com");
        assertThat(updateEmployee.getLastName()).isEqualTo("Basha");

    }

    // JUnit test for delete employee operation
    @Test
    @DisplayName("JUnit test for delete employee operation")
    public void givenEmployee_whenDelete_thenRemoveEmployee() {
        // given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/
        employeeRepository.save(employee);
        // when  - action or the behaviour that we are going test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());
        // then  - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // JUnit test for custom query using JPQL by index
    @Test
    @DisplayName("JUnit test for custom query using JPQL by index")
    public void givenEmployeeObject_whenFindByJPQL_thenReturnEmployeeObject() {
        // given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/
        employeeRepository.save(employee);
        String firstName = "Dowlath";
        String lastName = "Basha G";
        // when  - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName,lastName);
        // then  - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using JPQL by index
    @Test
    @DisplayName("JUnit test for custom query using JPQL by named param")
    public void givenEmployeeObject_whenFindByJPQLNamedParam_thenReturnEmployeeObject() {
        // given - precondition or setup
        /*Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/
        employeeRepository.save(employee);
        String firstName = "Dowlath";
        String lastName = "Basha G";
        // when  - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName,lastName);
        // then  - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using JPQL by index
    @Test
    @DisplayName("JUnit test for custom query using Native by index")
    public void givenEmployeeObject_whenFindByNativeQuery_thenReturnEmployeeObject() {
        // given - precondition or setup
       /* Employee employee = Employee.builder().firstName("Dowlath")
                .lastName("Basha G").email("dowlath@email.comn").build();*/
        employeeRepository.save(employee);
        // when  - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(),employee.getLastName());
        // verify
        assertThat(savedEmployee).isNotNull();

    }

    // JUnit test for custom query using JPQL by index
    @Test
    @DisplayName("JUnit test for custom query using Native by index")
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParama_thenReturnEmployeeObject() {
        // given - precondition or setup
        employeeRepository.save(employee);
        // when  - action or the behaviour that we are going test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(),employee.getLastName());
        // verify
        assertThat(savedEmployee).isNotNull();

    }


}
