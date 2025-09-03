package com.example.Task_3.service.Interface;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.ROLE;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public interface EmployeeServiceInterface {
    void addEmployee(Employee employee);

    List<Employee> getAllEmployee();

    @ExceptionHandler(EntityNotFoundException.class)
    Employee getEmployeeById(String id);

    @ExceptionHandler(EntityNotFoundException.class)
    void updateEmployee(Employee updatedEmployee);

    List<Employee> getEmployeeByRole(ROLE role);

    @ExceptionHandler(EntityNotFoundException.class)
    void deleteEmployee(String id);

}
