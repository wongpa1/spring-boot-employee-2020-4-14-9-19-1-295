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

    private void initialEmployeeList() {
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
        initialEmployeeList();
        if (!gender.equals("All")) {
            return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
        }
        if (page != 0 && pageSize != 0) {
            //TODO: use list size to do paging
            return employees.subList((page - 1) * pageSize, page * pageSize - 1);
        }
        return employees;
    }

    @GetMapping(path = "/{employeeID}")
    public Employee getEmployeeByID(@PathVariable("employeeID") int id) {

        initialEmployeeList();
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        initialEmployeeList();
        employees.add(employee);
        return employee;
    }

    @PutMapping(path = "/{employeeId}")
    public Employee modifyEmployee(@PathVariable("employeeId") int employeeId, @RequestBody Employee newEmployee) {

        initialEmployeeList();
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                employee.setId(newEmployee.getId());
                employee.setName(newEmployee.getName());
                employee.setAge(newEmployee.getAge());
                employee.setGender(newEmployee.getGender());
                employee.setSalary(newEmployee.getSalary());
                return employee;
            }
        }
        return null;
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") int employeeId) {

        initialEmployeeList();
        employees.removeIf(employee -> employee.getId() == employeeId);
    }
}


