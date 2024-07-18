package com.timeofview.company.analyzer.persistence;

import com.timeofview.company.analyzer.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class EmployeeReaderImpl implements EmployeeReader {


	@Override
	public List<Employee> readEmployeesFromFile(final String fileName) throws IOException {
		ClassLoader classLoader = EmployeeReader.class.getClassLoader();
		try (final InputStream inputStream = classLoader.getResourceAsStream(fileName);
		     final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			return reader.lines()
			             .skip(1) // skip header
			             .map(line -> {
				             final String[] fields = line.split(",");
				             final int id = Integer.parseInt(fields[0]);
				             final String firstName = fields[1];
				             final String lastName = fields[2];
				             final int salary = Integer.parseInt(fields[3]);
				             final Integer managerId = fields.length > 4 && !fields[4].isEmpty() ?
						             Integer.parseInt(fields[4]) : null;
				             return new Employee(id, firstName, lastName, salary, managerId);
			             })
			             .toList();
		}
	}
}
