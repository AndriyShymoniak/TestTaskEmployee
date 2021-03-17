package com.shymoniak.testtask.service;

import com.shymoniak.testtask.domain.EmployeeDTO;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    void addEmployee (EmployeeDTO employee) throws IOException;
    List<EmployeeDTO> getAllEmployees() throws IOException;
    Map<String,List<EmployeeDTO>> getAllDepartmentsWithEmployeesSortedBySalary();
    EmployeeDTO getMostPaidInDepartment(String department);
}
