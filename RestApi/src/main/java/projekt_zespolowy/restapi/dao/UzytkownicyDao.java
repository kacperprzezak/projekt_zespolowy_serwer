package projekt_zespolowy.restapi.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import projekt_zespolowy.restapi.model.Uzytkownicy;
import projekt_zespolowy.restapi.util.DatabaseConnection;

public class UzytkownicyDao
{
    private static DatabaseConnection connection = new DatabaseConnection();

    public List<Uzytkownicy> getAll() {
        List<Uzytkownicy> list = new ArrayList<>();
        Uzytkownicy uzytkownicy;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setFacebook(resultSet.getLong("facebook"));
                uzytkownicy.setGoogle(resultSet.getLong("google"));
                uzytkownicy.setTyp(resultSet.getString("typ"));
                uzytkownicy.setToken(resultSet.getString("token"));
                uzytkownicy.setUprawnienia(resultSet.getString("uprawnienia"));
                uzytkownicy.setCzy_aktywowany(resultSet.getBoolean("czy_aktywowany"));
                uzytkownicy.setData_rejestracji(resultSet.getString("data_rejestracji"));

                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public Uzytkownicy getByEmail(String email) {
        Uzytkownicy uzytkownicy = null;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE email = '" + email + "'");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setFacebook(resultSet.getLong("facebook"));
                uzytkownicy.setGoogle(resultSet.getLong("google"));
                uzytkownicy.setTyp(resultSet.getString("typ"));
                uzytkownicy.setToken(resultSet.getString("token"));
                uzytkownicy.setUprawnienia(resultSet.getString("uprawnienia"));
                uzytkownicy.setCzy_aktywowany(resultSet.getBoolean("czy_aktywowany"));
                uzytkownicy.setData_rejestracji(resultSet.getString("data_rejestracji"));
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return uzytkownicy;
    }

    public List<Uzytkownicy> getWithAdminRights() {
        List<Uzytkownicy> list = new ArrayList<>();
        Uzytkownicy uzytkownicy;
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM uzytkownicy WHERE uprawnienia = 'admin'");

            while (resultSet.next()) {
                uzytkownicy = new Uzytkownicy();

                uzytkownicy.setEmail(resultSet.getString("email"));
                uzytkownicy.setHaslo(resultSet.getString("haslo"));
                uzytkownicy.setFacebook(resultSet.getLong("facebook"));
                uzytkownicy.setGoogle(resultSet.getLong("google"));
                uzytkownicy.setTyp(resultSet.getString("typ"));
                uzytkownicy.setToken(resultSet.getString("token"));
                uzytkownicy.setUprawnienia(resultSet.getString("uprawnienia"));
                uzytkownicy.setCzy_aktywowany(resultSet.getBoolean("czy_aktywowany"));
                uzytkownicy.setData_rejestracji(resultSet.getString("data_rejestracji"));

                list.add(uzytkownicy);
            }
        }
        catch(Exception ex) {
            System.out.println("Zapytanie nie zostalo wykonane: " + ex.toString());
        }
        connection.closeConnection();
        return list;
    }

    public Response postUzytkownicy(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT adduzytkownik('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getHaslo() + "', " + uzytkownicy.getFacebook()
                    + ", " + uzytkownicy.getGoogle() + ", '" + uzytkownicy.getTyp() + "'" + ")");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podany email jest juz zajety").build();
            }
            else if (ex.toString().contains("(facebook)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podane id facebooka jest juz zajete").build();
            }
            else if (ex.toString().contains("(google)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podane if google jest juz zajete").build();
            }

            connection.closeConnection();
            return Response.ok("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }
    
    public Response register(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT register('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getHaslo() + "')");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.status(500).entity("Podany email jest juz zajety").build();
            }
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }
    
    public Response registerWithFacebook(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT register('" + uzytkownicy.getEmail()
                    + "', " + uzytkownicy.getFacebook() + ")");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.status(500).entity("Podany email jest juz zajety").build();
            }
            else if (ex.toString().contains("(facebook)=") && ex.toString().contains("już istnieje")) {
                return Response.status(500).entity("Podane id facebooka jest juz zajete").build();
            }
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }
    
    public Response registerWithGoogle(Uzytkownicy uzytkownicy) {
        Statement statement;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            statement.executeQuery("SELECT register('" + uzytkownicy.getEmail()
                    + "', " + uzytkownicy.getGoogle() + ")");
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            if (ex.toString().contains("(email)=") && ex.toString().contains("już istnieje")) {
                return Response.status(500).entity("Podany email jest juz zajety").build();
            }
            else if (ex.toString().contains("(google)=") && ex.toString().contains("już istnieje")) {
                return Response.ok("Podane if google jest juz zajete").build();
            }
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        return Response.ok("OK").build();
    }
    
    public Response login(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT login('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getHaslo() + "')");
            
            while (resultSet.next()) {
                uzytkownicy.setToken(resultSet.getString(1));
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        if (uzytkownicy.getToken() == null) {
            return Response.status(404).entity("Nie ma takieg uzytkownika").build();
        } else {
            return Response.ok("{\"token\":\"" + uzytkownicy.getToken() + "\"}").build();
        }
    }
    
    public Response loginWithFacebook(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT loginWithFacebook('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getFacebook() + "')");
            
            while (resultSet.next()) {
                uzytkownicy.setToken(resultSet.getString(1));
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        if (uzytkownicy.getToken() == null) {
            return Response.status(404).entity("Nie ma takieg uzytkownika").build();
        } else {
            return Response.ok("{\"token\":\"" + uzytkownicy.getToken() + "\"}").build();
        }
    }
    
    public Response loginWithGoogle(Uzytkownicy uzytkownicy) {
        Statement statement;
        ResultSet resultSet;

        try {
            connection.establishConnection();
            statement = connection.getConnection().createStatement();
            resultSet = statement.executeQuery("SELECT loginWithFacebook('" + uzytkownicy.getEmail()
                    + "', '" + uzytkownicy.getGoogle() + "')");
            
            while (resultSet.next()) {
                uzytkownicy.setToken(resultSet.getString(1));
            }
        } catch (Exception ex) {
            // Wypisanie bledu na serwer
            System.err.println(ex);

            // Zwrocenie informacji o bledzie uzytkownikowi
            connection.closeConnection();
            return Response.status(500).entity("Wystapil nieznany blad").build();
        }

        connection.closeConnection();
        if (uzytkownicy.getToken() == null) {
            return Response.status(404).entity("Nie ma takieg uzytkownika").build();
        } else {
            return Response.ok("{\"token\":\"" + uzytkownicy.getToken() + "\"}").build();
        }
    }
    
}
