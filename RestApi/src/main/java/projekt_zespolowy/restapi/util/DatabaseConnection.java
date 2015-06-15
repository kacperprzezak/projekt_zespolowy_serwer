package projekt_zespolowy.restapi.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
    private static Connection databaseConnection = null;

    public void establishConnection() {
        String database, user, passwd;
        try {
            String fileName = "rest.conf";
            File dir = new File("/var/lib/postgresql");
            File file = new File(dir, fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            database = bufferedReader.readLine().substring("database = ".length());
            user = bufferedReader.readLine().substring("user = ".length());
            passwd = bufferedReader.readLine().substring("passwd = ".length());
        } catch (Exception ex) {
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/" + database;

        try {
            Class.forName("org.postgresql.Driver");
            databaseConnection = DriverManager.getConnection(url, user, passwd);
        } catch(Exception ex) {
            System.out.println("Nie udalo sie ustanowic polaczenia do bazy danych");
        }
    }

    public void closeConnection() {
        try {
            if (databaseConnection != null) {
                databaseConnection.close();
            }
        } catch(Exception e) {
            System.out.println("Wystapil problem z zamknieciem bazy danych");
        }
    }

    public Connection getConnection() {
        return databaseConnection;
    }
}
