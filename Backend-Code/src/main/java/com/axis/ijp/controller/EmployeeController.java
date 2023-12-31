package com.axis.ijp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.axis.ijp.entity.Employee;

import com.axis.ijp.enums.JobApplicationStatus;
import com.axis.ijp.service.EmployeeService;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

	private final EmployeeService employeeService;
	private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeService employeeService,PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register Employee Details.
     * Author: Utkarsha Bhosale
     */
    @PostMapping("/registerEmployee")
//  @PreAuthorize("hasAuthority('ROLE_HR')")
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    /**
     * View Employee By Id.
     * Author: Utkarsha Bhosale
     */
    @GetMapping("/{employeeId}")
    public Employee getEmployeeById(@PathVariable int employeeId) {
        return employeeService.getEmployeeById(employeeId);
    }

    /**
     * View All Employees.
     * Author: Utkarsha Bhosale
     */
    @GetMapping("/viewAllEmployees")
    @PreAuthorize("hasAuthority('ROLE_HR')")
//	public List<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page) {
//	    int pageSize = 20; // Number of records per page
//	    return employeeService.getAllEmployees(page, pageSize);
//	}
  
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    /**
     * Search for employees by name.
     * Author: Utkarsha Bhosale
     */
    @GetMapping("/searchByName")
    @PreAuthorize("hasAuthority('ROLE_HR')")
    public List<Employee> searchEmployeesByName(@RequestParam String name) {
        return employeeService.searchEmployeesByName(name);
    }
   
    /**
     * Get Employee Role by Full Name and Password.
     * Author: Pallavi Bolar
     */
    @PostMapping("/getRoleByFullNameAndPassword")
    public String getRoleByFullNameAndPassword(@RequestBody Employee credentials) {
        Employee employee = employeeService.getEmployeeByFullName(credentials.getFullName());
        if (employee != null && passwordEncoder.matches(credentials.getPassword(), employee.getPassword())) {
            return employee.getRole().toString();
        }
        return "Invalid credentials";
    }
    
    @PutMapping("/update/{employeeId}")
	public ResponseEntity<Void> updateEmployee(@PathVariable int employeeId, @RequestBody Employee employee){
		return employeeService.updateEmployee(employeeId, employee);
		
	}
    
    @GetMapping("/count")
    public long getEmployeeCount() {
        return employeeService.getEmployeeCount();
    }
    
    
    @GetMapping("/filterByGender")
    public List<Employee> filterEmployeesByGender(@RequestParam String gender) {
        return employeeService.filterByGender(gender);
    }

   

}
