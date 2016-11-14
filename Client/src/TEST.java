import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Александр on 31.10.2016.
 */
public class TEST {
    public static void main(String[] args) {
            Contact contact = new Contact();
            String login = "123";
            String name = "123";
            contact.login = login;
            contact.name = name;
            SubSystemMSG subSystemMSG = new SubSystemMSG();
            ReportListener reportListener = new ReportListener() {
                @Override
                public void handler(Report report) {

                }
            };
            subSystemMSG.addContact((Contact) contact, reportListener);
    }
}
