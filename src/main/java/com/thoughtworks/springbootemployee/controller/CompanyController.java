package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")

public class CompanyController {

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
    public List<Company> getAllCompanies(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                         @RequestParam(value = "pageSize", required = false, defaultValue = "0") int pageSize) {
        List<Company> companies = new ArrayList<>();
        initialCompanyList(companies);
        if (page != 0 && pageSize != 0) {
            return companies.subList((page - 1) * pageSize, page * pageSize);
        }
        return companies;
    }

    @GetMapping(path = "/{companyId}")
    public Company getCompanyById(@PathVariable("companyId") int companyId) {
        List<Company> companies = new ArrayList<>();
        initialCompanyList(companies);
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                return company;
            }
        }
        return null;
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> getCompanyEmployeesById(@PathVariable("companyId") int companyId) {
        List<Company> companies = new ArrayList<>();
        initialCompanyList(companies);
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                return company.getEmployees();
            }
        }
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Company> addNewCompany(@RequestBody Company newAddCompany) {
        List<Company> companies = new ArrayList<>();
        initialCompanyList(companies);
        companies.add(newAddCompany);
        return companies;
    }

    @PutMapping(path = "/{companyId}")
    public List<Company> modifyEmployee(@PathVariable("companyId") int companyId, @RequestBody Company newCompany) {
        List<Company> companies = new ArrayList<>();
        initialCompanyList(companies);
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                company.setCompanyId(newCompany.getCompanyId());
                company.setCompanyName(newCompany.getCompanyName());
                company.setEmployees(newCompany.getEmployees());
            }
        }
        return companies;
    }

    @DeleteMapping(path = "/{companyId}")
    public void deleteEmployeesBelongToCompany(@PathVariable("companyId") int companyId) {
        List<Company> companies = new ArrayList<>();
        initialCompanyList(companies);
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                company.setEmployees(new ArrayList<>());
            }
        }
    }
}


