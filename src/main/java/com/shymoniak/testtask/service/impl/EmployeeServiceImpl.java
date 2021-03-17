package com.shymoniak.testtask.service.impl;

import com.shymoniak.testtask.domain.EmployeeDTO;
import com.shymoniak.testtask.entity.Employee;
import com.shymoniak.testtask.service.EmployeeService;
import com.shymoniak.testtask.service.utils.CsvSerializer;
import com.shymoniak.testtask.service.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private ObjectMapperUtils modelMapper;

    @Autowired
    private CsvSerializer serializer;

    @Override
    public void addEmployee(EmployeeDTO employee) throws IOException {
        serializer.addEmployee(modelMapper.map(employee, Employee.class));
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() throws IOException {
        return modelMapper.mapAll(serializer.getAll(), EmployeeDTO.class);
    }

    @Override
    public Map<String, List<EmployeeDTO>> getAllDepartmentsWithEmployeesSortedBySalary() {
        try {
            Map<String, List<Employee>> beforeMap =
                    serializer.getAllGroupedByDepartmentWithSalarySortedDesc();
            List<EmployeeDTO> employeeDTOList = beforeMap.entrySet().stream()
                    .map(e -> e.getValue())
                    .map(e -> modelMapper.mapAll(e, EmployeeDTO.class))
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            return employeeDTOList.stream()
                    .collect(Collectors.groupingBy(EmployeeDTO::getDepartment));
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public EmployeeDTO getMostPaidInDepartment(String department) {
        try {
            return modelMapper.map(serializer.getMostPaidPerDepartment(department),
                    EmployeeDTO.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
