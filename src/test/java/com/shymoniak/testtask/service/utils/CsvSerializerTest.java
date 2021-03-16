package com.shymoniak.testtask.service.utils;

import com.shymoniak.testtask.entity.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CsvSerializerTest {

    List<Employee> expectedList = new ArrayList<>();
    CsvSerializer serializer = new CsvSerializer();

    @Before
    public void fillInitialValues() {
        expectedList.add(new Employee("John Smith", "IT", 100));
        expectedList.add(new Employee("Nabilah Naylor", "IT", 150));
        expectedList.add(new Employee("Faiz Dodd", "IT", 100));
        expectedList.add(new Employee("Chay Parkinson", "Sales", 120));
        expectedList.add(new Employee("viana Hess", "Sales", 130));
        expectedList.add(new Employee("Jennie Rosario", "Sales", 112));
        expectedList.add(new Employee("Toni Gill", "Sales", 123));
        expectedList.add(new Employee("Marek Kearns", "Marketing", 200));
        expectedList.add(new Employee("Stuart Dyer", "Marketing", 145));
        expectedList.add(new Employee("Bonnie Gilbert", "Marketing", 130));
        expectedList.add(new Employee("Kaila Lindsey", "Marketing", 100));
    }

    @After
    public void clearAll() {
        expectedList.clear();
    }

    @Test
    public void getAll() {
        try {
            List<Employee> employees = serializer.getAll();
            employees.stream().forEach(System.out::println);
            assertEquals(employees, expectedList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void writeIntoFile() {
        try {
            Employee employee = new Employee("John Doe", "IT", 99);
            serializer.addEmployee(employee);
            expectedList.add(employee);
            assertEquals(serializer.getAll(), expectedList);
        } catch (IOException ex){
            ex.printStackTrace();
        }

    }
}