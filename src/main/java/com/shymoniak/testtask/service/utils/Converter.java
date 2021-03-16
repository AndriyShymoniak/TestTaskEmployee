package com.shymoniak.testtask.service.utils;

import com.shymoniak.testtask.entity.Employee;

public class Converter {
    public Employee convertFromString(String line) {
        String[] separated = line.replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .split(";");
        return new Employee(separated[0], separated[1],
                Integer.parseInt(separated[2]));
    }

    public String convertIntoString(Employee employee) {
        return employee.getFullName() + ";" + employee.getDepartment() + ";" +
                employee.getSalary();
    }
}
