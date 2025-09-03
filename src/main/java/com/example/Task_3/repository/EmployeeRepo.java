package com.example.Task_3.repository;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,String> {
    public List<Employee> findEmployeeByRole(ROLE role);


}
