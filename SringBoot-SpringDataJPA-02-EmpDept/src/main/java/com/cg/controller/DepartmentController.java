package com.cg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.Department;
import com.cg.exception.NoSuchDepartmentFoundException;
import com.cg.service.DepartmentService;

@RestController
public class DepartmentController {

	@Autowired
	private DepartmentService dService;

	@GetMapping("/getalldept")
	public ResponseEntity<List<Department>> getAllDept() {
		try {
			return new ResponseEntity<>(dService.getAllDepartment(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getdeptbyid/{id}")
	public ResponseEntity<Department> getDeptById(@PathVariable int id) {
		try {
			return new ResponseEntity<>(dService.getDeptById(id), HttpStatus.OK);
		} catch (NoSuchDepartmentFoundException ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/createdept")
	public ResponseEntity<Department> createDept(@RequestBody Department dep) {
		try {
			return new ResponseEntity<>(dService.addDeparment(dep), HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updatedept/{id}")
	public ResponseEntity<Department> updateDept(@RequestBody Department dep, @PathVariable int id) {
		try {
			return new ResponseEntity<>(dService.updateDepartment(dep, id), HttpStatus.CREATED);
		} catch (NoSuchDepartmentFoundException ex) {
			return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/deletedept/{id}")
	public ResponseEntity<String> deleteDept(@PathVariable int id) {
		try {
			boolean success = dService.removeDepartment(id);
			if (success)
				return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
			else
				return new ResponseEntity<>("Failed to delete", HttpStatus.NOT_MODIFIED);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
