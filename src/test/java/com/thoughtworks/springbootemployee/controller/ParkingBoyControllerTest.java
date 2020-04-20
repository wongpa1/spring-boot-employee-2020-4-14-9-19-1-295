package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
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
import java.util.stream.Collectors;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParkingBoyControllerTest {

    @Autowired
    private ParkingBoyController parkingBoyController;

    @MockBean
    private ParkingBoyRepository repository;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.standaloneSetup(parkingBoyController);
    }

    @Test
    public void shouldPrintAllParkingBoys() {
        List<ParkingBoy> expected = new ArrayList<>();
        expected.add(new ParkingBoy(1, "Pau", null, null));
        when(repository.findAll()).thenReturn(expected);

        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys");

        Assert.assertEquals(200, response.getStatusCode());

        List<ParkingBoy> employees = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, employees.size());
        Assert.assertEquals("Pau", employees.get(0).getNickName());
    }

    @Test
    public void shouldFindParkingBoyById() {
        List<ParkingBoy> parkingBoys = new ArrayList<>();
        parkingBoys.add(new ParkingBoy(1, "Pau", null, null));
        parkingBoys.add(new ParkingBoy(2, "JJay", null, null));
        Optional<ParkingBoy> expected = parkingBoys.stream().filter(employee -> employee.getId().equals(1)).findFirst();
        when(repository.findById(1)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .get("/parking-boys/1");

        Assert.assertEquals(200, response.getStatusCode());

        ParkingBoy parkingBoy = response.getBody().as(ParkingBoy.class);
        Assert.assertEquals(new Integer(1), parkingBoy.getId());
        Assert.assertEquals("Pau", parkingBoy.getNickName());
    }

    @Test
    public void shouldFindParkingBoyByNickName() {
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        parkingBoyList.add(new ParkingBoy(1, "Pau", null, null));
        parkingBoyList.add(new ParkingBoy(2, "JJay", null, null));
        List<ParkingBoy> expected = parkingBoyList.stream().filter(employee -> employee.getNickName().equals("JJay")).collect(Collectors.toList());
        when(repository.findAllByNickName("JJay")).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("nickName", "JJay")
                .when()
                .get("/parking-boys");

        Assert.assertEquals(200, response.getStatusCode());

        List<ParkingBoy> parkingBoys = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(1, parkingBoys.size());
        Assert.assertEquals("JJay", parkingBoys.get(0).getNickName());
    }

    @Test
    public void shouldPrintParkingBoyByPage() {
        List<ParkingBoy> parkingBoyList = new ArrayList<>();
        parkingBoyList.add(new ParkingBoy(1, "Pau", null, null));
        parkingBoyList.add(new ParkingBoy(2, "Johnny", null, null));
        parkingBoyList.add(new ParkingBoy(3, "George", null, null));
        parkingBoyList.add(new ParkingBoy(4, "Yoko", null, null));
        Page<ParkingBoy> expected = new PageImpl<>(parkingBoyList.subList(2, 4));
        when(repository.findAll(PageRequest.of(1, 2))).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .params("page", 2).params("pageSize", 2)
                .when()
                .get("/parking-boys");

        Assert.assertEquals(200, response.getStatusCode());

        List<ParkingBoy> parkingBoys = response.getBody().as(new TypeRef<List<ParkingBoy>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });
        Assert.assertEquals(2, parkingBoys.size());
        Assert.assertEquals("George", parkingBoys.get(0).getNickName());
    }

    @Test
    public void shouldAddParkingBoy() {
        ParkingBoy parkingBoy = new ParkingBoy(1, "Pau", null, null);
        ParkingBoy expected = parkingBoy;
        when(repository.save(parkingBoy)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(parkingBoy)
                .when()
                .post("/parking-boys");

        Assert.assertEquals(201, response.getStatusCode());

        ParkingBoy respondedParkingBoy = response.getBody().as(ParkingBoy.class);
        Assert.assertEquals("Pau", respondedParkingBoy.getNickName());
    }

    @Test
    public void shouldModifyParkingBoy() {
        ParkingBoy parkingBoy = new ParkingBoy(1, "Pau", null, null);
        ParkingBoy expected = new ParkingBoy(1, "PauL", null, null);
        when(repository.findById(1)).thenReturn(Optional.of(parkingBoy));
        when(repository.save(expected)).thenReturn(expected);
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .body(expected)
                .when()
                .put("/parking-boys/1");

        Assert.assertEquals(200, response.getStatusCode());

        ParkingBoy respondedParkingBoy = response.getBody().as(ParkingBoy.class);
        Assert.assertEquals("PauL", parkingBoy.getNickName());
        Assert.assertEquals(new Integer(1), parkingBoy.getId());
    }

    @Test
    public void shouldDeleteParkingBoy() {
        MockMvcResponse response = given().contentType(ContentType.JSON)
                .when()
                .delete("/parking-boys/1");

        Assert.assertEquals(200, response.getStatusCode());
    }
}