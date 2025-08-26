package com.example.Task_3.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.Cascade;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.UUID;

@Entity
@Table(name = "employee_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Own separate primary key

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private Integer experienceYears;

    @Column(nullable = false)
    private String departmentName;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'Not Assigned'")
    private String project;

    @OneToOne
    @JoinColumn(name = "employee_id", nullable = false, unique = true)
    @JsonBackReference
    private Employee employee;
}

