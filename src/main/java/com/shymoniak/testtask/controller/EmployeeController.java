package com.shymoniak.testtask.controller;

import com.shymoniak.testtask.domain.EmployeeDTO;
import com.shymoniak.testtask.exception.ApiRequestException;
import com.shymoniak.testtask.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("employee")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @GetMapping({"", "/"})
    ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return new ResponseEntity<>(service.getAllEmployees(), HttpStatus.OK);
    }

    @GetMapping("/getDepartmentsWithSortedSalary/")
    ResponseEntity<Map<String, List<EmployeeDTO>>> getDepartmentsWithSortedSalary() {
        return new ResponseEntity<>(
                service.getAllDepartmentsWithEmployeesSortedBySalary(),
                HttpStatus.OK);
    }

    @GetMapping("/getWithHighestSalaryInDepartment/{department}")
    ResponseEntity<EmployeeDTO> getWithHighestSalaryInDepartment(
            @PathVariable("department") String department) {
        if (department.matches("^[a-zA-Z ]+$")) {
            return new ResponseEntity<>(service.getMostPaidInDepartment(department),
                    HttpStatus.OK);
        } else {
            throw new ApiRequestException("Department name should only " +
                    "consist of letters");
        }
    }

    @PostMapping
    ResponseEntity<Void> addEmployee(@RequestBody EmployeeDTO employee) {
        service.addEmployee(employee);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
