package com.exe.escobar.IMSBackend.Employee;


import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeDao {

    Optional<Employee> getEmployeeByFirstAndLastName(String firstName, String lastName);

}
