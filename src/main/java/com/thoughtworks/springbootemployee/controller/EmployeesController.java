package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
    @Autowired
    EmployeeService service;

    @GetMapping
    public List<Employee> getAllEmployees(@RequestParam(value = "gender", required = false, defaultValue = "All") String gender,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize) {
        return service.getAll(gender, page, pageSize);
    }

    @GetMapping(path = "/{employeeID}")
    public Employee getEmployeeByID(@PathVariable("employeeID") Integer id) {
        return service.getEmployeeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Employee> addEmployee(@RequestBody Employee newEmployee) {
        return service.addNewEmployee(newEmployee);
    }

    @PutMapping(path = "/{employeeId}")
    public List<Employee> modifyEmployee(@PathVariable("employeeId") Integer employeeId, @RequestBody Employee newEmployee) {
        return service.modifyEmployee(employeeId, newEmployee);
    }

    @DeleteMapping(path = "/{employeeId}")
    public void deleteEmployee(@PathVariable("employeeId") Integer employeeId) {
        service.deleteEmployee(employeeId);
    }
}


