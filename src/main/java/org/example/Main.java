package org.example;

import java.sql.Connection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DatabaseConnector dbConnector = new DatabaseConnector();
        Connection connection = dbConnector.connect();

        if (connection != null) {
            EmployeeDAO employeeDAO = new EmployeeDAO(connection);

            employeeDAO.addEmployee("Іван Петренко", 30, "Інженер", 5000.0f);
            employeeDAO.addEmployee("Марія Іванова", 25, "Аналітик", 4500.0f);

            System.out.println("Список співробітників після додавання:");
            List<Employee> employees = employeeDAO.getAllEmployees();
            employees.forEach(System.out::println);

            employeeDAO.updateEmployee(1, "Іван Петренко", 31, "Старший інженер", 5500.0f);

            Employee updatedEmployee = employeeDAO.getEmployeeById(1);
            System.out.println("Дані співробітника після оновлення:");
            System.out.println(updatedEmployee);

            employeeDAO.deleteEmployee(2);

            System.out.println("Список співробітників після видалення:");
            employees = employeeDAO.getAllEmployees();
            employees.forEach(System.out::println);

            dbConnector.closeConnection();
        }
    }
}
