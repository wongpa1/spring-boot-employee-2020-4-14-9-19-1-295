package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.service.ParkingBoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking-boys")
public class ParkingBoyController {
    @Autowired
    ParkingBoyService service;

    @GetMapping
    public List<ParkingBoy> getAllParkingBoy(@RequestParam(value = "nickName", required = false) String nickName,
                                             @RequestParam(value = "page", required = false) Integer page,
                                             @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return service.getAll(nickName, page, pageSize);
    }

    @GetMapping(path = "/{ID}")
    public ParkingBoy getParkingByID(@PathVariable("ID") Integer id) {
        return service.getParkingBoyById(id);
    }

    @GetMapping(path = "/{Id}/employee")
    public Employee getParkingBoyEmployeeById(@PathVariable("Id") Integer Id) {
        return service.getParkingBoyEmployeeById(Id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingBoy addNewParkingBoy(@RequestBody ParkingBoy newParkingBoy) {
        return service.addNewParkingBoy(newParkingBoy);
    }

    @PutMapping(path = "/{Id}")
    public ParkingBoy modifyEmployee(@PathVariable("Id") Integer Id, @RequestBody ParkingBoy newParkingBoy) {
        return service.modifyParkingBoy(Id, newParkingBoy);
    }

    @DeleteMapping(path = "/{Id}")
    public void deleteEmployee(@PathVariable("Id") Integer Id) {
        service.deleteParkingBoy(Id);
    }
}

