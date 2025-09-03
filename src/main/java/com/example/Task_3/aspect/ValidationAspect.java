package com.example.Task_3.aspect;

import com.example.Task_3.validation.Validatable;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ValidationAspect {

    @Before("execution(* com.example.Task_3.service..*(..))")
    public void validateInputs(JoinPoint joinPoint) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof Validatable v) {
                v.validate(); // Calls validate() on Employee or EmployeeDetail
            }
        }
    }
}

