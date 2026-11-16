
package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeRepository {

    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper
    private final RowMapper<Employee> rowMapper =
            (rs, rowNum) -> new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("salary")
            );

    // 1. Insert Employee
    public int addEmployee(Employee employee) {

        String sql = "INSERT INTO employees " +
                "(id, name, department, salary) VALUES (?, ?, ?, ?)";

        return jdbcTemplate.update(
                sql,
                employee.getId(),
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }

    // 2. Get All Employees
    public List<Employee> getAllEmployees() {

        String sql = "SELECT * FROM employees";

        return jdbcTemplate.query(sql, rowMapper);
    }

    // 3. Get Employee By ID
    public Employee getEmployeeById(int id) {

        String sql = "SELECT * FROM employees WHERE id = ?";

        List<Employee> list =
                jdbcTemplate.query(sql, rowMapper, id);

        if (list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }

    // 4. Update Employee
    public int updateEmployee(int id, Employee employee) {

        String sql = "UPDATE employees SET name = ?, " +
                "department = ?, salary = ? WHERE id = ?";

        return jdbcTemplate.update(
                sql,
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary(),
                id
        );
    }

    // 5. Delete Employee
    public int deleteEmployee(int id) {

        String sql = "DELETE FROM employees WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
