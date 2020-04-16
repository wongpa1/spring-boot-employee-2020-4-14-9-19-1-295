package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

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

    public CompanyRepository() {
        initialCompanyList();
    }

    public List<Company> findAll() {
        return companies;
    }

    public List<Company> findCompanyByPage(Integer page, Integer pageSize) {
        return companies.subList((page - 1) * pageSize, page * pageSize);
    }

    public Company findCompanyById(Integer companyId) {
        return companies.stream().filter(employee -> employee.getCompanyId().equals(companyId))
                .findFirst()
                .orElse(null);
    }

    public List<Employee> findEmployees(Company company) {
        return company.getEmployees();
    }

    public List<Company> add(Company newAddCompany) {
        companies.add(newAddCompany);
        return companies;
    }

    public List<Company> save(Company targetedCompany) {
        companies.remove(findCompanyById(targetedCompany.getCompanyId()));
        companies.add(targetedCompany);
        return companies;
    }

    public void delete(Company targetedCompany) {
        companies.remove(targetedCompany);
    }
}
