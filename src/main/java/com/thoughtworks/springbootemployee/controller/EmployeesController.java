package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false, defaultValue = "All") String gender,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize) {

        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        if (!gender.equals("All")) {
            return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        }
        if (page != 0 && pageSize != 0) {
            return employees.stream().filter(employee -> employee.getId() >= (page - 1) * pageSize && employee.getId() < page * pageSize).collect(Collectors.toList());
        }
        return employees;
    }

    @GetMapping(path = "/{employeeID}")
    public Employee getEmployeeByID(@PathVariable("employeeID") int id) {

        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @PostMapping
    public Employee addEmployee(@RequestParam(value = "id") int employeeId, @RequestParam(value = "name") String employeeName, @RequestParam(value = "age") int employeeAge,
                                @RequestParam(value = "gender") String employeeGender, @RequestParam(value = "salary") int employeeSalary) {

        Employee addEmployee = new Employee(employeeId, employeeName, employeeAge, employeeGender, employeeSalary);
        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        employees.add(addEmployee);
        return addEmployee;
    }

    @PutMapping(path = "/{employeeId}")
    public Employee modifyEmployee(@PathVariable("employeeId") int employeeId, @RequestParam(value = "name") String employeeName, @RequestParam(value = "age") int employeeAge, @RequestParam(value = "gender") String employeeGender, @RequestParam(value = "salary") int employeeSalary) {

        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                employee.setName(employeeName);
                employee.setAge(employeeAge);
                employee.setGender(employeeGender);
                employee.setSalary(employeeSalary);
                return employee;
            }
        }
        return null;
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") int employeeId) {

        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        employees.removeIf(employee -> employee.getId() == employeeId);
    }
}


