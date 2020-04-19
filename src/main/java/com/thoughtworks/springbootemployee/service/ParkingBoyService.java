package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.ParkingBoy;
import com.thoughtworks.springbootemployee.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingBoyService {
    @Autowired
    private ParkingBoyRepository repository;

    public List<ParkingBoy> getAll(String nickName, Integer page, Integer pageSize) {
        if (nickName != null) {
            return repository.findAllByNickName(nickName);
        }
        if (page != null && pageSize != null) {
            return repository.findAll(PageRequest.of(page - 1, pageSize)).getContent();
        }
        return repository.findAll();
    }

    public ParkingBoy getParkingBoyById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public ParkingBoy addNewParkingBoy(ParkingBoy newParkingBoy) {
        return repository.save(newParkingBoy);
    }

    public ParkingBoy modifyParkingBoy(Integer id, ParkingBoy newParkingBoy) {
        ParkingBoy targetedParkingBoy = repository.findById(id).orElse(null);
        if (targetedParkingBoy == null) {
            return null;
        }
        if (newParkingBoy.getNickName() != null) {
            targetedParkingBoy.setNickName(newParkingBoy.getNickName());
        }
        if (newParkingBoy.getEmployeeId() != null) {
            targetedParkingBoy.setEmployeeId(newParkingBoy.getEmployeeId());
        }
        return repository.save(targetedParkingBoy);
    }

    public void deleteParkingBoy(Integer id) {
        repository.deleteById(id);
    }

    public Employee getParkingBoyEmployeeById(Integer id) {
        ParkingBoy parkingBoy = repository.findById(id).orElse(null);
        if (parkingBoy != null) {
            return parkingBoy.getEmployee();
        }
        return null;
    }
}
