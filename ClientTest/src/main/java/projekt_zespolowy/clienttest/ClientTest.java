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

<<<<<<< HEAD
        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/post";
=======
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/registerWithGoogle";
       //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/registerWithGoogle";
>>>>>>> pelne zmiany

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String register(String email, String haslo) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/register";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String registerWithFacebook(String email, long facebook) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("facebook", facebook)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/registerWithFacebook";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String registerWithGoogle(String email, long google) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("google", google)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/registerWithGoogle";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String login(String email, String haslo) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("haslo", haslo)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/login";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String loginWithFacebook(String email, long facebook) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("facebook", facebook)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/loginWithFacebook";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String loginWithGoogle(String email, long google) {
        JSONObject json = null;

        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("google", google)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

<<<<<<< HEAD
        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/loginWithGoogle";
=======
       //String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/login";
       String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/loginWithGoogle";
>>>>>>> pelne zmiany

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

<<<<<<< HEAD
    private String dodajZgloszenie(int id_typu, double x, double y, String opis, String adres, String email_uzytkownika, String token) {
=======
    private String dodajZgloszenie(int id_typu, double x, double y, String opis, String email_uzytkownika, String token) {
>>>>>>> pelne zmiany
        JSONObject json = null;
        try {
            json = new JSONObject()
            .put("zgloszenia", new JSONObject()
                    .put("id_typu", id_typu)
                    .put("x", x)
                    .put("y", y)
                    .put("opis", opis)
                    .put("adres", adres)
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

    private String updateStatusZgloszenia(int id_zgloszenia,int id_statusu ) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("zgloszenia", new JSONObject()
                    .put("id_zgloszenia", id_zgloszenia)
                    .put("id_statusu", id_statusu)

            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/zgloszenia/postStatusZgloszenia";

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
<<<<<<< HEAD

=======
    
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
    
>>>>>>> pelne zmiany
    public static void addZdjecie() {
        try {
            //URL url = new URL("http://localhost:8084/RestApi/service/zdjecia/post");
            URL url = new URL("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zdjecia/post");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
<<<<<<< HEAD

            File file = new File("C:\\Users\\Sebastian\\Desktop\\Nowy folder\\studia\\aa.jpg");
=======
            
            File file = new File("C:\\Users\\Sebastian\\Desktop\\test.jpg");
            //int length = (int)file.length();
>>>>>>> pelne zmiany
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

    private String updatePassword(String haslo, String email) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("haslo", haslo)
                    .put("email", email)

            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/postPassword";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String updateAdminRights(String haslo, String email, String uprawnienia) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("haslo", haslo)
                    .put("email", email)
                    .put("uprawnienia",uprawnienia )

            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/postAdminRights";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String logout(String email, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/logout";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();

        String help = "";

        // TESTY - dokladniejsze informacje o bledach sa wypisywane w oknie serwera
<<<<<<< HEAD
        //help = test.dodajUzytkownika("email5", "1234", 0, 0, "restapi");
        //help = test.register("przyklad", "1234");
        //help = test.registerWithFacebook("przyklad_facebook", 5);
        //help = test.registerWithGoogle("przyklad_google", 5);
        //help = test.login("email", "haslo");
        //help = test.loginWithFacebook("email2", 1);
        //help = test.loginWithGoogle("email3", 2);
        //help = test.dodajZgloszenie(1, 100.094703, 127.021475, "opis", "adres", "email", "853babe4628d53896fa08402a43d9d4a");
        //help = test.updatePassword("1234", "email");
        //help = test.updateAdminRights("1234", "email", "admin");
        //help = test.updateStatusZgloszenia(15, 2);
        //help = test.logout("email3", "fa6d70d545f979afc65799effba534aa");

        System.out.println(help);
        //addZdjecie();
=======
        //help = test.dodajUzytkownika("przyklad3@email.com", "1234", 1, 2, "facebook");
        //help = test.login("przyklad@email.com", "1234", 1, 2);
        //help = test.dodajZgloszenie(0, 51.15687078830179, 17.17881314456463, "opis", "194217@student.pwr.wroc.pl", "a8f591be9b5c19808bd116cc83eda419");

        //System.out.println(help);
        //addZdjecie(13, "C:\\Users\\Sebastian\\Desktop\\Nowy folder\\studia\\aa.jpg");
        addZdjecie();
        //test();
>>>>>>> pelne zmiany
    }
}