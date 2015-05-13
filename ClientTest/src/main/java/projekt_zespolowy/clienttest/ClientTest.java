package projekt_zespolowy.clienttest;

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
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.postgresql.geometric.PGpoint;

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

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/register";

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

        String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/login";

        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

    private String dodajZgloszenie(int id_typu, PGpoint punkt, String opis, String email_uzytkownika) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("zgloszenia", new JSONObject()
                    .put("id_typu", id_typu)
                    .put("wspolrzedne", punkt)
                    .put("opis", opis)
                    .put("email_uzytkownika", email_uzytkownika)
            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";
        }

        String url_address = "http://localhost:8080/RestApi/service/zgloszenia/post";

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

    public static void addZdjecie() {
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
            return "Klient: Blad przy tworzeniu JSONa";        }

         String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/postPassword";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

     private String updateAdminRights(String haslo, String email,String uprawnienia) {
        JSONObject json = null;
        try {
            json = new JSONObject()
                .put("uzytkownicy", new JSONObject()
                    .put("haslo", haslo)
                    .put("email", email)
                    .put("uprawnienia",uprawnienia )

            );
        } catch (JSONException ex) {
            return "Klient: Blad przy tworzeniu JSONa";        }

         String url_address = "http://localhost:8080/RestApi/service/uzytkownicy/postAdminRights";
        String help;
        help = dataTransfer(json, url_address);
        return help;
    }

     public static void main(String[] args) {
        ClientTest test = new ClientTest();

        String help = "";

        // TESTY - dokladniejsze informacje o bledach sa wypisywane w oknie serwera
        //help = test.dodajUzytkownika("przyfkldad@email.com", "1d2f34", 0, 0, "restapi");
        //help = test.login("email", "haslo", 0, 0);
        //help = test.dodajZgloszenie(1, new PGpoint(51.094703, 17.021475), "opis", "email");
        //help = test.updatePassword("haselko", "email3");
        //help = test.updateAdminRights("haselko", "email3","admin");
        //help = test.updateStatusZgloszenia(15, 2);

        System.out.println(help);
        //addZdjecie();
    }
}