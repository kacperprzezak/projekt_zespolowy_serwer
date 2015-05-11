package projekt_zespolowy.restapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
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

    @POST
    @Path("/post")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response add(/*@FormDataParam("data") int id, */@FormDataParam("file") InputStream incomingData) {
        System.out.println("Wszedlem");
        Zdjecia zdjecia = new Zdjecia();
        int id = 6;
        File file = new File("C:\\Users\\Sebastian\\Desktop\\tmp" + id + ".jpg");

        try {
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];

            while ((read = incomingData.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            out.flush();
            out.close();

            /*zdjecia.setId_zgloszenia(id);
            zdjecia.setZdjecie(bytes);*/
            zdjeciaDao.postZdjecia(id, file);
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}
