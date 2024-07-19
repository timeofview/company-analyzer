package com.timeofview.company.analyzer.strategy;

import com.timeofview.company.analyzer.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryAnalyzer {


	public static Map<Employee, Double> findManagersWithSalaryDiscrepancies(List<Employee> employees, Map<Integer, List<Employee>> managerToEmployees) {
		Map<Employee, Double> discrepancies = new HashMap<>();
		for (Employee manager : employees) {
			List<Employee> subordinates = managerToEmployees.get(manager.id());
			if (subordinates != null && !subordinates.isEmpty()) {
				double avgSubordinateSalary = subordinates.stream().mapToInt(Employee::salary).average().orElse(0.0);
				double minSalary = avgSubordinateSalary * 1.2;
				double maxSalary = avgSubordinateSalary * 1.5;
				if (manager.salary() < minSalary) {
					discrepancies.put(manager, manager.salary() - minSalary);
				} else if (manager.salary() > maxSalary) {
					discrepancies.put(manager, manager.salary() - maxSalary);
				}
			}
		}
		return discrepancies;
	}
}
