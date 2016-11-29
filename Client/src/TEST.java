import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Александр on 31.10.2016.
 */
public class TEST {
    public static void main(String[] args) {
            /*Contact contact = new Contact();
            String login = "123";
            String name = "Super_Duper";
            contact.login = login;
            contact.name = name;
            contact.password = "123";
            SubSystemMSG subSystemMSG = new SubSystemMSG();
            Model model = new Model();
            ReportListener reportListener = new ReportListener() {
                @Override
                public void handler(Report report) {

                }
            };
            //model.addContact(contact);
            //model.registration((Contact) contact);
            model.loginMe("123","123");
            //subSystemMSG.addContact((Contact) contact, reportListener);*/

        Model model = new Model();
        model.regLoginMeListener((int typeResponse) -> {
            System.out.println(typeResponse);
            GetListContactListener getListContactListener = new GetListContactListener() {
                @Override
                public void handleEvent(ArrayList<Contact> contactArrayList) {

                }
            };
            model.regGetListContactListener(getListContactListener);
            model.getListContact();
        });
        model.loginMe("Tony", "123");



    }
}
