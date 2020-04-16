package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository repository;

    public List<Company> getAll(Integer page, Integer pageSize) {
        if (page != 0 && pageSize != 0) {
            return repository.findCompanyByPage(page, pageSize);
        }
        return repository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        return repository.findCompanyById(companyId);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        Company company = repository.findCompanyById(companyId);
        return repository.findEmployees(company);
    }


    public List<Company> addNewCompany(Company newAddCompany) {
        return repository.add(newAddCompany);
    }

    public List<Company> modifyCompany(Integer companyId, Company newCompany) {
        Company targetedCompany = repository.findCompanyById(companyId);
        if (newCompany.getCompanyName() != null) {
            targetedCompany.setCompanyName(newCompany.getCompanyName());
        }
        if (newCompany.getEmployees() != null) {
            targetedCompany.setEmployees(newCompany.getEmployees());
        }
        return repository.save(targetedCompany);
    }

    public void deleteCompany(Integer companyId) {
        Company targetedCompany = repository.findCompanyById(companyId);
        repository.delete(targetedCompany);
    }
}

