package com.axis.ijp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import com.axis.ijp.dto.JobApplicationDTO;

import com.axis.ijp.dto.JobDetailsDTO;
import com.axis.ijp.entity.JobApplication;
import com.axis.ijp.enums.JobApplicationStatus;

@Service
public interface JobApplicationService {

    ResponseEntity<List<JobDetailsDTO>> getAllOpenApplications();

    ResponseEntity<String> applyForJob(int jobId, int employeeId);

    ResponseEntity<List<JobApplicationDTO>> getApplicationsByApplicant(int employeeId);
    void updateJobApplicationStatus(int employeeId, int applicationId, JobApplicationStatus newStatus);



    ResponseEntity<String> getJobApplicationStatus(int jobId, int employeeId);

	Map<JobApplicationStatus, Long> getStatusCounts();



	


	

}
