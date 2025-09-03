package com.example.Task_3.model;

import com.example.Task_3.validation.Validatable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.UUID;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "employeeDetail")
public class Employee implements Validatable {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ROLE role;

    @Column(nullable = false)
    private String reportingTo;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private EmployeeDetail employeeDetail;

    @Override
    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        if (reportingTo == null || reportingTo.trim().isEmpty()) {
            throw new IllegalArgumentException("Reporting To cannot be empty");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role must not be null. Must be one of: " +
                    Arrays.toString(ROLE.values()));
        }
        if (employeeDetail != null) {
            employeeDetail.validate();
        }
    }
}

