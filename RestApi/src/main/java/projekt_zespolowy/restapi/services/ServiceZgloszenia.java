/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import projekt_zespolowy.restapi.dao.ZgloszeniaDao;
import projekt_zespolowy.restapi.model.Zgloszenia;
/**
 *
 * @author guncda, Piotr, Kacper
 */
@Path("/service/zgloszenia")
public class ServiceZgloszenia {
    
    private ZgloszeniaDao zgloszeniaDao = new ZgloszeniaDao();
    
    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Zgloszenia/*Object*/> getAll() {
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
        int httpCode = 500, idx = 0, oldIdx = 0;
        try {
            if ((idx = zgloszenia.setId_uzytkownikaByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if ((idx = zgloszenia.setId_typuByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if ((idx = zgloszenia.setId_disqusByJSON(incomingData, idx)) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if (zgloszenia.setWspolrzedneByJSON(incomingData, idx) == oldIdx) {
                throw new Exception();
            }
            oldIdx = idx;
            if (zgloszenia.setOpisByJSON(incomingData, idx) == oldIdx) {
                throw new Exception();
            }
        } catch (Exception ex) {
            System.out.println("Niepowodzenie odczytania danych przychodzacych");
        }
        
        try {
            httpCode = zgloszeniaDao.postZgloszenia(zgloszenia);
        } catch (Exception ex) {

        }

        if (httpCode == 200) {
            System.out.println("{\"id_zgloszenia\":" + zgloszenia.getId_zgloszenia() + "}");
            return Response.ok("{\"id_zgloszenia\":" + zgloszenia.getId_zgloszenia() + "}", MediaType.APPLICATION_JSON).build();
        }
        return Response.status(httpCode).entity("Operacja dodania zgloszenia nie powiodla sie").build();
    }
}