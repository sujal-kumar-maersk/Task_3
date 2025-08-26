package com.example.Task_3.controller;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.EmployeeDetail;
import com.example.Task_3.service.Implementation.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeService empService;

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        Employee employee = new Employee();
        employee.setEmployeeDetail(new EmployeeDetail()); // Initialize nested object
        model.addAttribute("employee", employee);
        return "addEmployee";
    }
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute Employee e){
        empService.addEmployee(e);
        return "redirect:/api/employee/home";
    }

    @GetMapping
    public String getAllEmployee(Model model){
        List<Employee> emp = empService.getAllEmployee();
        model.addAttribute("employees",emp);
        return "allEmployee";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable String id,Model model){
        Employee emp = empService.getEmployeeById(id);
        model.addAttribute("employee",emp);
        return "employee";
    }

    @GetMapping("/update")
    public String updateRedirectForm() {
        return "enterUpdateId"; // This will be a simple form to enter ID
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Employee employee = empService.getEmployeeById(id);
        if (employee.getEmployeeDetail() == null) {
            employee.setEmployeeDetail(new EmployeeDetail()); // prevent null pointer
        }
        model.addAttribute("employee", employee);
        return "updateEmployee";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee){
        empService.updateEmployee(employee);
        return "redirect:/api/employee";
    }

    @GetMapping("/delete")
    public String showDeleteForm() {
        return "deleteEmployee";
    }
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable String id){
        empService.deleteEmployee(id);
        return "redirect:/api/employee";
    }

}
