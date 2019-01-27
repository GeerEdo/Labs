package PO61.Geer.wdad.data.managers;

import PO61.Geer.wdad.learnXml.XmlTaskInterface;
import PO61.Geer.wdad.learnXml.Department;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataManager extends Remote, XmlTaskInterface {
    void add(Department department) throws RemoteException;
}
