package projekt_zespolowy.clienttest;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.client.ClientResponse;

public class ClientTest
{
    
    private String dodajUzytkownika(String email, String haslo, long facebook, long google, String typ) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
                    .put("facebook", facebook)
                    .put("google", google)
                    .put("typ", typ)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/registerWithGoogle";
       //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/registerWithGoogle";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }
    
    private String login(String email, String haslo, long facebook, long google) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
                    .put("facebook", facebook)
                    .put("google", google)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

       //String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/login";
       String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/loginWithGoogle";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dodajZgloszenie(int id_typu, double x, double y, String opis, String email_uzytkownika, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
            .put("zgloszenia", new JSONObject()
                    .put("id_typu", id_typu)
                    .put("x", x)
                    .put("y", y)
                    .put("opis", opis)
                    .put("email_uzytkownika", email_uzytkownika)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        //String url_address = "http://localhost:8084/RestApi/service/zgloszenia/post";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zgloszenia/post";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dataTransfer(JSONObject json, String url_address) {
        String print_returned = "";

        try {
            URL url = new URL(url_address);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(json.toString());
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            while ((input = in.readLine()) != null) {
                print_returned += input + "\n";
            }

            in.close();
        } catch (IOException e) {
            if (e.toString().contains("java.net.ConnectException: Connection refused: connect")) {
                print_returned += "Zapomniales zalaczyc serwer" + "\n";
            }
            else {
                print_returned += e;
            }
        }

        return print_returned;
    }
    
    public static void addZdjecie(int id, String directory) {
        try {
            //String zapytanie = "http://localhost:8084/RestApi/service/zdjecia/post";
            String zapytanie = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zdjecia/post";
            Client client = Client.create();
            WebResource webResource = client.resource(zapytanie);
            
            //tworzymy tablicę bajtów ze zdjęcia
            File file = new File(directory);
            byte[] bytes = new byte[(int)file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(bytes, 0, bytes.length);
            
            //Wysyłamy parametry i wywołujemy zapytanie
            /*ClientResponse response = webResource
                    .type("multipart/form-data")    //<--bardzo ważne, nie używamy do dodawania zdjeć JSONa
                                                    //a multipart form-data
                    .header("id", id)   //id_zgloszenia
                    .post(ClientResponse.class, bytes); //samo zdjęcie*/
            ClientResponse response = webResource
                    .type("multipart/form-data")
                    .header("id", id)
                    .post(ClientResponse.class, bytes);
            
            //Dalej test nie przejdzie, wywali błąd i wyświetli długaśny log, kod poniżej nasłuchuje odpowiedzi
            //serwera więc na zapytanie, więc takowej odpowidzi nie odbierzemy(mimo ze wsyłana). Sprawia to ze 
            //funkcja jest trochę upośledzona, a my nie dowiemy się czy zdjęcie udało się dodać (bo może już było
            //podane jakieś dla danego zgłoszenia). Ale bardzo to jednak nie przeszkadza bo zdjęcie zostanie opercja
            //dodania zdjęcia (jeśli to możliwe) zakończy się sukcesem. Ale przy odrobinie czasu pasowałoby to naprawić.
            URL url = new URL(zapytanie);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String input;
            String print_returned = "";
            while ((input = in.readLine()) != null) {
                print_returned += input + "\n";
            }
            System.out.println(print_returned);
            in.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static void addZdjecie() {
        try {
            //URL url = new URL("http://localhost:8084/RestApi/service/zdjecia/post");
            URL url = new URL("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zdjecia/post");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            
            File file = new File("C:\\Users\\Sebastian\\Desktop\\test.jpg");
            //int length = (int)file.length();
            byte[] bytes = new byte[(int)file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(bytes, 0, bytes.length);
            
            OutputStream os = connection.getOutputStream();
            os.write(bytes, 0, bytes.length);
            os.flush();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while (in.readLine() != null) {
            }
            System.out.println("\nSukces");
            in.close();
        } catch (Exception e) {
            System.out.println("\nNie przeszlo");
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();

        String help;

        // TESTY - dokladniejsze informacje o bledach sa wypisywane w oknie serwera
        //help = test.dodajUzytkownika("przyklad3@email.com", "1234", 1, 2, "facebook");
        //help = test.login("przyklad@email.com", "1234", 1, 2);
        //help = test.dodajZgloszenie(0, 51.15687078830179, 17.17881314456463, "opis", "194217@student.pwr.wroc.pl", "a8f591be9b5c19808bd116cc83eda419");

        //System.out.println(help);
        //addZdjecie(13, "C:\\Users\\Sebastian\\Desktop\\Nowy folder\\studia\\aa.jpg");
        addZdjecie();
        //test();
    }
}