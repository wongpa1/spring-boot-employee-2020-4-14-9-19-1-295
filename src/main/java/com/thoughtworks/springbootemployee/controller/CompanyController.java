package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")

public class CompanyController {

    List<Company> companies = new ArrayList<>();

    private void initialCompanyList() {
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
        initialCompanyList();
        if (page != 0 && pageSize != 0) {
            return companies.subList((page - 1) * pageSize, page * pageSize - 1);
        }
        return companies;
    }

    @GetMapping(path = "/{companyId}")
    public Company getCompanyById(@PathVariable("companyId") int companyId) {
        initialCompanyList();
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                return company;
            }
        }
        return null;
    }

    @GetMapping(path = "/{companyId}/employees")
    public List<Employee> getCompanyEmployeesById(@PathVariable("companyId") int companyId) {
        initialCompanyList();
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                return company.getEmployees();
            }
        }
        return null;
    }

    @PostMapping
    public Company addNewCompany(@RequestBody Company newAddCompany) {
        initialCompanyList();
        companies.add(newAddCompany);
        return newAddCompany;
    }

    @PutMapping(path = "/{companyId}")
    public Company modifyEmployee(@PathVariable("companyId") int companyId, @RequestBody Company newCompany) {
        initialCompanyList();

        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                company.setCompanyId(newCompany.getCompanyId());
                company.setCompanyName(newCompany.getCompanyName());
                company.setEmployees(newCompany.getEmployees());
                return company;
            }
        }
        return null;
    }

    @DeleteMapping(path = "/{companyId}")
    public void deleteEmployeesBelongToCompany(@PathVariable("companyId") int companyId) {
        initialCompanyList();
        for (Company company : companies) {
            if (company.getCompanyId() == companyId) {
                company.setEmployees(new ArrayList<>());
            }
        }
    }
}


