package io.dowlath.employee.controller;

import io.dowlath.employee.model.Employee;
import io.dowlath.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/***
 * Author : Dowlath Basha G
 * Date   : 2/27/2022
 * Time   : 1:27 AM
 ***/
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }


}
