package PO61.Geer.wdad.jdbc;

import PO61.Geer.wdad.data.managers.JDBCDataManager;
import PO61.Geer.wdad.learnXml.Department;
import PO61.Geer.wdad.learnXml.Employee;
import PO61.Geer.wdad.learnXml.JobTitle;
import PO61.Geer.wdad.learnXml.JobTitleEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JDBCDataManagerTest {
    public static void main(String[] args) {
        try {
            JDBCDataManager jdm = new JDBCDataManager();

            Department department = new Department();
            department.setName("deb");

            List<Employee> employees = new ArrayList<>();

            Employee employee = new Employee();
            JobTitle boss = new JobTitle();
            boss.setValue(JobTitleEnum.boss);
            employee.setJobTitle(boss);
            employee.setFirstname("asd");
            employee.setSecondname("dsa");
            employee.setSalary(333);
            employee.setHiredate(new Date());
            employees.add(employee);

            Employee employee1 = new Employee();
            JobTitle engineer = new JobTitle();
            engineer.setValue(JobTitleEnum.engineer);
            employee1.setJobTitle(boss);
            employee1.setFirstname("qqq");
            employee1.setSecondname("eee");
            employee1.setSalary(223);
            employee1.setHiredate(new Date());
            employees.add(employee1);

            department.setEmployees(employees);

            jdm.add(department);

//            System.out.println(jdm.salaryAverage());
//            System.out.println(jdm.salaryAverage("Managers"));
//            jdm.setJobTitle("Alex", "Merge", JobTitleEnum.boss);
//            jdm.setSalary("Alex", "Merge", 4);
//            jdm.fireEmployee("Alex", "Merge");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
