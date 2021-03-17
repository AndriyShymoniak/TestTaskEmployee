package com.shymoniak.testtask.service.impl;

import com.shymoniak.testtask.domain.EmployeeDTO;
import com.shymoniak.testtask.entity.Employee;
import com.shymoniak.testtask.exception.ApiRequestException;
import com.shymoniak.testtask.service.EmployeeService;
import com.shymoniak.testtask.serializer.CsvSerializer;
import com.shymoniak.testtask.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shymoniak.testtask.constant.ApplicationConstants.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private ObjectMapperUtils modelMapper;

    @Autowired
    private CsvSerializer serializer;

    @Override
    public void addEmployee(EmployeeDTO employee) {
        try {
            serializer.addEmployee(modelMapper.map(employee, Employee.class));
        } catch (IOException ex) {
            throw new ApiRequestException("Employee can not be added.", ex);
        }
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            return modelMapper.mapAll(serializer.getAll(), EmployeeDTO.class);
        } catch (IOException ex) {
            throw new ApiRequestException(FILE_PROBLEMS_MESSAGE, ex);
        }
    }

    @Override
    public Map<String, List<EmployeeDTO>> getAllDepartmentsWithEmployeesSortedBySalary() {
        try {
            Map<String, List<Employee>> beforeMap =
                    serializer.getAllGroupedByDepartmentWithSalarySortedDesc();
            List<EmployeeDTO> employeeDTOList = beforeMap.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .map(e -> modelMapper.mapAll(e, EmployeeDTO.class))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            return employeeDTOList.stream()
                    .collect(Collectors.groupingBy(EmployeeDTO::getDepartment));
        } catch (IOException ex) {
            throw new ApiRequestException(FILE_PROBLEMS_MESSAGE, ex);
        }
    }

    @Override
    public EmployeeDTO getMostPaidInDepartment(String department) {
        try {
            Employee employee = serializer.getMostPaidPerDepartment(department);
            return modelMapper.map(employee, EmployeeDTO.class);
        } catch (IOException ex) {
            throw new ApiRequestException(FILE_PROBLEMS_MESSAGE, ex);
        } catch (NoSuchElementException ex) {
            throw new ApiRequestException("There is no such department as '"
                    + department + "'", ex);
        }
    }
}
