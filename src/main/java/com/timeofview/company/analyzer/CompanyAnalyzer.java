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


	public static void main(String[] args) {
		try {
			final String fileName = "employees.csv";
			final EmployeeReader employeeReader = new ResourceEmployeeReaderImpl();

			final List<Employee> employees = employeeReader.readEmployeesFromFile(fileName);
			final Map<Integer, List<Employee>> managerToEmployees = OrganizationHelper.buildManagerToEmployeeMap(employees);

			final int ceoId = 123;

			final Map<Employee, Double> salaryDiscrepancies = SalaryAnalyzer.findManagersWithSalaryDiscrepancies(employees, managerToEmployees);
			final Map<Employee, Integer> longReportingLines =
					ReportingLineAnalyzer.findEmployeesWithLongReportingLines(employees, managerToEmployees, ceoId);

			System.out.println("Salary Discrepancies:");
			salaryDiscrepancies.forEach((manager, discrepancy) ->
					System.out.println(manager.firstName() + " " + manager.lastName() + " discrepancy: " + discrepancy)
			);

			System.out.println("\nEmployees with Long Reporting Lines:");
			longReportingLines.forEach((employee, depth) ->
					System.out.println(employee.firstName() + " " + employee.lastName() + ": " + depth)
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
