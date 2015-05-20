package projekt_zespolowy.restapi.services;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import projekt_zespolowy.restapi.dao.UzytkownicyDao;
import projekt_zespolowy.restapi.model.Uzytkownicy;

@Path("/service/uzytkownicy")
public class ServiceUzytkownicy
{
    private UzytkownicyDao uzytkownicyDao = new UzytkownicyDao();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getAll() {
        return uzytkownicyDao.getAll();
    }

    @GET
    @Path("/getByEmail/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uzytkownicy getByEmail(@PathParam("email") String email) {
        return uzytkownicyDao.getByEmail(email);
    }

    @GET
    @Path("/getWithAdminRights")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Uzytkownicy> getWithAdminRights() {
        return uzytkownicyDao.getWithAdminRights();
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String email;
        String haslo;
        long facebook;
        long google;
        String typ;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            haslo = json.getString("haslo");
            facebook = json.getLong("facebook");
            google = json.getLong("google");
            typ = json.getString("typ");
        } catch (JSONException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setHaslo(haslo);
        uzytkownicy.setFacebook(facebook);
        uzytkownicy.setGoogle(google);
        uzytkownicy.setTyp(typ);

        return uzytkownicyDao.postUzytkownicy(uzytkownicy);
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register (String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String email;
        String haslo;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            haslo = json.getString("haslo");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setHaslo(haslo);

        return uzytkownicyDao.register(uzytkownicy);
    }


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login (String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String email;
        String haslo;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            haslo = json.getString("haslo");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setHaslo(haslo);

        return uzytkownicyDao.login(uzytkownicy);
    }

    @POST
    @Path("/loginWithFacebook")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginWithFacebook (String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String email;
        long facebook;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            facebook = json.getLong("facebook");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setFacebook(facebook);

        return uzytkownicyDao.loginWithFacebook(uzytkownicy);
    }

    @POST
    @Path("/loginWithGoogle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginWithGoogle (String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String email;
        long google;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            google = json.getLong("google");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setGoogle(google);

        return uzytkownicyDao.loginWithGoogle(uzytkownicy);
    }

    @POST
    @Path("/changeGoogle")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeGoogle (String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();

        String email;
        long google;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("uzytkownicy");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            email = json.getString("email");
            google = json.getLong("google");
            token = json.getString("token");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setGoogle(google);
        uzytkownicy.setToken(token);

        return uzytkownicyDao.changeGoogle(uzytkownicy);
    }

    @POST
    @Path("/postPassword")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePassword(String incomingData) throws Exception
    {
        Uzytkownicy uzytkownicy = new Uzytkownicy();
        String haslo = null;
        String email = null;

        try {
            JSONObject json = new JSONObject(incomingData);
            json = json.getJSONObject("uzytkownicy");
            email = json.getString("email");
            haslo = json.getString("haslo");

        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }
        uzytkownicy.setHaslo(haslo);
        uzytkownicy.setEmail(email);

        return uzytkownicyDao.updatePassword(uzytkownicy);
    }

    @POST
    @Path("/updateUprawnienia")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUprawnienia(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();
        String admin_email;
        String token;
        String user_email;
        String uprawnienia;

        try {
            JSONObject json = new JSONObject(incomingData);
            json = json.getJSONObject("uzytkownicy");
            admin_email = json.getString("admin_email");
            token = json.getString("token");
            user_email = json.getString("user_email");
            uprawnienia = json.getString("uprawnienia");

        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(admin_email);
        uzytkownicy.setToken(token);
        uzytkownicy.setUprawnienia(uprawnienia);

        return uzytkownicyDao.updateUprawnienia(uzytkownicy, user_email);
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();
        String email;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            json = json.getJSONObject("uzytkownicy");

            email = json.getString("email");
            token = json.getString("token");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        }catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setToken(token);

        return uzytkownicyDao.logout(uzytkownicy);
    }

    @POST
    @Path("/activate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response activate(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();
        String email;

        try {
            JSONObject json = new JSONObject(incomingData);

            json = json.getJSONObject("uzytkownicy");

            email = json.getString("email");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        }catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);

        return uzytkownicyDao.activate(uzytkownicy);
    }
    
    @POST
    @Path("/valid")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response valid(String incomingData) {
        Uzytkownicy uzytkownicy = new Uzytkownicy();
        String email;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            json = json.getJSONObject("uzytkownicy");

            email = json.getString("email");
            token = json.getString("token");
        } catch (JSONException ex) {
            System.err.println(ex.toString());
            return Response.status(500).entity("Niepoprawny format JSONa").build();
        }catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        uzytkownicy.setEmail(email);
        uzytkownicy.setToken(token);

        return uzytkownicyDao.valid(uzytkownicy);
    }
}
