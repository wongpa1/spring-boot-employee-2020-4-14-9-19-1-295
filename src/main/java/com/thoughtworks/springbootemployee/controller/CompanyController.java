package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")

public class CompanyController {

    @Autowired
    CompanyService service;

    @GetMapping
    public List<Company> getAllCompanies(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "pageSize", required = false) Integer pageSize) {
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
    public Company addNewCompany(@RequestBody Company newAddCompany) {
        return service.addNewCompany(newAddCompany);
    }

    @PutMapping(path = "/{companyId}")
    public Company modifyEmployee(@PathVariable("companyId") Integer companyId, @RequestBody Company newCompany) {
        return service.modifyCompany(companyId, newCompany);
    }

    @DeleteMapping(path = "/{companyId}")
    public void deleteEmployeesBelongToCompany(@PathVariable("companyId") Integer companyId) {
        service.deleteCompany(companyId);
    }
}


