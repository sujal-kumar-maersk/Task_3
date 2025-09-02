package com.example.Task_3.service.Implementation;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.EmployeeDetail;
import com.example.Task_3.repository.EmployeeRepo;
import com.example.Task_3.service.Interface.EmployeeServiceInterface;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepo empRepo;

    @Override
    public void addEmployee(Employee employee) {
        if (employee.getEmployeeDetail() != null) {
            logger.info("EmployeeDetail reference is present");
            employee.getEmployeeDetail().setEmployee(employee); // Ensure bidirectional relationship
        }
        empRepo.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return empRepo.findAll();
    }

    @Override
    @Cacheable(value="applicationCache",key="#id")
    public Employee getEmployeeById(String id) {
        logger.info("Finding employee by id");
        return empRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
    }

    @Override
    @CacheEvict(value = "applicationCache" , key = "#updatedEmployee.id")
    public void updateEmployee(Employee updatedEmployee) {
        logger.info("Searching for employee using employee_id");
        Employee existing = empRepo.findById(updatedEmployee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + updatedEmployee.getId()));

        // Update base employee fields
        existing.setName(updatedEmployee.getName());
        existing.setRole(updatedEmployee.getRole());
        existing.setReportingTo(updatedEmployee.getReportingTo());

        // Update EmployeeDetail if provided
        if (updatedEmployee.getEmployeeDetail() != null) {
            logger.info("Employee detail reference is present in data provided by client");
            EmployeeDetail updatedDetail = updatedEmployee.getEmployeeDetail();
            EmployeeDetail existingDetail = existing.getEmployeeDetail();

            if (existingDetail == null) {
                updatedDetail.setEmployee(existing);
                existing.setEmployeeDetail(updatedDetail);
            } else {
                logger.info("Employee detail already exist and now updating");
                existingDetail.setAge(updatedDetail.getAge());
                existingDetail.setExperienceYears(updatedDetail.getExperienceYears());
                existingDetail.setDepartmentName(updatedDetail.getDepartmentName());
                existingDetail.setProject(updatedDetail.getProject());
            }
        }

        empRepo.save(existing);
    }

    @Override
    @CacheEvict(value = "applicationCache",key="#deptId")
    public void deleteEmployee(String id) {
        logger.info("Deleting employee and its details using employee_id");
        Employee employee = empRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
        empRepo.delete(employee); // cascade will handle deleting EmployeeDetail
    }
}
