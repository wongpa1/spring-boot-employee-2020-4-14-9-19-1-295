package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll(String gender, Integer page, Integer pageSize) {
        if (!gender.equals("All")) {
            return repository.findAllByGender(gender);
        }
        if (page != null && pageSize != null) {
            return repository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
        }
        return repository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public Employee addNewEmployee(Employee newEmployee) {
        return repository.save(newEmployee);
    }

    public Employee modifyEmployee(Integer employeeId, Employee newEmployee) {
        Employee existingEmployee = repository.findById(employeeId).orElse(null);

        if (existingEmployee == null) {
            return null;
        }

        if (newEmployee.getName() != null) {
            existingEmployee.setName(newEmployee.getName());
        }
        if (newEmployee.getAge() != null) {
            existingEmployee.setAge(newEmployee.getAge());
        }
        if (newEmployee.getGender() != null) {
            existingEmployee.setGender(newEmployee.getGender());
        }
        if (newEmployee.getSalary() != null) {
            existingEmployee.setSalary(newEmployee.getSalary());
        }

        return repository.save(existingEmployee);
    }

    public void deleteEmployee(Integer employeeId) {
        repository.deleteById(employeeId);
    }
}
