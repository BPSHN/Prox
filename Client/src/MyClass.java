import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class MyClass {

    public static void main(String[] args) {

    //    for(int i = 0; i < 100; ++i)
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlParameters = "param1=a&param2=b&param3=c";
                byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                int postDataLength = postData.length;
                String request = "http://localhost:8080/login";
                try
                {
                    HttpURLConnection connection = (HttpURLConnection) new URL(request).openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("User-Agent", "fff");
                    connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

                    int responseCode = connection.getResponseCode();
                    System.out.println("\nSending 'POST' request to URL : " + request);
                    System.out.println("Response Code : " + responseCode);


                }
                catch (Exception e) {}
            }
        }).start();
    }
}
