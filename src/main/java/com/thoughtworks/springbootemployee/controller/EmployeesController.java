package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private void initialEmployeeList(List<Employee> employees) {
        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
    }

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false, defaultValue = "All") String gender,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize) {
        List<Employee> employees = new ArrayList<>();
        initialEmployeeList(employees);
        if (!gender.equals("All")) {
            return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        }
        if (page != 0 && pageSize != 0) {
            return employees.subList((page - 1) * pageSize, page * pageSize);
        }
        return employees;
    }

    @GetMapping(path = "/{employeeID}")
    public Employee getEmployeeByID(@PathVariable("employeeID") int id) {
        List<Employee> employees = new ArrayList<>();
        initialEmployeeList(employees);
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> addEmployee(@RequestBody Employee employee) {
        List<Employee> employees = new ArrayList<>();
        initialEmployeeList(employees);
        employees.add(employee);
        return employees;
    }

    @PutMapping(path = "/{employeeId}")
    public List<Employee> modifyEmployee(@PathVariable("employeeId") int employeeId, @RequestBody Employee newEmployee) {
        List<Employee> employees = new ArrayList<>();
        initialEmployeeList(employees);
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                employee.setId(newEmployee.getId());
                employee.setName(newEmployee.getName());
                employee.setAge(newEmployee.getAge());
                employee.setGender(newEmployee.getGender());
                employee.setSalary(newEmployee.getSalary());
            }
        }
        return employees;
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") int employeeId) {
        List<Employee> employees = new ArrayList<>();
        initialEmployeeList(employees);
        employees.removeIf(employee -> employee.getId() == employeeId);
    }
}


