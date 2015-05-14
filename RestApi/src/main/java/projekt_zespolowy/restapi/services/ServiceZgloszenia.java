package projekt_zespolowy.restapi.services;

import java.sql.SQLException;
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
import org.postgresql.geometric.PGpoint;
import projekt_zespolowy.restapi.dao.ZgloszeniaDao;
import projekt_zespolowy.restapi.model.Zgloszenia;

@Path("/service/zgloszenia")
public class ServiceZgloszenia
{
    private ZgloszeniaDao zgloszeniaDao = new ZgloszeniaDao();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Zgloszenia> getAll() {
        return zgloszeniaDao.getAll();
    }

    @GET
    @Path("/getById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Zgloszenia getById(@PathParam("id") int id) {
        return zgloszeniaDao.getById(id);
    }

    @GET
    @Path("/getByType/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Zgloszenia> getByType(@PathParam("id") int id) {
        return zgloszeniaDao.getByType(id);
    }

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(String incomingData) {
        Zgloszenia zgloszenia = new Zgloszenia();

        int id_typu;
        PGpoint point;
        String opis;
        String email_uzytkownika;
        String token;

        try {
            JSONObject json = new JSONObject(incomingData);

            // Wyciagniecie danych o zgloszeniu
            json = json.getJSONObject("zgloszenia");

            // Sprawdzenie czy JSON zgloszenia zawiera wszystkie potrzebne pola
            id_typu = json.getInt("id_typu");
            //point = new PGpoint((String)json.get("wspolrzedne"));
            point = new PGpoint(json.getDouble("x"), json.getDouble("y"));
            opis = json.getString("opis");
            email_uzytkownika = json.getString("email_uzytkownika");
            token = json.getString("token");
            
        } catch (JSONException ex) {
            return Response.ok("Niepoprawny format JSONa").build();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return Response.serverError().build();
        }

        zgloszenia.setId_typu(id_typu);
        zgloszenia.setWspolrzedne(point);
        zgloszenia.setOpis(opis);
        zgloszenia.setEmail_uzytkownika(email_uzytkownika);

        return zgloszeniaDao.postZgloszenia(zgloszenia, token);
    }
}
