package projekt_zespolowy.clienttest;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.security.ntlm.Client;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
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

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/post";

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

        //String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/registerWithGoogle";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/registerWithGoogle";

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

        //String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/loginWithFacebook";
        String url_address = "http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/uzytkownicy/loginWithFacebook";

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

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/loginWithGoogle";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dodajZgloszenie(int id_typu, double x, double y, String opis, String adres, String email_uzytkownika, String token) {
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
        String url_address = ("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zgloszenia/post");

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

    /*public static void addZdjecie() {
        try {
            URL url = new URL("http://localhost:8084/RestApi/service/zdjecia/post");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "multipart/form-data");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            File file = new File("C:\\Users\\Sebastian\\Desktop\\Nowy folder\\studia\\aa.jpg");
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
    }*/

    public static void addZdjecie() {
        try {
            //URL url = new URL("http://localhost:8084/RestApi/service/zdjecia/post");
            URL url = new URL("http://virt2.iiar.pwr.edu.pl:8080/RestApi/service/zdjecia/post");
            File file = new File("C:\\Users\\Sebastian\\Desktop\\bomba.jpg");
            int id = 34;

            //przygotowanie pliku do wysłania - konerwsja na ciąg bajtów
            byte[] bytes = new byte[(int)file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.read(bytes, 0, bytes.length);

            //wysyłanie pliku
            Client client = Client.create();
            WebResource webResource  = client.resource(url.toString());
            ClientResponse response = webResource
                    .type("multipart/form-data")    //bardzo ważne, musi być podany ten format wymiany danych
                    .header("id", id)               //również ważne, nagłówek z polem "id"
                    .post(ClientResponse.class, bytes);

            //dotąd już nie dojdzie, więc nie odbierzemy  odpowiedzi serwera, trzeba to naprawić
            //a na razie, zakładamy ze odpowiedz jest pozytywna
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while (in.readLine() != null) {
            }
            in.close();
        } catch (ClientHandlerException e) {
            System.out.println(e.toString() + "\nWywalilo blad ale zdjecie zostalo dodane :)");
        } catch (Exception e) {
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

    private String changeGoogle(String email, long google, String token) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("email", email)
                    .put("google", google)
                    .put("token", token)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8084/RestApi/service/uzytkownicy/changeGoogle";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    public static void main(String[] args) {
        ClientTest test = new ClientTest();

        String help = "";

        // TESTY - dokladniejsze informacje o bledach sa wypisywane w oknie serwera
        //help = test.dodajUzytkownika("email5", "1234", 0, 0, "restapi");
        //help = test.register("przyklad", "1234");
        //help = test.registerWithFacebook("przyklad_facebook", 5);
        //help = test.registerWithGoogle("przykladEmail@email.com", 123456);
        //help = test.login("email", "haslo");
        //help = test.loginWithFacebook("194217@student.pwr.wroc.pl", 817901084963190l);
        //help = test.loginWithGoogle("email3", 2);
        //help = test.dodajZgloszenie(1, 100.094703, 127.021475, "opis", "adres", "194217@student.pwr.wroc.pl", "f2f0997fd69cda2407a2506216a6daf0");
        //help = test.updatePassword("1234", "email");
        //help = test.updateAdminRights("1234", "email", "admin");
        //help = test.updateStatusZgloszenia(15, 2);
        //help = test.logout("email3", "fa6d70d545f979afc65799effba534ab");
        //help = test.changeGoogle("przyklad2@email.com", 123456, "ac54e17ef970bb7acd7ecf247653f942");

        //System.out.println(help);
        addZdjecie();
    }
}