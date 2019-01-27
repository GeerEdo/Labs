package PO61.Geer.wdad.learnXml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.OptionalDouble;

public class XmlTask implements XmlTaskInterface {
    private Organization organization;

    XmlTask(String path) throws JAXBException {
        System.setProperty("javax.xml.accessExternalDTD", "all");

        File file = new File(path);
        JAXBContext jaxbContext = JAXBContext.newInstance(Organization.class);

        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        organization = (Organization) jaxbUnmarshaller.unmarshal(file);
    }

    public void save(String path) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(Organization.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        OutputStream os = new FileOutputStream(path);
        marshaller.marshal(organization, os);
        os.close();
    }

    @Override
    public int salaryAverage() {
        OptionalDouble average = organization.getDepartments().stream()
                .map(Department::getEmployees)
                .flatMap(Collection::stream)
                .mapToInt(Employee::getSalary)
                .average();

        return average.isPresent() ? (int) average.getAsDouble() : 0;
    }

    @Override
    public int salaryAverage(String departmentName) {
        List<Employee> employees = organization.getDepartments().stream()
                .filter(department -> department.getName().equals(departmentName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .getEmployees();

        int sum = employees.stream()
                .mapToInt(Employee::getSalary)
                .sum();

        return sum / employees.size();
    }

    private Employee findEmployee(String firstName, String secondName) {
        return organization.getDepartments().stream()
                .map(Department::getEmployees)
                .flatMap(Collection::stream)
                .filter(employee -> employee.getFirstname().equals(firstName) &&
                        employee.getSecondname().equals(secondName))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void setJobTitle(String firstName, String secondName, JobTitleEnum newJobTitle) {
        Employee employee = findEmployee(firstName, secondName);
        employee.getJobTitle().setValue(newJobTitle);
    }

    @Override
    public void setSalary(String firstName, String secondName, int newSalary) {
        Employee employee = findEmployee(firstName, secondName);
        employee.setSalary(32000);
    }

    @Override
    public void fireEmployee(String firstName, String secondName) {
        Department department = organization.getDepartments().stream()
                .filter(dep -> dep.getEmployees().stream()
                        .anyMatch(employee -> employee.getFirstname().equals(firstName) &&
                                employee.getSecondname().equals(secondName)))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

        department.getEmployees().removeIf(employee -> employee.getFirstname().equals(firstName) &&
                employee.getSecondname().equals(secondName));
    }
}
