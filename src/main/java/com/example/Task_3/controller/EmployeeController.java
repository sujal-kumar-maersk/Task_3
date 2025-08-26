package com.example.Task_3.controller;

import com.example.Task_3.model.Employee;
import com.example.Task_3.service.Implementation.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService empService;

    @GetMapping("/")
    public String home(){
        return "Welcome to my Application!";
    }
    @PostMapping
    public void addEmployee(@RequestBody Employee e){
        empService.addEmployee(e);
    }

    @GetMapping
    public List<Employee> getAllEmployee(){
        return empService.getAllEmployee();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id){
        return empService.getEmployeeById(id);
    }

    @PutMapping
    public void updateEmployee(@RequestBody Employee employee){
        empService.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id){
        empService.deleteEmployee(id);
    }

}
