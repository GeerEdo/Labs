package PO61.Geer.wdad.learnXml;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

public class Employee {
    String firstname;
    String secondname;

    Date hiredate;
    int salary;
    JobTitle jobTitle;

    public String getFirstname() {
        return firstname;
    }

    @XmlAttribute(required = true)
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    @XmlAttribute(required = true)
    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public Date getHiredate() {
        return hiredate;
    }

    @XmlElement
    @XmlJavaTypeAdapter(dateFormate.class)
    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public int getSalary() {
        return salary;
    }

    @XmlElement
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    @XmlElement(name = "jobtitle")
    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }
}