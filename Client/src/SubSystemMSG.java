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
        try //
        {

            URL url = new URL("http://localhost:8080/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());
            connection.setRequestMethod("POST");
            System.out.println("Метод запроса: " +
                    connection.getRequestMethod());

            int responseCode = connection.getResponseCode();
            connection.setRequestProperty("User-Agent", "fff");
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            //System.out.println(string);
            System.out.println("Код ошибки : " + responseCode);
            System.out.println("Контакт : " + string);

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
