package com.shymoniak.testtask.utils;

import com.shymoniak.testtask.entity.Employee;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterTest {

    Converter converter = new Converter();

    @Test
    public void convertFromString(){
        String str = "[Chay Parkinson\tSales\t120]";
        Employee employee = converter.convertStringIntoEmployee(str);
        assertEquals(new Employee("Chay Parkinson", "Sales", 120),employee);
    }

}