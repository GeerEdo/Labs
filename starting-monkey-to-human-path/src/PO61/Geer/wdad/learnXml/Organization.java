package PO61.Geer.wdad.learnXml;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Organization {
    String name;
    List<Department> departments;

    public String getName() {
        return name;
    }

    @XmlAttribute(required = true)
    public void setName(String name) {
        this.name = name;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    @XmlElement(name = "department")
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
