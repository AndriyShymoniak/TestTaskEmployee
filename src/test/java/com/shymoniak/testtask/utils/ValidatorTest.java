package com.shymoniak.testtask.utils;

import com.shymoniak.testtask.constant.ApplicationConstants;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ValidatorTest {

    @Test
    public void isValidCSVFile() throws IOException {
        Validator validator = new Validator();
        assertTrue(validator.isValidCSVFile(new File(ApplicationConstants.CSV_FILE_PATH)));
    }
}