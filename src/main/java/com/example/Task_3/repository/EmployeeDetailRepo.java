package com.example.Task_3.repository;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDetailRepo extends JpaRepository<EmployeeDetail,String> {
}
