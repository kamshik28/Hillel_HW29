package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection connection;

    public EmployeeDAO(Connection connection) {
        this.connection = connection;
    }

    public void addEmployee(String name, int age, String position, float salary) {
        String sql = "INSERT INTO employees (name, age, position, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, position);
            pstmt.setFloat(4, salary);
            pstmt.executeUpdate();
            System.out.println("Співробітника додано.");
        } catch (SQLException e) {
            System.err.println("Помилка додавання співробітника: " + e.getMessage());
        }
    }

    public void updateEmployee(int id, String name, int age, String position, float salary) {
        String sql = "UPDATE employees SET name=?, age=?, position=?, salary=? WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, position);
            pstmt.setFloat(4, salary);
            pstmt.setInt(5, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Співробітника оновлено.");
            } else {
                System.out.println("Співробітника з id " + id + " не знайдено.");
            }
        } catch (SQLException e) {
            System.err.println("Помилка оновлення співробітника: " + e.getMessage());
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM employees WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Співробітника видалено.");
            } else {
                System.out.println("Співробітника з id " + id + " не знайдено.");
            }
        } catch (SQLException e) {
            System.err.println("Помилка видалення співробітника: " + e.getMessage());
        }
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT * FROM employees WHERE id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("position"),
                        rs.getFloat("salary")
                );
            }
        } catch (SQLException e) {
            System.err.println("Помилка отримання співробітника: " + e.getMessage());
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("position"),
                        rs.getFloat("salary")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            System.err.println("Помилка отримання списку співробітників: " + e.getMessage());
        }
        return employees;
    }
}
