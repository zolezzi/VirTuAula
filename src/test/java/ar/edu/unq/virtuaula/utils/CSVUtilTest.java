package ar.edu.unq.virtuaula.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import ar.edu.unq.virtuaula.VirtuaulaApplicationTests;
import ar.edu.unq.virtuaula.model.PlayerAccount;
import ar.edu.unq.virtuaula.util.CSVUtil;

public class CSVUtilTest extends VirtuaulaApplicationTests {
	
	@Autowired
	CSVUtil util;

    @SuppressWarnings("static-access")
    @Test
    public void testLoadCSVWithoutLineThenReturnListEmpty() throws IOException {
    	int expected = 0;
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre,Apellido,DNI,Email\n");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", is);
		List<PlayerAccount> result = util.csvToPlayers(file.getInputStream());
        assertNotNull(result);
        assertEquals(expected, result.size());
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testLoadCSVWithOneLineThenReturnListNotEmpty() throws IOException {
    	int expected = 1;
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("First Name,Last Name,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.csv", "text/csv", is);
        List<PlayerAccount> result = util.csvToPlayers(file.getInputStream());
        assertNotNull(result);
        assertEquals(expected, result.size());
    }
    
    @SuppressWarnings("static-access")
    @Test
    public void testFileCSVNotFormatValidThenReturnFalse() throws IOException {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre,Apellido,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.html", "text/html", is);
        boolean result = util.hasCSVFormat(file);
        assertFalse(result);
    }
    
    @Test
    @SuppressWarnings("static-access")
    public void testFileCSVFormatValidThenReturnTrue() throws IOException {
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Nombre,Apellido,DNI,Email\n");
        csvBuilder.append("Carlos,Cardozo,36000001,carlos@gmail.com");
        InputStream is = new ByteArrayInputStream(csvBuilder.toString().getBytes());
        MockMultipartFile file = new MockMultipartFile("file", "hello.html", "text/csv", is);
        boolean result = util.hasCSVFormat(file);
        assertTrue(result);
    }
}
