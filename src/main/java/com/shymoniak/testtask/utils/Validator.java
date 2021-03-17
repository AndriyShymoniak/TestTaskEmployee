package com.shymoniak.testtask.utils;

import com.shymoniak.testtask.entity.Employee;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.shymoniak.testtask.constant.ApplicationConstants.*;

@Component
public class Validator {
    public boolean isInvalidEmployee(Employee em) {
        return (em.getFullName().equals("") || em.getFullName() == null)
                && (em.getDepartment().equals("") || em.getDepartment() == null)
                && em.getSalary() < 0;
    }

    public boolean isValidCSVFile(File file) throws IOException {
        return file.getName().endsWith(".csv")
                && file.length() != 0
                && hasEqualNumberOfColumns(file)
                && !hasEmptyFields(file);
    }

    private boolean hasEmptyFields(File file) throws IOException {
        List<String[]> list = Files.lines(Path.of(file.getPath()))
                .map(e -> e.split(DELIMITER))
                .collect(Collectors.toList());
        for (String[] arr : list) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasEqualNumberOfColumns(File file) throws IOException {
        Optional<String> firstLine =
                Files.lines(Path.of(file.getPath())).findFirst();
        int separations = columnCounter(firstLine.get());
        return Files.lines(Path.of(file.getPath()))
                .filter(e -> columnCounter(e) != separations)
                .count() == 0;
    }

    private int columnCounter(String str) {
        return (int) str.chars()
                .filter(c -> c == (int) DELIMITER.charAt(0))
                .count();
    }
}
