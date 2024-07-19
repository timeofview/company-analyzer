package com.timeofview.company.analyzer.strategy;

import com.timeofview.company.analyzer.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SalaryAnalyzerTest {

	@Test
	void testFindManagersWithSalaryDiscrepancies() {
		Employee manager1 = new Employee(1, "John", "Doe", 1_000_000, null);
		Employee employee1 = new Employee(2, "Jane", "Smith", 80_000, 1);
		Employee employee2 = new Employee(3, "Michael", "Johnson", 70_000, 1);
		Employee manager2 = new Employee(4, "Emily", "Davis", 60_000, null);
		Employee employee3 = new Employee(5, "Robert", "Wilson", 55_000, 4);
		Employee employee4 = new Employee(6, "Lisa", "Brown", 50_000, 4);

		List<Employee> employees = Arrays.asList(manager1, employee1, employee2, manager2, employee3, employee4);

		Map<Integer, List<Employee>> managerToEmployees = new HashMap<>();
		managerToEmployees.put(1, Arrays.asList(employee1, employee2));
		managerToEmployees.put(4, Arrays.asList(employee3, employee4));

		Map<Employee, Double> discrepancies = SalaryAnalyzer.findManagersWithSalaryDiscrepancies(employees, managerToEmployees);

		assertEquals(2, discrepancies.size());
		assertEquals(887500.0, discrepancies.get(manager1));
		assertEquals(-3000.0, discrepancies.get(manager2));
	}
}
