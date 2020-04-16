package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompanyControllerTest {

    @Autowired
    private CompanyController companyController;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(companyController);
    }

    @Test
    public void shouldPrintAllCompanies() {
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
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        Company company = response.getBody().as(Company.class);
        Integer integer = 1;
        Assert.assertEquals(integer, company.getCompanyId());
        Assert.assertEquals("ADIDAS", company.getCompanyName());
    }

    @Test
    public void shouldPrintCompaniesByPage() {
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
        Assert.assertEquals("NIKE", companies.get(0).getCompanyName());
    }

    @Test
    public void shouldPrintEmployeesByCompanyId() {
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
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals("Xiaomgang", employees.get(0).getName());
    }

    @Test
    public void shouldAddCompany() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(5, "Paul", 23, "male", 2000));
        Company company = new Company(2, "PUMA", employees);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .post("/companies");

        Assert.assertEquals(201, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, companies.size());
        Assert.assertEquals("NIKE", companies.get(0).getCompanyName());
        Assert.assertEquals("PUMA", companies.get(2).getCompanyName());
    }

    @Test
    public void shouldModifyCompany() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(5, "Paul", 23, "male", 2000));
        Company company = new Company(1, "PUMA", employees);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(company)
                .when()
                .put("/companies/1");

        Assert.assertEquals(200, response.getStatusCode());

        List<Company> companies = response.getBody().as(new TypeRef<List<Company>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, companies.size());
        Assert.assertEquals("PUMA", companies.get(1).getCompanyName());
        Assert.assertEquals("Paul", companies.get(1).getEmployees().get(0).getName());
    }

    @Test
    public void shouldDeleteEmployeesByCompanyId() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/companies/1");
        Assert.assertEquals(200, response.getStatusCode());
    }


}