package com.timeofview.company.analyzer.model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrganizationHelper {

	public static Map<Integer, List<Employee>> buildManagerToEmployeeMap(List<Employee> employees) {
		return employees.stream()
		                .filter(e -> e.managerId() != null)
		                .collect(Collectors.groupingBy(Employee::managerId));
	}
}
