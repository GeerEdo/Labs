package PO61.Geer.wdad.data.managers;

import java.util.Properties;

public interface PreferencesManagerInterface {
    String getProperty(String key);

    void setProperty(String key, String value);

    Properties getProperties();

    void setProperties(Properties prop);

    void addBoundObject(String name, String className);

    void removeBoundObject(String name);
}
