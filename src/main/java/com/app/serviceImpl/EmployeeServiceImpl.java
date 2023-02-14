package com.app.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.EmployeeDto;
import com.app.entity.Employee;
import com.app.exception.EmployeeNotFoundException;
import com.app.repository.EmployeeRepository;
import com.app.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee findEmpById(Long id) {
		// TODO Auto-generated method stub
		Optional<Employee> findEmp = employeeRepository.findById(id);
		if (findEmp.isPresent()) {
			return findEmp.get();
		} else {
			throw new EmployeeNotFoundException("This employee not found in the database.");
		}
	}
	
	@Override
	public List<Employee> findAllEmps() {
		// TODO Auto-generated method stub
		List<Employee> listOfEmps = employeeRepository.findAll();
		if (listOfEmps.isEmpty()) {
			throw new EmployeeNotFoundException("No employees found in the database.");
		}
		return listOfEmps;
	}
	
	@Override
	public Employee saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		if (employeeRepository.existsById(employee.getId())) {
			throw new RuntimeException("ID is already present in database");
		}
		return employeeRepository.save(employee);
	}
	
	@Override
	public Employee updateEmp(Employee employee, Long id) {
		// TODO Auto-generated method stub
		Optional<Employee> existingEmployee = employeeRepository.findById(id);
		if (existingEmployee.isPresent()) {
			Employee updatedEmployee = existingEmployee.get();
			updatedEmployee.setFirstName(employee.getFirstName());
			updatedEmployee.setLastName(employee.getLastName());
			updatedEmployee.setEmail(employee.getEmail());
			updatedEmployee.setAge(employee.getAge());
			employeeRepository.save(updatedEmployee);
			return updatedEmployee;
		} else {
			throw new EmployeeNotFoundException("Given Id Not Found =" + id);
		}
	}
	
	@Override
	public void deleteEmpById(Long id) {
		// TODO Auto-generated method stub
		Optional<Employee> employee = employeeRepository.findById(id);
		if (employee.isPresent()) {
			employeeRepository.deleteById(id);
		} else {
			throw new EmployeeNotFoundException("Employee with id " + id + " not found");
		}
	}
	
	/*
	 * public static EmployeeDto convertDtoObject(Employee employee) {
	 * 
	 * EmployeeDto employeeDto=new EmployeeDto();
	 * employeeDto.setId(employee.getId());
	 * employeeDto.setFirstName(employee.getFirstName());
	 * employeeDto.setLastName(employee.getLastName());
	 * employeeDto.setEmail(employee.getEmail());
	 * employeeDto.setAge(employee.getAge()); return employeeDto; }
	 */
}
