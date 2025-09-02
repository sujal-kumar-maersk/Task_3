package com.example.Task_3.config;

import com.example.Task_3.model.Employee;
import com.example.Task_3.service.Implementation.EmployeeService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    EmployeeService employeeService;

    @PostConstruct
    public void preloadCache(){
        Cache cache = cacheManager.getCache("applicationCache");
        System.out.println("***********Inatilize cache");
        List<Employee> employeeList = employeeService.getAllEmployee();

        for (Employee employee : employeeList) {
            cache.put(employee.getId(),employee);
        }
    }

    @Scheduled(fixedRate = 15000,initialDelay = 15000)
    public void clearCache(){
        System.out.println("****** Clearing cache");
        cacheManager.getCacheNames().parallelStream().forEach(
                name -> cacheManager.getCache(name).clear()
        );
    }
}
