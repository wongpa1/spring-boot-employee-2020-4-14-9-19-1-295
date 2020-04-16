package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAll(String gender, int page, int pageSize) {
        if (!gender.equals("All")) {
            return repository.findEmployeeByGender(gender);
        }
        if (page != 0 && pageSize != 0) {
            return repository.findEmployeeByPage(page, pageSize);
        }
        return repository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return repository.findEmployeeById(id);
    }

    public List<Employee> addNewEmployee(Employee newEmployee) {
        return repository.add(newEmployee);
    }

    public List<Employee> modifyEmployee(Integer employeeId, Employee newEmployee) {
        Employee existingEmployee = repository.findEmployeeById(employeeId);

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
        Employee targetedEmployee = repository.findEmployeeById(employeeId);
        repository.delete(targetedEmployee);
    }
}
