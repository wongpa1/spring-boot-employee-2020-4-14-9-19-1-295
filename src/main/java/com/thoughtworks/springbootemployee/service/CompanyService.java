package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository repository;

    public List<Company> getAll(Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            return repository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
        }
        return repository.findAll();
    }

    public Company getCompanyById(Integer companyId) {
        return repository.findById(companyId).orElse(null);
    }

    public List<Employee> getEmployeesByCompanyId(Integer companyId) {
        Company company = repository.findById(companyId).orElse(null);
        if (company != null) {
            return company.getEmployees();
        }
        return null;
    }

    public Company addNewCompany(Company newAddCompany) {
        return repository.save(newAddCompany);
    }

    public Company modifyCompany(Integer companyId, Company newCompany) {
        Company targetedCompany = repository.findById(companyId).orElse(null);

        if (targetedCompany == null) {
            return null;
        }

        if (newCompany.getCompanyName() != null) {
            targetedCompany.setCompanyName(newCompany.getCompanyName());
        }
        if (newCompany.getEmployees() != null) {
            targetedCompany.setEmployees(newCompany.getEmployees());
        }

        return repository.save(targetedCompany);
    }

    public void deleteCompany(Integer companyId) {
        repository.deleteById(companyId);
    }
}

