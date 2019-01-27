package PO61.Geer.wdad.rmi;

import PO61.Geer.wdad.data.managers.DataManager;
import PO61.Geer.wdad.data.managers.PreferencesManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        PreferencesManager pm = PreferencesManager.getInstance();

        System.setProperty("java.security.policy", pm.getProperty(PreferencesManager.POLICYPATH));
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            Registry registry = LocateRegistry.getRegistry(pm.getProperty(PreferencesManager.REGISTRYADDRESS),
                    Integer.parseInt(pm.getProperty(PreferencesManager.REGISTRYPORT)));

            DataManager dataManager = (DataManager) registry.lookup("DataManager");

            System.out.println(dataManager.salaryAverage());
            System.out.println(dataManager.salaryAverage("Workers"));
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}
