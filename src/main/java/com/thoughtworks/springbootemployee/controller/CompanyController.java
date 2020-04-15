package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/companies")

public class CompanyController {
    @GetMapping
    public List<Company> getAllCompanies(@RequestParam(value = "page", required = false, defaultValue = "0") int page, @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize) {
        List<Company> companies = new ArrayList<>();
        List<Employee> employeesOne = new ArrayList<>();
        List<Employee> employeesTwo = new ArrayList<>();
        employeesOne.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employeesOne.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employeesOne.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employeesTwo.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employeesTwo.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        companies.add(new Company(0, "NIKE", employeesOne));
        companies.add(new Company(1, "ADIDAS", employeesTwo));

        if (page == 0 && pageSize == 0) {
            return companies;
        } else {
            return companies.stream().filter(company -> company.getCompanyId() >= (page - 1) * pageSize && company.getCompanyId() < page * pageSize).collect(Collectors.toList());
        }
    }

    @GetMapping(path = "/{companyId}")
    public Company getCompanyById(@PathVariable("companyId") int companyId) {
        List<Company> companies = new ArrayList<>();
        List<Employee> employeesOne = new ArrayList<>();
        List<Employee> employeesTwo = new ArrayList<>();
        Company targetCompany = null;
        employeesOne.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employeesOne.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employeesOne.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employeesTwo.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employeesTwo.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        companies.add(new Company(0, "NIKE", employeesOne));
        companies.add(new Company(1, "ADIDAS", employeesTwo));

        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                targetCompany = company;
            }
        }
        return targetCompany;
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> getCompanyEmployeesById(@PathVariable("companyId") int companyId) {
        List<Company> companies = new ArrayList<>();
        List<Employee> employeesOne = new ArrayList<>();
        List<Employee> employeesTwo = new ArrayList<>();
        Company targetCompany = null;
        employeesOne.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        employeesOne.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        employeesOne.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        employeesTwo.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        employeesTwo.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        companies.add(new Company(0, "NIKE", employeesOne));
        companies.add(new Company(1, "ADIDAS", employeesTwo));

        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                targetCompany = company;
            }
        }
        return targetCompany.getEmployees();
    }
}


