package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dao.EmployeeRepo;
import com.cg.entity.Employee;
import com.cg.exception.NoSuchEmployeeFoundException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepo empRepo;

	@Override
	@Transactional
	public Employee addEmployee(Employee emp) {
		return empRepo.save(emp);
	}

	@Override
	public List<Employee> findAllEmployee() {
		return empRepo.findAll();
	}

	@Override
	public Employee findEmployeeById(int id) throws NoSuchEmployeeFoundException {
		Optional<Employee> emp = empRepo.findById(id);
		if (emp.isPresent())
			return emp.get();

		throw new NoSuchEmployeeFoundException("Employee not found with the id: " + id);
	}

	@Override
	@Transactional
	public Employee modifyEmployee(Employee emp, int id) throws NoSuchEmployeeFoundException {
		Employee findEmp = findEmployeeById(id);
		findEmp.setName(emp.getName());
		findEmp.setDepartment(emp.getDepartment());
		findEmp.setRole(emp.getRole());
		findEmp.setSalary(emp.getSalary());
		return empRepo.save(findEmp);
	}

	@Override
	@Transactional
	public boolean removeEmployee(int id) throws NoSuchEmployeeFoundException {
		empRepo.deleteById(id);
		Optional<Employee> emp = empRepo.findById(id);
		return !(emp.isPresent());
	}

	@Override
	public List<Employee> findEmpByDept(String departmentName) {
		return empRepo.findEmpByDeptId(departmentName);
	}

}
