package com.timeofview.company.analyzer;

import com.timeofview.company.analyzer.model.Employee;
import com.timeofview.company.analyzer.model.OrganizationHelper;
import com.timeofview.company.analyzer.persistence.EmployeeReader;
import com.timeofview.company.analyzer.persistence.ResourceEmployeeReaderImpl;
import com.timeofview.company.analyzer.strategy.ReportingLineAnalyzer;
import com.timeofview.company.analyzer.strategy.SalaryAnalyzer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CompanyAnalyzer {

	public static final int TOO_LONG_REPORT = 2;

	public static void main(String[] args) {
		try {
			final String fileName = "employees.csv";
			final EmployeeReader employeeReader = new ResourceEmployeeReaderImpl();

			final List<Employee> employees = employeeReader.readEmployeesFromFile(fileName);
			final Map<Integer, List<Employee>> managerToEmployees = OrganizationHelper.buildManagerToEmployeeMap(employees);

			final Employee ceo =
					employees.stream()
					         .filter(employee -> employee.managerId() == null)
					         .findFirst()
					         .orElseThrow(() -> new IllegalStateException("CEO wasn't found"));

			final Map<Employee, Double> salaryDiscrepancies = SalaryAnalyzer.findManagersWithSalaryDiscrepancies(employees, managerToEmployees);
			final Map<Employee, Integer> longReportingLines =
					ReportingLineAnalyzer.findEmployeesWithLongReportingLines(employees, managerToEmployees, ceo.id());

			System.out.println("Salary Discrepancies:");
			salaryDiscrepancies.forEach((manager, discrepancy) -> {
						if (discrepancy < 0) {
							System.out.printf("Manager %s %s earns %.2f less than should%n", manager.firstName()
									, manager.lastName(), Math.abs(discrepancy));
						} else {
							System.out.printf("Manager %s %s earns %.2f more than should%n", manager.firstName()
									, manager.lastName(), discrepancy);
						}
					}

			);

			System.out.println("\nEmployees with Long Reporting Lines:");
			longReportingLines.forEach((employee, depth) ->
					System.out.println(employee.firstName() + " " + employee.lastName() + ", by : " + (depth - TOO_LONG_REPORT))
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
