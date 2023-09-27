package com.axis.ijp.controller;

import com.axis.ijp.dto.ComplaintWithEmployeeDTO;

import com.axis.ijp.entity.Complaint;

import com.axis.ijp.enums.ComplaintStatus;
import com.axis.ijp.service.ComplaintService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/support-assistant")
public class SupportAssistantController {

	private final ComplaintService complaintService;

    @Autowired
    public SupportAssistantController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }
    
    /**
     * Get complaint by ID
     * Author: Pallavi Bolar
     */
    @GetMapping("/complaints/{complaintId}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable int complaintId) {
        Complaint complaint = complaintService.getComplaintById(complaintId);
        if (complaint != null) {
            return ResponseEntity.ok(complaint);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all complaints (accessible by support assistant)
     * Author: Pallavi Bolar
     */
    @GetMapping("/complaints")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        return ResponseEntity.ok(complaints);
    }
    
    @GetMapping("/open-complaints")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<List<Complaint>> getAllOpenComplaints() {
        List<Complaint> openComplaints = complaintService.getAllOpenComplaints();
        return ResponseEntity.ok(openComplaints);
    }

    /**
     * Get the count of all open complaints
     * Author: Pallavi Bolar
     */
    @GetMapping("/open-complaints/count")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<Long> getOpenComplaintsCount() {
        long count = complaintService.getOpenComplaintsCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/complaints-with-employee-info")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<List<ComplaintWithEmployeeDTO>> getComplaintsWithEmployeeInfoByComplaintId(@RequestParam int complaintId) {
        List<ComplaintWithEmployeeDTO> complaintsWithInfo = complaintService.getComplaintsWithEmployeeInfoByComplaintId(complaintId);
        return ResponseEntity.ok(complaintsWithInfo);
    }
    
    /**
     * Update complaint status (accessible by support assistant)
     * Author: Pallavi Bolar
     */
    @PutMapping("/complaints/{complaintId}/update-status")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable int complaintId,
                                                           @RequestParam ComplaintStatus status) {
        Complaint updatedComplaint = complaintService.updateComplaintStatus(complaintId, status);
        return ResponseEntity.ok(updatedComplaint);
    }

    /**
     * Add a comment to a complaint (accessible by support assistant)
     * Author: Pallavi Bolar
     */
    @PutMapping("/complaints/{complaintId}/add-comment")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<Complaint> addCommentToComplaint(@PathVariable int complaintId,
                                                           @RequestParam String comment) {
        Complaint updatedComplaint = complaintService.addCommentToComplaint(complaintId, comment);
        return ResponseEntity.ok(updatedComplaint);
    }

    /**
     * Update a comment for a complaint (accessible by support assistant)
     * Author: Pallavi Bolar
     */
    @PutMapping("/complaints/{complaintId}/update-comment/{commentIndex}")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER_SUPPORT')")
    public ResponseEntity<Complaint> updateCommentForComplaint(@PathVariable int complaintId,
                                                               @PathVariable int commentIndex,
                                                               @RequestParam String updatedComment) {
        Complaint updatedComplaint = complaintService.updateCommentForComplaint(complaintId, commentIndex,
                updatedComment);
        if (updatedComplaint != null) {
            return ResponseEntity.ok(updatedComplaint);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
