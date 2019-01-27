package PO61.Geer.wdad.rmi;

import PO61.Geer.wdad.data.managers.DataManager;
import PO61.Geer.wdad.data.managers.PreferencesManager;
import PO61.Geer.wdad.rmi.DataManagerImpl;

import javax.xml.bind.JAXBException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        PreferencesManager pm = PreferencesManager.getInstance();

        System.setProperty("java.security.policy", pm.getProperty(PreferencesManager.POLICYPATH));
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            DataManager dataManager = new DataManagerImpl("src/PO61.Geer/wdad/learnXml/first.xml");
            DataManager stub = (DataManager) UnicastRemoteObject.exportObject(dataManager, 0);
            Registry registry;
            if (pm.getProperty(PreferencesManager.CREATEREGISTRY).equals("yes")) {
                registry = LocateRegistry.createRegistry(
                        Integer.parseInt(pm.getProperty(PreferencesManager.REGISTRYPORT)));

                pm.addBoundObject("DataManager", DataManager.class.getName());
                Runtime.getRuntime().addShutdownHook(new Thread(() -> pm.removeBoundObject("DataManager")));
            } else {
                registry = LocateRegistry.getRegistry(pm.getProperty(PreferencesManager.REGISTRYADDRESS),
                        Integer.parseInt(pm.getProperty(PreferencesManager.REGISTRYPORT)));
            }

            registry.bind("DataManager", stub);
        } catch (RemoteException | AlreadyBoundException | JAXBException e) {
            e.printStackTrace();
        }
    }
}
