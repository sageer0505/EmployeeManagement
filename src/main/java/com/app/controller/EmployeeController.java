package com.app.controller;

import java.util.List;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.EmployeeDto;
import com.app.entity.Employee;
import com.app.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/get/{id}")
	ResponseEntity<EmployeeDto> findEmpById(@PathVariable Long id) {
		Employee findEmp = employeeService.findEmpById(id);
		
		EmployeeDto employeeDto=modelMapper.map(findEmp, EmployeeDto.class);
		if (employeeDto == null) {
			return new ResponseEntity<EmployeeDto>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EmployeeDto>(employeeDto, HttpStatus.OK);
	}

	@GetMapping("/getAll")
	ResponseEntity<List<EmployeeDto>> findAllEmps() {
		List<EmployeeDto> listOfEmps = employeeService.findAllEmps().stream().map(post->modelMapper.map(post, EmployeeDto.class))
				.collect(Collectors.toList());
		if (listOfEmps == null) {
			return new ResponseEntity<List<EmployeeDto>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<EmployeeDto>>(listOfEmps, HttpStatus.OK);
	}

	@PostMapping("/save")
	ResponseEntity<EmployeeDto> saveEmp(@RequestBody EmployeeDto employeeDto) {
		try {
			Employee postRequest = modelMapper.map(employeeDto, Employee.class);
			Employee savedEmp = employeeService.saveEmp(postRequest);
			
			EmployeeDto postResponse = modelMapper.map(savedEmp, EmployeeDto.class);
			
			return new ResponseEntity<EmployeeDto>(postResponse, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<EmployeeDto>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/update/{id}")
	ResponseEntity<EmployeeDto> updateEmp(@PathVariable Long id, @RequestBody EmployeeDto employeeDto) {
		
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		Employee updatedEmp = employeeService.updateEmp(employee, id);
		EmployeeDto postResponse = modelMapper.map(updatedEmp, EmployeeDto.class);
		return new ResponseEntity<EmployeeDto>(postResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	ResponseEntity<String> deleteEmpById(@PathVariable("id") Long id) {
		employeeService.deleteEmpById(id);
		return new ResponseEntity<String>("id no:" + id + " has been deleted successfully", HttpStatus.ACCEPTED);
	}
	
	
}
