package PO61.Geer.wdad.data.storage;

import PO61.Geer.wdad.data.managers.PreferencesManager;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;

public class DataSourceFactory {
    public static DataSource createDataSource() {
        PreferencesManager pm = PreferencesManager.getInstance();

        return createDataSource(
                pm.getProperty(PreferencesManager.HOSTNAME),
                Integer.parseInt(pm.getProperty(PreferencesManager.DATASOURCEPORT)),
                pm.getProperty(PreferencesManager.DATABASENAME),
                pm.getProperty(PreferencesManager.DATASOURCEUSERNAME),
                pm.getProperty(PreferencesManager.DATASOURCEPASS)
        );
    }

    public static DataSource createDataSource(String host, int port, String dbName, String user, String password) {
        MysqlDataSource mysql = new MysqlDataSource();
        mysql.setServerName(host);
        mysql.setPortNumber(port);
        mysql.setDatabaseName(dbName);
        mysql.setUser(user);
        mysql.setPassword(password);
        return mysql;
    }
}
