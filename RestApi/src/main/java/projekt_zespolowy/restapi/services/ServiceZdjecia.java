package projekt_zespolowy.restapi.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
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
    public Response add(@HeaderParam("id") int id, @FormDataParam("file") InputStream incomingData) {
        boolean webKit = false;
        System.out.println("weszlem");
        //File file = new File("C:\\Users\\Sebastian\\Desktop\\tmp" + id + ".jpg");
        String fileName = "tmp" + id + ".jpg";
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

        try {
            OutputStream out = new FileOutputStream(file);
            int read;
            byte[] bytes = new byte[1024];
            
            while ((read = incomingData.read(bytes)) != -1) {
                int idx = 0;
                if (bytes[0] == bytes[1] && bytes[1] == bytes[2] && bytes[2] == bytes[3] && bytes[3] == bytes[4] && bytes[4] == bytes[5] && bytes[5] == 45) {
                    webKit = true;
                    for (int i = 6; i < bytes.length; i++) {
                        if (bytes[i] == -1) {
                            idx = i;
                            break;
                        }
                    }
                    
                    read -= idx;
                    for (int i = 0; idx < bytes.length; idx++, i++) {
                        bytes[i] = bytes[idx];
                    }

                }

                out.write(bytes, 0, read);
            }
            
            if (webKit)
            {
                byte[] tmp = new byte[(int) file.length()];
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                bis.read(tmp, 0, tmp.length);
                
                file.delete();
                //file = new File("C:\\Users\\Sebastian\\Desktop\\tmp" + id + ".jpg");
                file = new File(dir, fileName);
                
                int idx = tmp.length;
                for (int i = tmp.length - 1; i > 0; i--) {System.out.println(i);
                    if (tmp[i] == tmp[i-1] && tmp[i-1] == tmp[i-2] && tmp[i-2] == tmp[i-3] && tmp[i-3] == tmp[i-4] && tmp[i-4] == tmp[i-5] && tmp[i-5] == 45) {
                        idx = i - 5;
                        break;
                    }
                }
                
                out.close();
                out = new FileOutputStream(file);
                out.write(tmp, 0, idx);
            }
            
            zdjeciaDao.postZdjecia(id, file);
            file.delete();
            dir.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("/addZdjecie")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addZdjecie(@FormDataParam("file") InputStream incomingData) {
        System.out.println("weszlem");
        int read, id;
        byte[] bytes = new byte[1024], id_zgloszenia = new byte[4];
        
        //odczytanie numeru zgloszenia
        try {
            if (incomingData.read(id_zgloszenia) == -1) {
                throw new Exception();
            }
            id = ByteBuffer.wrap(id_zgloszenia).getInt();
        } catch (Exception ex) {
            System.out.println("Wystapil blad przy odczytywaniu numeru zgloszenia");
            return Response.serverError().entity("Wystapil blad przy odczytywaniu numeru zgloszenia").build();
        }
        
        //File file = new File("C:\\Users\\Sebastian\\Desktop\\tmp" + id + ".jpg");
        String fileName = "tmp" + id + ".jpg";
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
        
        try {
            OutputStream out = new FileOutputStream(file);
            
            //odczytanie i zapisanie zdjęcia w tymczasowym pliku
            while ((read = incomingData.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            zdjeciaDao.postZdjecia(id, file);
            file.delete();
            dir.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}