package com.shymoniak.testtask.service;

import com.shymoniak.testtask.domain.EmployeeDTO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    void addEmployee(EmployeeDTO employee);

    List<EmployeeDTO> getAllEmployees();

    Map<String, List<EmployeeDTO>> getAllDepartmentsWithEmployeesSortedBySalary();

    EmployeeDTO getMostPaidInDepartment(String department);
}
