package com.example.Task_3.service.Interface;

import com.example.Task_3.model.EmployeeDetail;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

public interface EmployeeDetailServiceInterface {
    List<EmployeeDetail> getAllDetails();

    @ExceptionHandler(EntityNotFoundException.class)
    EmployeeDetail getDetailById(Long id);

    @ExceptionHandler(IllegalArgumentException.class)
    void updateDetails(EmployeeDetail incomingDetail);

}
