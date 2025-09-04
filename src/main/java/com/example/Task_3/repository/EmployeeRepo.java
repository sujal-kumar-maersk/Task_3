package com.example.Task_3.repository;

import com.example.Task_3.model.Employee;
import com.example.Task_3.model.ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,String> {
    public List<Employee> findEmployeeByRole(ROLE role);

    @Query(value="SELECT * FROM EMPLOYEE WHERE employee.reporting_to = :id",nativeQuery = true)
    public List<Employee> findAllEmployeeUnderManagerById(@Param("id") String id);
}
