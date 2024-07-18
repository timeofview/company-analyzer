package com.timeofview.company.analyzer.persistence;

import com.timeofview.company.analyzer.model.Employee;

import java.io.IOException;
import java.util.List;

public interface EmployeeReader {

	enum CsvField {
		ID("Id"),
		FIRST_NAME("firstName"),
		LAST_NAME("lastName"),
		SALARY("salary"),
		MANAGER_ID("managerId");

		private String headerName;

		CsvField(final String headerName) {
			this.headerName = headerName;
		}

		public String getHeaderName() {
			return headerName;
		}

		public static CsvField fromHeaderName(String headerName) {
			for (CsvField field : CsvField.values()) {
				if (field.getHeaderName().equalsIgnoreCase(headerName.trim())) {
					return field;
				}
			}
			return null;
		}
	}

	List<Employee> readEmployeesFromFile(String fileName) throws IOException;
}
