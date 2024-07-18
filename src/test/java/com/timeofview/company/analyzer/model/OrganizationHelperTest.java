package com.timeofview.company.analyzer.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganizationHelperTest {

	@Test
	public void testBuildManagerToEmployeeMap() {
		Employee ceo = new Employee(1, "John", "Doe", 100000, null);
		Employee manager1 = new Employee(2, "Jane", "Smith", 80000, 1);
		Employee manager2 = new Employee(3, "Michael", "Johnson", 70000, 1);
		Employee employee1 = new Employee(4, "Emily", "Davis", 60000, 2);
		Employee employee2 = new Employee(5, "Robert", "Wilson", 55000, 2);
		Employee employee3 = new Employee(6, "Lisa", "Brown", 50000, 3);

		List<Employee> employees = Arrays.asList(ceo, manager1, manager2, employee1, employee2, employee3);

		Map<Integer, List<Employee>> managerToEmployeesMap = OrganizationHelper.buildManagerToEmployeeMap(employees);

		assertEquals(3, managerToEmployeesMap.size());
		assertEquals(2, managerToEmployeesMap.get(1).size());
		assertEquals(2, managerToEmployeesMap.get(2).size());
		assertEquals(1, managerToEmployeesMap.get(3).size());
	}
}
