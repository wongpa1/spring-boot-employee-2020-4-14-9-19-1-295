package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
    private int companyId;
    private String companyName;
    private int employeesNumber;
    private List<Employee> employees;

    public Company(int companyId, String companyName, List<Employee> employees) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.employees = employees;
        this.employeesNumber = employees.size();
    }

    public Company() {
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        this.employeesNumber = employees.size();
    }

    public int getEmployeesNumber() {
        return employeesNumber;
    }
}
