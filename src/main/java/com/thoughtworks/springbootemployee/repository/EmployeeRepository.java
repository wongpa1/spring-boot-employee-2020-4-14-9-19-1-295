package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeRepository() {
        initialEmployeeList();
    }

    private void initialEmployeeList() {
        employees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findEmployeeById(Integer id) {
        return employees.stream().filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> findEmployeeByPage(Integer page, Integer pageSize) {
        return employees.subList((page - 1) * pageSize, page * pageSize);
    }

    public List<Employee> add(Employee newEmployee) {
        employees.add(newEmployee);
        return employees;
    }

    public List<Employee> save(Employee employee) {
        employees.remove(findEmployeeById(employee.getId()));
        employees.add(employee);
        return employees;
    }

    public void delete(Employee employee) {
        employees.remove(employee);
    }
}

