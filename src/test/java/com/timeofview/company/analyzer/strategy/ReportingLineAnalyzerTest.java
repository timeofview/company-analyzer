package com.timeofview.company.analyzer.strategy;

import com.timeofview.company.analyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReportingLineAnalyzerTest {

	@Test
	void testFindEmployeesWithLongReportingLines() {
		Employee ceo = new Employee(1, "John", "Doe", 100000, null);
		Employee manager1 = new Employee(2, "Jane", "Smith", 80000, 1);
		Employee manager2 = new Employee(3, "Michael", "Johnson", 70000, 1);
		Employee employee1 = new Employee(4, "Emily", "Davis", 60000, 2);
		Employee employee2 = new Employee(5, "Robert", "Wilson", 55000, 2);
		Employee employee3 = new Employee(6, "Lisa", "Brown", 50000, 3);
		Employee employee4 = new Employee(7, "Sarah", "Adams", 45000, 3);
		Employee employee5 = new Employee(8, "Mark", "Lee", 40000, 4);

		List<Employee> employees = Arrays.asList(ceo, manager1, manager2, employee1, employee2, employee3, employee4, employee5);

		Map<Integer, List<Employee>> managerToEmployees = new HashMap<>();
		managerToEmployees.put(1, Arrays.asList(manager1, manager2));
		managerToEmployees.put(2, Arrays.asList(employee1, employee2));
		managerToEmployees.put(3, Arrays.asList(employee3, employee4));
		managerToEmployees.put(4, Arrays.asList(employee5));

		Map<Employee, Integer> employeesWithLongReportingLines = ReportingLineAnalyzer.findEmployeesWithLongReportingLines(employees, managerToEmployees, 1);

		assertEquals(2, employeesWithLongReportingLines.size());

	}

}
