package com.timeofview.company.analyzer.strategy;

import com.timeofview.company.analyzer.model.Employee;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReportingLineAnalyzer {

	private static final int TOO_LONG = 4;

	public static Map<Employee, Integer> findEmployeesWithLongReportingLines(List<Employee> employees, Map<Integer, List<Employee>> managerToEmployees, int ceoId) {
		Map<Employee, Integer> longReportingLines = new HashMap<>();
		Map<Integer, Integer> depthCache = new HashMap<>();
		for (Employee employee : employees) {
			int depth = findDepth(employee.id(), managerToEmployees, ceoId, depthCache);
			if (depth > TOO_LONG) {
				longReportingLines.put(employee, depth);
			}
		}
		return longReportingLines;
	}

	private static int findDepth(int employeeId, Map<Integer, List<Employee>> managerToEmployees, int ceoId, Map<Integer, Integer> depthCache) {
		if (employeeId == ceoId) {
			return 0;
		}
		if (depthCache.containsKey(employeeId)) {
			return depthCache.get(employeeId);
		}
		Optional<Employee> manager = managerToEmployees.values().stream()
		                                               .flatMap(Collection::stream)
		                                               .filter(e -> e.id() == employeeId)
		                                               .findFirst();
		if (manager.isPresent() && manager.get().managerId() != null) {
			int depth = 1 + findDepth(manager.get().managerId(), managerToEmployees, ceoId, depthCache);
			depthCache.put(employeeId, depth);
			return depth;
		}
		return 0;
	}

}
