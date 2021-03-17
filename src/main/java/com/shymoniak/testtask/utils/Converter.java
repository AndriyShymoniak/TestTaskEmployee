package com.shymoniak.testtask.utils;

import com.shymoniak.testtask.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public Employee convertStringIntoEmployee(String line) {
        String[] separated = line.replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .split(";");
        return new Employee(separated[0], separated[1],
                Integer.parseInt(separated[2]));
    }

    public String convertEmployeeIntoString(Employee employee) {
        return employee.getFullName() + ";" + employee.getDepartment() + ";" +
                employee.getSalary();
    }
}
