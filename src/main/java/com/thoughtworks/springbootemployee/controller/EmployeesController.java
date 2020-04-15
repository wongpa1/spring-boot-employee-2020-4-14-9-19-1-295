package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value="gender",required = false) String gender){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        if(gender.isEmpty()) {
            return employees;
        }
        else{
            return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        }
    }

    @GetMapping(path = "/{employeeID}")
    public Employee getEmployeeByID(@PathVariable("employeeID") int id) {
        List<Employee> employees = new ArrayList<>();
        Employee targetEmployee = null;
        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                targetEmployee = employee;
            }
        }
        return targetEmployee;
    }
}