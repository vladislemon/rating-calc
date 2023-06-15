package main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 * slimon
 * 25.08.2014
 */
public class URLConnection {

    public static void connect() throws Exception {
        /*URL url = new URL("http://blazar.ru/login.php?do=login");
        connection = (HttpURLConnection) url.openConnection();*/

        //String params = "do=login&username=slimon&password=LaimerLuser01193713795Blazar";
        String params = "do=login&username=Besta&password=rfnz1996";

        sendPost("http://blazar.ru/login.php?do=login", params);
        sendGet("http://blazar.ru/x.php");
        sendGet("http://blazar.ru/alliance.php");
    }

    public static void sendPost(String url, String params) throws Exception {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("POST");
        //connection.setRequestProperty("", "");
        connection.setRequestProperty("Host", "blazar.ru");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Length", "53");
        connection.setRequestProperty("Cache-Control", "max-age=0");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("Origin", "http://blazar.ru");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Referer", "http://blazar.ru/login.php");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
        connection.setRequestProperty("Cookie", "cw=428; ch=257; ct=pub; PHPSESSID=p9gg7o19189d0gls4c6qv0c1i4; __utma=115682773.1451268952.1404562359.1408962661.1408977758.102; __utmb=115682773.2.10.1408977758; __utmc=115682773; __utmz=115682773.1404562359.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); mrc=app_id%3D629715%26is_app_user%3D0%26window_id%3DCometName_9e6ad6ccec918c5b116904d0d8ba28d9");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(params);
        wr.flush();
        wr.close();

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + params);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "Windows-1251"));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();
        System.out.println("\n==============");
        //System.out.println(response.toString());
    }

    public static void sendGet(String url) throws Exception {
        URL u = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
        connection.setRequestMethod("GET");
        //connection.setRequestProperty("", "");
        connection.setRequestProperty("Host", "blazar.ru");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36");
        connection.setRequestProperty("Referer", "http://blazar.ru/x.php");
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        connection.setRequestProperty("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
        connection.setRequestProperty("Cookie", "cw=428; ch=257; ct=pub; PHPSESSID=p9gg7o19189d0gls4c6qv0c1i4; __utma=115682773.1451268952.1404562359.1408962661.1408977758.102; __utmb=115682773.2.10.1408977758; __utmc=115682773; __utmz=115682773.1404562359.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); mrc=app_id%3D629715%26is_app_user%3D0%26window_id%3DCometName_9e6ad6ccec918c5b116904d0d8ba28d9");
        connection.setDoInput(true);
        connection.setDoOutput(true);

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "Windows-1251"));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();

        File file = new File("text.txt");
        if(file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(response.toString().getBytes());
        System.out.println("\n==============");
        System.out.println(response.toString());
    }
}
