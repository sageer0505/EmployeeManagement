package com.app.service;

import java.util.List;

import com.app.entity.Employee;

public interface EmployeeService {
	
	public Employee findEmpById(Long id);
	
	public List<Employee> findAllEmps();
	
	public Employee saveEmp(Employee employee);
	
	public Employee updateEmp(Employee employee, Long id);
	
	public void deleteEmpById(Long id);
}
