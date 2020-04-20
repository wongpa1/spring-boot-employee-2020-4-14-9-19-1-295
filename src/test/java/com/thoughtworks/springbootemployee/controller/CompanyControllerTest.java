package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @MockBean
    private CompanyRepository repository;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(companyController);
    }

    @Test
    public void shouldPrintAllCompanies() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "Male", 2000, 1));
        List<Company> expected = new ArrayList<>();
        expected.add(new Company(1, "NIKE", 1, employeeList));
        expected.add(new Company(2, "ADIDAS", 0, null));
        when(repository.findAll()).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies");

        Assert.assertEquals(200, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("NIKE", companies.get(0).getCompanyName());
    }

    @Test
    public void shouldFindCompanyById() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "Male", 2000, 1));
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "NIKE", 1, employeeList));
        companies.add(new Company(2, "ADIDAS", 0, null));

        Optional<Company> expected = companies.stream().filter(company -> company.getId().equals(2)).findFirst();
        when(repository.findById(2)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/2");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(new Integer(2), company.getId());
        Assert.assertEquals("ADIDAS", company.getCompanyName());
    }

    @Test
    public void shouldPrintCompaniesByPage() {
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "NIKE", null, null));
        companyList.add(new Company(2, "ADIDAS", null, null));
        companyList.add(new Company(3, "PUMA", null, null));
        companyList.add(new Company(4, "NEW_BALANCE", null, null));
        Page<Company> expected = new PageImpl<>(companyList.subList(2, 4));
        when(repository.findAll(PageRequest.of(0, 2))).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", 1).params("pageSize", 2)
                .when()
                .get("/companies");

        Assert.assertEquals(200, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("PUMA", companies.get(0).getCompanyName());
    }

    @Test
    public void shouldPrintEmployeesByCompanyId() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "Male", 2000, 1));
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1, "NIKE", 1, employeeList));
        companies.add(new Company(2, "ADIDAS", 0, null));

        Optional<Company> expected = companies.stream().filter(company -> company.getId().equals(1)).findFirst();
        when(repository.findById(1)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals("Paul", employees.get(0).getName());
    }

    @Test
    public void shouldAddCompany() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "Male", 2000, 1));
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "NIKE", 1, employeeList));
        companyList.add(new Company(2, "ADIDAS", 0, null));

        Company newCompany = new Company(3, "PUMA", 0, null);
        Company expected = newCompany;
        when(repository.save(newCompany)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(newCompany)
                .when()
                .post("/companies");

        Assert.assertEquals(201, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(new Integer(3), company.getId());
        Assert.assertEquals("PUMA", company.getCompanyName());
    }

    @Test
    public void shouldModifyCompany() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "Male", 2000, 1));
        List<Company> companyList = new ArrayList<>();
        companyList.add(new Company(1, "NIKE", 1, employeeList));
        companyList.add(new Company(2, "ADIDAS", 0, null));

        Optional<Company> expected = companyList.stream().filter(company -> company.getId().equals(2)).findFirst();
        Company modifyCompany = new Company(2, "PUMA", 0, null);
        when(repository.findById(2)).thenReturn(expected);
        when(repository.save(modifyCompany)).thenReturn(modifyCompany);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(modifyCompany)
                .when()
                .put("/companies/2");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Assert.assertEquals(new Integer(2), company.getId());
        Assert.assertEquals("PUMA", company.getCompanyName());
    }

    @Test
    public void shouldDeleteEmployeesByCompanyId() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");
        Assert.assertEquals(200, response.getStatusCode());
    }
}