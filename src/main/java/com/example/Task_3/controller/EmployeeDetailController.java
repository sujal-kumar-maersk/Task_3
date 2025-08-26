package com.example.Task_3.controller;

import com.example.Task_3.model.EmployeeDetail;
import com.example.Task_3.service.Implementation.EmployeeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employeeDetail")
public class EmployeeDetailController {
    @Autowired
    EmployeeDetailService empDetService;

    @GetMapping
    public List<EmployeeDetail> getAllEmployee(){
        return empDetService.getAllDetails();
    }

    @GetMapping("/{id}")
    public EmployeeDetail getEmployeeById(@PathVariable Long id){
        return empDetService.getDetailById(id);
    }

    @PutMapping
    public void updateDetails(@RequestBody EmployeeDetail employeeDetail){
        empDetService.updateDetails(employeeDetail);
    }


    @DeleteMapping("/{id}")
    public void deleteDetails(@PathVariable Long id){
        empDetService.deleteDetails(id);
    }

}
