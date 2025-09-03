package com.example.Task_3.service.Implementation;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.EmployeeDetail;
import com.example.Task_3.model.ROLE;
import com.example.Task_3.repository.EmployeeRepo;
import com.example.Task_3.service.Interface.EmployeeServiceInterface;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService implements EmployeeServiceInterface {

    @Autowired
    private EmployeeRepo empRepo;

    @Override
    public void addEmployee(Employee employee) {
        if (employee.getEmployeeDetail() != null) {
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
        return empRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
    }

    @Override
    @CacheEvict(value = "applicationCache" , key = "#updatedEmployee.id")
    public void updateEmployee(Employee updatedEmployee) {
        Employee existing = empRepo.findById(updatedEmployee.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + updatedEmployee.getId()));

        // Update base employee fields
        existing.setName(updatedEmployee.getName());
        existing.setRole(updatedEmployee.getRole());
        existing.setReportingTo(updatedEmployee.getReportingTo());

        // Update EmployeeDetail if provided
        if (updatedEmployee.getEmployeeDetail() != null) {
            EmployeeDetail updatedDetail = updatedEmployee.getEmployeeDetail();
            EmployeeDetail existingDetail = existing.getEmployeeDetail();

            if (existingDetail == null) {
                updatedDetail.setEmployee(existing);
                existing.setEmployeeDetail(updatedDetail);
            } else {
                existingDetail.setAge(updatedDetail.getAge());
                existingDetail.setExperienceYears(updatedDetail.getExperienceYears());
                existingDetail.setDepartmentName(updatedDetail.getDepartmentName());
                existingDetail.setProject(updatedDetail.getProject());
            }
        }

        empRepo.save(existing);
    }

    @Override
    public List<Employee> getEmployeeByRole(ROLE role) {
        return empRepo.findEmployeeByRole(role);
    }

    @Override
    @CacheEvict(value = "applicationCache",key="#id")
    public void deleteEmployee(String id){
        Employee employee = empRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + id));
        empRepo.delete(employee); // cascade will handle deleting EmployeeDetail
    }
}
