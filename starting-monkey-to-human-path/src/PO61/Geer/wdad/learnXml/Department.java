package PO61.Geer.wdad.learnXml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Department {
    String name;
    List<Employee> employees;

    public String getName() {
        return name;
    }

    @XmlAttribute(required = true)
    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    @XmlElement(name = "employee")
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
