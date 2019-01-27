package PO61.Geer.wdad.learnXml;
import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface XmlTaskInterface {
    int salaryAverage();

    int salaryAverage(String departmentName);

    void setJobTitle(String firstName, String secondName, JobTitleEnum newJobTitle) throws JAXBException, IOException;

    void setSalary(String firstName, String secondName, int newSalary) throws JAXBException, IOException;

    void fireEmployee(String firstName, String secondName) throws JAXBException, IOException;

    void save(String path) throws JAXBException, IOException;
}
