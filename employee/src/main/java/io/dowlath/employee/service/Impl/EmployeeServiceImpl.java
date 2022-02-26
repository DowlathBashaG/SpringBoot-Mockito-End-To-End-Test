package io.dowlath.employee.service.Impl;

import io.dowlath.employee.exception.ResourceNotFoundException;
import io.dowlath.employee.model.Employee;
import io.dowlath.employee.repository.EmployeeRepository;
import io.dowlath.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/***
 * Author : Dowlath Basha G
 * Date   : 2/22/2022
 * Time   : 10:20 PM
 ***/
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employee.getEmail());
        if(optionalEmployee.isPresent()){
            throw new ResourceNotFoundException("Email already exist in the given email"+employee.getEmail());
        }
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
