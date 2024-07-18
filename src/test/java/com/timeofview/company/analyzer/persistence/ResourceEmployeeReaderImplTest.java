package com.timeofview.company.analyzer.persistence;

import com.timeofview.company.analyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeReaderTest {
	final ResourceEmployeeReaderImpl employeeReader = new ResourceEmployeeReaderImpl();

	@Test
	void testReadEmployeesFromFile_ValidFile() throws IOException {
		List<Employee> employees = employeeReader.readEmployeesFromFile("test_employees.csv");

		assertEquals(5, employees.size());
		assertEquals(123, employees.get(0).id());
		assertEquals("Joe", employees.get(0).firstName());
	}

	@Test
	void testReadEmployeesFromFile_EmptyFile() throws IOException {
		ResourceEmployeeReaderImpl employeeReader = new ResourceEmployeeReaderImpl();
		List<Employee> employees = employeeReader.readEmployeesFromFile("empty_file.csv");

		assertEquals(0, employees.size());
	}

	@Test
	void testReadEmployeesFromFile_InvalidLinesIgnored() throws IOException {
		ResourceEmployeeReaderImpl employeeReader = new ResourceEmployeeReaderImpl();
		List<Employee> employees = employeeReader.readEmployeesFromFile("invalid_lines.csv");

		assertEquals(4, employees.size());

		assertEquals(123, employees.get(0).id());
		assertEquals("Joe", employees.get(0).firstName());
		assertEquals(124, employees.get(1).id());
		assertEquals("Martin", employees.get(1).firstName());
	}
}
