package PO61.Geer.wdad.rmi;

import PO61.Geer.wdad.data.managers.DataManager;
import PO61.Geer.wdad.learnXml.Organization;
import PO61.Geer.wdad.learnXml.XmlTask;
import PO61.Geer.wdad.learnXml.Department;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Optional;

public class DataManagerImpl extends XmlTask implements DataManager {
    public DataManagerImpl(String path) throws JAXBException {
        super(path);
    }

    @Override
    public void add(Department department) {
        Optional<Department> schrodingerDepartment = Organization.getDepartments().stream()
                .filter(dep -> dep.getName().equals(department.getName()))
                .findAny();

        if (schrodingerDepartment.isPresent()) {
            Department dep = schrodingerDepartment.get();
            dep.setEmployees(department.getEmployees());
        } else {
            List<Department> departments = Organization.getDepartments();
            departments.add(department);
        }
    }
}
