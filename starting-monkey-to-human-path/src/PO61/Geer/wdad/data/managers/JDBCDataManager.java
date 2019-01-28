package PO61.Geer.wdad.data.managers;

import PO61.Geer.wdad.data.storage.DataSourceFactory;
import PO61.Geer.wdad.learnXml.Department;
import PO61.Geer.wdad.learnXml.JobTitleEnum;

import java.sql.*;
import java.util.Calendar;

public class JDBCDataManager implements DataManager {
    @Override
    public int salaryAverage() {
        Connection connection = DataSourceFactory.createDataSource().getConnection();
        String query = "SELECT AVG(salary) FROM employees";

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                connection.close();
                return rs.getInt("AVG(salary)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
        return 0;
    }

    @Override
    public int salaryAverage(String departmentName) {
        Connection connection = DataSourceFactory.createDataSource().getConnection();
        String query = "SELECT AVG(salary) FROM employees LEFT JOIN departments ON employees.department_id = departments.id WHERE departments.name = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, departmentName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                connection.close();
                return rs.getInt("AVG(salary)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
        return 0;
    }

    @Override
    public void setJobTitle(String firstName, String secondName, JobTitleEnum newJobTitle) {
        Connection connection = DataSourceFactory.createDataSource().getConnection();
        String query = "UPDATE employees SET jobtitle_id = (SELECT id FROM jobtitles WHERE name = ?) WHERE first_name = ? AND second_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(2, firstName);
            stmt.setString(3, secondName);
            stmt.setString(1, newJobTitle.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    @Override
    public void setSalary(String firstName, String secondName, int newSalary) {
        Connection connection = DataSourceFactory.createDataSource().getConnection();
        String query = "UPDATE employees SET salary = ? WHERE first_name = ? AND second_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(2, firstName);
            stmt.setString(3, secondName);
            stmt.setDouble(1, newSalary);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    @Override
    public void fireEmployee(String firstName, String secondName) {
        Connection connection = DataSourceFactory.createDataSource().getConnection();
        String query = "DELETE FROM employees WHERE first_name = ? AND second_name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, secondName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    @Override
    public void add(Department department) {
        Connection connection = DataSourceFactory.createDataSource().getConnection();
        int departmentId = getDepartmentId(department, connection);

        try {
            PreparedStatement insertStmt = connection.prepareStatement("INSERT INTO employees " +
                    "(first_name, second_name, birth_date, hire_date, salary, jobtitle_id, department_id)" +
                    "VALUES (?, ? , ?, ?, ?, (SELECT id FROM jobtitles WHERE name = ?), ?)");
            PreparedStatement updateStmt = connection.prepareStatement("UPDATE employees " +
                    "SET birth_date = ?, hire_date = ?, salary = ?, jobtitle_id = (SELECT id FROM jobtitles WHERE name = ?) " +
                    "WHERE first_name = ? AND second_name = ?");
            PreparedStatement addDepartmentStmt = connection.prepareStatement("INSERT INTO departments (name, description) VALUES (?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            if (departmentId >= 0) {
                department.getEmployees().forEach(employee -> {
                    try {
                        updateStmt.setDate(1, new Date(Calendar.getInstance().getTime().getTime()));
                        updateStmt.setDate(2, new Date(employee.getHiredate().getTime()));
                        updateStmt.setDouble(3, employee.getSalary());
                        updateStmt.setString(4, employee.getJobTitle().getValue().toString());
                        updateStmt.setString(5, employee.getFirstname());
                        updateStmt.setString(6, employee.getSecondname());
                        updateStmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                addDepartmentStmt.setString(1, department.getName());
                addDepartmentStmt.setString(2, department.getName());
                departmentId = addDepartmentStmt.executeUpdate();
                final int depId = getDepartmentId(department, connection);

                department.getEmployees().forEach(employee -> {
                    try {
                        insertStmt.setString(1, employee.getFirstname());
                        insertStmt.setString(2, employee.getSecondname());
                        insertStmt.setDate(3, new Date(Calendar.getInstance().getTime().getTime()));
                        insertStmt.setDate(4, new Date(employee.getHiredate().getTime()));
                        insertStmt.setDouble(5, employee.getSalary());
                        insertStmt.setString(6, employee.getJobTitle().getValue().toString());
                        insertStmt.setInt(7, depId);
                        insertStmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.close();
    }

    private int getDepartmentId(Department department, Connection connection) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM departments WHERE name = ? LIMIT 1");
            stmt.setString(1, department.getName());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
