package com.example.employeemanagement.controller;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<Employee> addEmployee(
            @RequestBody Employee employee) {

        repository.addEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {

        return repository.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable int id) {

        Employee employee = repository.getEmployeeById(id);

        if (employee == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employee) {

        int result = repository.updateEmployee(id, employee);

        if (result == 0) {
            return ResponseEntity.notFound().build();
        }
        Employee updatedEmployee =
                repository.getEmployeeById(id);
        return ResponseEntity.ok(updatedEmployee);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable int id) {
        int result = repository.deleteEmployee(id);

        if (result == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
