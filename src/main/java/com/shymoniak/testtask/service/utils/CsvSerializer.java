package com.shymoniak.testtask.service.utils;

import com.shymoniak.testtask.entity.Employee;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvSerializer {

    public static final String CSV_FILE_PATH =
            System.getProperty("user.dir") + "\\src\\main\\resources\\data.csv";

    private Converter converter = new Converter();

    public List<Employee> getAll() throws IOException {
        List<Employee> lines = Files.lines(Path.of(CSV_FILE_PATH))
                .skip(1)
                .map(e -> converter.convertFromString(e))
                .collect(Collectors.toList());
        return lines;
    }

    public void addEmployee(Employee employee) {
        try (
                FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            bufferedWriter.write(converter.convertIntoString(employee));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}