package com.example.Task_3.service.Implementation;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.EmployeeDetail;
import com.example.Task_3.repository.EmployeeDetailRepo;
import com.example.Task_3.repository.EmployeeRepo;
import com.example.Task_3.service.Interface.EmployeeDetailServiceInterface;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class EmployeeDetailService implements EmployeeDetailServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDetailService.class);
    @Autowired
    private EmployeeDetailRepo empDetRep;
    @Autowired
    private EmployeeRepo empRepo;

    @Override
    public List<EmployeeDetail> getAllDetails() {
        logger.info("listing employee details");
        return empDetRep.findAll();
    }

    @Override
    public EmployeeDetail getDetailById(Long id) {
        logger.info("Finding employee details by id");
        return empDetRep.findById(String.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("EmployeeDetail not found with id " + id));
    }


    @Override
    public void  updateDetails(EmployeeDetail incomingDetail){
        if (incomingDetail.getEmployee() == null || incomingDetail.getEmployee().getId() == null) {
            logger.error("Either employee reference is null or if present employee_id is null");
            throw new IllegalArgumentException("Employee reference is required for updating details.");
        }

        String empId = incomingDetail.getEmployee().getId();

        // Check employee existence
        logger.info("Checking employee existence");
        Employee employee = empRepo.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + empId));

        // Fetch the current EmployeeDetail
        logger.info("Checking for employee detail");
        EmployeeDetail existingDetail = empDetRep.findById(String.valueOf(incomingDetail.getId()))
                .orElseThrow(() -> new EntityNotFoundException("EmployeeDetail not found with id: " + incomingDetail.getId()));

        // Update fields
        existingDetail.setAge(incomingDetail.getAge());
        existingDetail.setExperienceYears(incomingDetail.getExperienceYears());
        existingDetail.setDepartmentName(incomingDetail.getDepartmentName());
        existingDetail.setProject(incomingDetail.getProject());

        // Maintain bidirectional relationship
        existingDetail.setEmployee(employee);
        employee.setEmployeeDetail(existingDetail);

        // Now save the existing managed entity
        empDetRep.save(existingDetail);
    }

}
