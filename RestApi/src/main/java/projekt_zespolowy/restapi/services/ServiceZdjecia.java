package projekt_zespolowy.restapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import projekt_zespolowy.restapi.dao.ZdjeciaDao;
import projekt_zespolowy.restapi.model.Zdjecia;

@Path("/service/zdjecia")
public class ServiceZdjecia {

    private ZdjeciaDao zdjeciaDao = new ZdjeciaDao();

    @GET
    @Path("/getById/{id}")
    @Produces("image/jpg")
    public Response getById(@PathParam("id") int id) {
        Zdjecia zdjecia = null;

        try {
            if ((zdjecia = zdjeciaDao.getById(id)) == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException ex) {
            return Response.status(404).entity("Nie znaleziono zdjecia").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }

        ResponseBuilder response = Response.ok((Object) zdjecia.getZdjecie());
        response.header("Content-Disposition", "attachment; filename=zdj" + id + ".jpg");

        return response.build();
    }

    /*@POST
    @Path("/post")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(@HeaderParam("id") int id, @FormDataParam("file") InputStream incomingData) {
        System.out.println("weszlem");
        //File file = new File("C:\\Users\\Sebastian\\Desktop\\tmp" + id + ".jpg");
        File file = new File("/tmp/upload/tmp" + id + ".jpg");
        file.deleteOnExit();

        try {
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];
            
            while ((read = incomingData.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            zdjeciaDao.postZdjecia(id, file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }*/
    
    @POST
    @Path("/post")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(@FormDataParam("file") InputStream incomingData) {
        //File file = new File("C:\\Users\\Sebastian\\Desktop\\tmp" + 13 + ".jpg");
        //File file = new File("/tmp/upload/tmp/test.txt"/* + 24 + ".jpg"*/);
        String fileName = "tmp" + 31 + ".jpg";
        File dir = new File("/tmp");
        dir.mkdir();
        File file = new File(dir, fileName);
        
        try {
            if (!file.createNewFile()) {
                return Response.status(500).entity("Brak dostepu do pliku").build();
            }
        } catch (IOException e) {
            return Response.status(500).entity("Inny blad z dostepem do pliku").build();
        }
        file.deleteOnExit();

        try {
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];
            
            while ((read = incomingData.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            zdjeciaDao.postZdjecia(31, file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}