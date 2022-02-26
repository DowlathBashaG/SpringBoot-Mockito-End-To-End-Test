package io.dowlath.employee.service;

import io.dowlath.employee.model.Employee;

import java.util.List;
import java.util.Optional;

/***
 * Author : Dowlath Basha G
 * Date   : 2/22/2022
 * Time   : 7:38 PM
 ***/
public interface EmployeeService {
    Employee saveEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(long id);
    Employee updateEmployee(Employee updatedEmployee);
    void deleteEmployee(long id);
}
