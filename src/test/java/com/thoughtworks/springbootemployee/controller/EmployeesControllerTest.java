package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeesControllerTest {

    @Autowired
    private EmployeesController employeesController;

    @MockBean
    private EmployeeRepository repository;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(employeesController);
    }

    @Test
    public void shouldPrintAllEmployees() {
        List<Employee> expected = new ArrayList<>();
        expected.add(new Employee(1, "Paul", 20, "Male", 2000, 1));
        when(repository.findAll()).thenReturn(expected);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees");

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
    public void shouldFindEmployeeById() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Paul", 20, "male", 2000, 1));
        Optional<Employee> expected = employees.stream().filter(employee -> employee.getId().equals(1)).findFirst();
        when(repository.findById(1)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee employee = response.getBody().as(Employee.class);
        Assert.assertEquals(new Integer(1), employee.getId());
        Assert.assertEquals("Paul", employee.getName());
    }

    @Test
    public void shouldFindEmployeeByGender() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "male", 2000, 1));
        employeeList.add(new Employee(2, "John", 22, "male", 2000, 2));
        employeeList.add(new Employee(3, "George", 20, "male", 2000, 1));
        employeeList.add(new Employee(5, "Yoko", 19, "female", 2000, 2));
        List<Employee> expected = employeeList.stream().filter(employee -> employee.getGender().equals("male")).collect(Collectors.toList());
        when(repository.findAllByGender("male")).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("gender", "male")
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(3, employees.size());
        Assert.assertEquals("Paul", employees.get(0).getName());
    }

    @Test
    public void shouldPrintEmployeeByPage() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1, "Paul", 20, "male", 2000, 1));
        employeeList.add(new Employee(2, "John", 22, "male", 2000, 2));
        employeeList.add(new Employee(3, "George", 20, "male", 2000, 1));
        employeeList.add(new Employee(5, "Yoko", 19, "female", 2000, 2));
        List<Employee> expected = employeeList.subList(2, 4);
        when(repository.findAll(PageRequest.of(1, 2)).getContent()).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", 2).params("pageSize", 2)
                .when()
                .get("/employees");

        Assert.assertEquals(200, response.getStatusCode());

        List<Employee> employees = response.getBody().as(new TypeRef<List<Employee>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, employees.size());
        Assert.assertEquals("George", employees.get(0).getName());
    }

    @Test
    public void shouldAddEmployee() {
        Employee employee = new Employee(1, "Paul", 20, "male", 2000, 1);
        Employee expected = employee;
        when(repository.save(employee)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(employee)
                .when()
                .post("/employees");

        Assert.assertEquals(201, response.getStatusCode());

        Employee respondedEmployee = response.getBody().as(Employee.class);
        Assert.assertEquals("Paul", respondedEmployee.getName());
    }

    @Test
    public void shouldModifyEmployee() {
        Employee employee = new Employee(1, "Paul", 20, "male", 2000, 1);
        Employee expected = new Employee(1, "Pau", 21, "male", 3000, 1);
        when(repository.findById(1)).thenReturn(Optional.of(employee));
        when(repository.save(expected)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(expected)
                .when()
                .put("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());

        Employee respondedEmployee = response.getBody().as(Employee.class);
        Assert.assertEquals("Pau", employee.getName());
        Assert.assertEquals(new Integer(21), employee.getAge());
        Assert.assertEquals(new Integer(3000), employee.getSalary());
    }

    @Test
    public void shouldDeleteEmployee() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/employees/1");

        Assert.assertEquals(200, response.getStatusCode());
    }
}