package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")

public class CompanyController {
    
    @Autowired
    CompanyService service;

    List<Company> companies = new ArrayList<>();

    private void initialCompanyList(List<Company> companies) {
        List<Employee> nikeEmployees = new ArrayList<>();
        List<Employee> adidasEmployees = new ArrayList<>();
        nikeEmployees.add(new Employee(0, "Xiaoming", 20, "male", 20000));
        nikeEmployees.add(new Employee(1, "Xiaohong", 19, "female", 20000));
        nikeEmployees.add(new Employee(2, "Xiaozhi", 15, "male", 20000));
        adidasEmployees.add(new Employee(3, "Xiaomgang", 26, "male", 20000));
        adidasEmployees.add(new Employee(4, "Xiaoxia", 15, "female", 20000));
        companies.add(new Company(0, "NIKE", nikeEmployees));
        companies.add(new Company(1, "ADIDAS", adidasEmployees));
    }

    @GetMapping
    public List<Company> getAllCompanies(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "0") Integer pageSize) {
        return service.getAll(page, pageSize);
    }

    @GetMapping(path = "/{companyId}")
    public Company getCompanyById(@PathVariable("companyId") Integer companyId) {
        return service.getCompanyById(companyId);
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> getCompanyEmployeesById(@PathVariable("companyId") Integer companyId) {
        return service.getEmployeesByCompanyId(companyId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> addNewCompany(@RequestBody Company newAddCompany) {
        return service.addNewCompany(newAddCompany);
    }

    @PutMapping(path = "/{companyId}")
    public List<Company> modifyEmployee(@PathVariable("companyId") Integer companyId, @RequestBody Company newCompany) {
        return service.modifyCompany(companyId,newCompany);
    }

    @DeleteMapping(path = "/{companyId}")
    public void deleteEmployeesBelongToCompany(@PathVariable("companyId") Integer companyId) {
        service.deleteCompany(companyId);
    }
}


