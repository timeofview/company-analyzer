package com.timeofview.company.analyzer.persistence;

import com.timeofview.company.analyzer.model.Employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceEmployeeReaderImpl implements EmployeeReader {

	@Override
	public List<Employee> readEmployeesFromFile(final String fileName) throws IOException {
		final ClassLoader classLoader = EmployeeReader.class.getClassLoader();
		final List<Employee> employees = new ArrayList<>();
		final Map<CsvField, Integer> fieldIndexMap = new HashMap<>();

		try (final InputStream inputStream = classLoader.getResourceAsStream(fileName);
		     final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

			final String headerLine = reader.readLine();
			if (headerLine == null) {
				System.err.println("Empty file or missing header");
				return Collections.emptyList();
			}

			final String[] fieldNames = headerLine.split(",");


			initFieldIndexMap(fieldNames, fieldIndexMap);

			String line;
			while ((line = reader.readLine()) != null) {
				final Employee employee = parseEmployeeFromCsvLine(line, fieldIndexMap);
				if (employee != null) {
					employees.add(employee);
				}
			}
		}

		return employees;
	}

	private void initFieldIndexMap(final String[] fieldNames, final Map<CsvField, Integer> fieldIndexMap) {
		for (int i = 0; i < fieldNames.length; i++) {
			final String fieldName = fieldNames[i].trim();
			final CsvField csvField = CsvField.fromHeaderName(fieldName);
			fieldIndexMap.put(csvField, i);
		}
	}

	private Employee parseEmployeeFromCsvLine(final String line, final Map<CsvField, Integer> fieldIndexMap) {
		try {
			final String[] fields = line.split(",");

			final int id = Integer.parseInt(getFieldValue(fields, fieldIndexMap, CsvField.ID));
			final String firstName = getFieldValue(fields, fieldIndexMap, CsvField.FIRST_NAME);
			final String lastName = getFieldValue(fields, fieldIndexMap, CsvField.LAST_NAME);

			if (firstName.isEmpty() || lastName.isEmpty()) {
				System.err.println("Skipping invalid line (empty name): " + line);
				return null;
			}

			final int salary = Integer.parseInt(getFieldValue(fields, fieldIndexMap, CsvField.SALARY));
			if (salary <= 0) {
				System.err.println("Skipping invalid salary: " + getFieldValue(fields, fieldIndexMap, CsvField.SALARY));
				return null;
			}

			Integer managerId = null;
			final String managerIdStr = getFieldValue(fields, fieldIndexMap, CsvField.MANAGER_ID);
			if (!managerIdStr.isEmpty()) {
				managerId = Integer.parseInt(managerIdStr);
			}

			return new Employee(id, firstName, lastName, salary, managerId);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
			System.err.println("Error processing line: " + line + ". Skipping...");
			return null;
		}
	}

	private String getFieldValue(final String[] fields, final Map<CsvField, Integer> fieldIndexMap, final CsvField csvField) {
		final Integer index = fieldIndexMap.get(csvField);
		if (index == null || index >= fields.length) {
			return "";
		}
		return fields[index].trim();
	}
}
