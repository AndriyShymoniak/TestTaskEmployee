package com.shymoniak.testtask.service.utils;

import com.shymoniak.testtask.entity.Employee;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static com.shymoniak.testtask.constants.ApplicationConstants.*;

@Component
public class CsvSerializer {

    private Converter converter = new Converter();

    public List<Employee> getAll() throws IOException {
        return Files.lines(Path.of(CSV_FILE_PATH))
                .skip(1)
                .map(e -> converter.convertStringIntoEmployee(e))
                .collect(Collectors.toList());
    }

    public void addEmployee(Employee employee) throws IOException {
        try (
                FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(converter.convertEmployeeIntoString(employee));
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