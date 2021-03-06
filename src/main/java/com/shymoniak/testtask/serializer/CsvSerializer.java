package com.shymoniak.testtask.serializer;

import com.shymoniak.testtask.entity.Employee;
import com.shymoniak.testtask.exception.ApiRequestException;
import com.shymoniak.testtask.utils.Converter;
import com.shymoniak.testtask.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.shymoniak.testtask.constant.ApplicationConstants.*;

@Component
public class CsvSerializer {

    @Autowired
    private Converter converter;

    @Autowired
    private Validator validator;

    public List<Employee> getAll() throws IOException {
        return Files.lines(Path.of(CSV_FILE_PATH))
                .skip(1)
                .map(e -> converter.convertStringIntoEmployee(e))
                .collect(Collectors.toList());
    }

    public void addEmployee(Employee employee) throws IOException {
        if (!validator.isInvalidEmployee(employee)) {
            throw new ApiRequestException("Employee is not valid");
        }
        try (FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(converter.convertEmployeeIntoString(employee));
            bufferedWriter.newLine();
        }
    }

    public Employee getMostPaidPerDepartment(String department) throws IOException {
        return getAll().stream()
                .filter(e -> e.getDepartment().equals(department))
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public Map<String, List<Employee>> getAllGroupedByDepartmentWithSalarySortedDesc() throws IOException {
        return getAll().stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .collect(Collectors.groupingBy(Employee::getDepartment));
    }
}