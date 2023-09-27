package com.axis.ijp.service.impl;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.axis.ijp.entity.Employee;

//import com.axis.ijp.entity.UpdatedStatus;
import com.axis.ijp.enums.EmployeeRole;
import com.axis.ijp.enums.JobApplicationStatus;
import com.axis.ijp.enums.ProfileStatus;
import com.axis.ijp.repository.EmployeeRepository;
//import com.axis.ijp.repository.UpdatedStatusRepository;
import com.axis.ijp.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;
//	private final UpdatedStatusRepository updatedStatusRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, 
//    		UpdatedStatusRepository updatedStatusRepository
    		PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
//        this.updatedStatusRepository = updatedStatusRepository;
        this.passwordEncoder = passwordEncoder;
    }

	  @Override
	    public Employee createEmployee(Employee employee) {
		  employee.setPassword(passwordEncoder.encode(employee.getPassword()));
	        return employeeRepository.save(employee);
	    }
	  
	    @Override
	    public Employee getEmployeeById(int employeeId) {
	        return employeeRepository.findById(employeeId).orElse(null);
	    }
	    
	    @Override
	    public List<Employee> getAllEmployees() {
	        return employeeRepository.findAll();
	    }
	    
	 // find employee by user role-HR,Customer_support,candidate
	    @Override
	 	public List<Employee> getEmployeesByRole(EmployeeRole role) {
	    	return employeeRepository.findByRole(role);
	 	}
	    
//	    @Override
//	    public List<Employee> getAllEmployees(int page, int pageSize) {
//	        Pageable pageable = PageRequest.of(page, pageSize);
//	        Page<Employee> employeePage = employeeRepository.findAll(pageable);
//	        return employeePage.getContent();
//	    }
	
	   
//	 	
	 // save an employee detail and set password as first for letter of name and DDMM of DOB
	    @Override
	 	public ResponseEntity<Void> saveEmployee(Employee employee) {
	 		
	         String nameFirstFour = employee.getFullName().substring(0, Math.min(employee.getFullName().length(), 4));
	         String dobDDMM = String.format("%02d%02d", employee.getDateOfBirth().getDayOfMonth(), employee.getDateOfBirth().getMonthValue());
	         String password = nameFirstFour + dobDDMM;
	         employee.setPassword(password);
	         employeeRepository.save(employee);
	         
	 		return ResponseEntity.status(HttpStatus.CREATED).build();
	 	}

	 	// employee details are never deleted, so employee status is set to deactivated
	    @Override
	 	public ResponseEntity<Void> deactivateEmployee(int id) {
	 		Optional<Employee> deactEmployee = employeeRepository.findById(id);
	 		if (deactEmployee.isPresent()) {
	 			// employeeRepository.deleteById(id);
	 			Employee existingEmployee = deactEmployee.get();
	 			existingEmployee.setProfileStatus(ProfileStatus.DEACTIVATED);
	 			employeeRepository.save(existingEmployee);
	 			return ResponseEntity.ok().build();
	 		} else {
	 			return ResponseEntity.notFound().build();
	 		}
	 	}

	 	// update employee details
	    @Override
	 	public ResponseEntity<Void> updateEmployee(int id, Employee updatedEmployee) {
	 		Optional<Employee> existingEmployee = employeeRepository.findById(id);
	 		if (existingEmployee.isPresent()) {
	 			Employee emp = existingEmployee.get();
	 			updatedEmployee.setEmployeeId(id);
	 			updatedEmployee.setFullName(emp.getFullName());
	 			updatedEmployee.setEmailId(emp.getEmailId());
	 			updatedEmployee.setPhoneNo(emp.getPhoneNo());
	 			updatedEmployee.setDateOfBirth(emp.getDateOfBirth());
	 			updatedEmployee.setGender(emp.getGender());
	 			updatedEmployee.setProfileStatus(emp.getProfileStatus());
	 			updatedEmployee.setComplaints(emp.getComplaints());
	 			updatedEmployee.setEducationDetails(emp.getEducationDetails());
	 			updatedEmployee.setJobApplications(emp.getJobApplications());
	 			//updatedEmployee.setResume(emp.getResume());
	 			updatedEmployee.setRole(emp.getRole());
	 			updatedEmployee.setSkills(emp.getSkills());
	 			updatedEmployee.setWorkHistoryList(emp.getWorkHistoryList());
	 			updatedEmployee.setPassword(emp.getPassword());
	 			
	 			employeeRepository.save(updatedEmployee);
	 			return ResponseEntity.ok().build();
	 		} else {
	 			return ResponseEntity.notFound().build();
	 		}
	 	}
	 	
	    @Override
	    public void deleteEmployee(int employeeId) {
	        employeeRepository.deleteById(employeeId);
	    }
	    
	    @Override
	    public List<Employee> searchEmployeesByName(String name) {
	        return employeeRepository.findByFullNameContainingIgnoreCase(name);
	    }
	    
//	    @Override
//	    public Employee updateJobApplicationStatus(int employeeId) {
//	        Employee employee = employeeRepository.findById(employeeId).orElse(null);
//	        if (employee != null) {
//	            List<UpdatedStatus> updatedStatuses = updatedStatusRepository.findByApplicant(employee);
//	            employee.setUpdatedStatuses(updatedStatuses);
//	        }
//	        return employee;
//	    }
	    
	    public Employee getEmployeeByFullName(String fullName) {
	        Optional<Employee> optionalEmployee = employeeRepository.findByFullName(fullName);
	        return optionalEmployee.orElse(null);
	    }  
	    
	   @Override
	    public long getEmployeeCount() {
	        return employeeRepository.count(); // This counts all employees in the table
	    }
	   
	   @Override
	   public List<Employee> filterByGender(String gender) {
		    return employeeRepository.findByGender(gender);
		}


}