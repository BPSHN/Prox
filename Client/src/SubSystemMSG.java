import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public class SubSystemMSG implements SubSystemMSGInterface{


    @Override
    public void addContact(Contact contact, ReportListener reportListener) {
        String string = JSONCoder.encode(contact); //получили JSON-строку контакта
        Report report = new Report();
        report.data = string;
        report.type = 2;
        String stringReport = JSONCoder.encode(report);

        try //
        {

            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());
            connection.setRequestMethod("POST");
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());


            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(stringReport);
            wr.flush();
            wr.close();
            OutputStream outputStream = connection.getOutputStream();

            int responseCode = connection.getResponseCode();
            System.out.println("Код ошибки : " + responseCode);
            System.out.println("Строка : " + stringReport);

        }
        catch (Exception e) {}
    }

    @Override
    public void auth(Contact contact, ReportListener reportListener, String string) {

    }

    @Override
    public void delContact(Contact contact, ReportListener reportListener) {

    }

    @Override
    public void findContact(Contact contact, ReportListener reportListener, int I1, int I2) {

    }

    @Override
    public void registrarion(Contact contact, ReportListener reportListener, String string) {

    }

    @Override
    public void registrListener(ReportListener reportListener) {

    }

    @Override
    public void sendMessage(Message message, ReportListener reportListener) {

    }
}
