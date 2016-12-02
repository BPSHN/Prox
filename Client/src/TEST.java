import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Created by Александр on 31.10.2016.
 */
public class TEST {
    public static void main(String[] args) {

        Model model = new Model();

        //тест входа в систему
        model.regLoginMeListener((int typeResponse) -> {
            System.out.println(typeResponse);

            //тест получения списка контактов
            /*model.regGetListContactListener((ArrayList<Contact> contactArrayList) -> {
                System.out.println(contactArrayList.size());
            });
            model.getListContact();*/


            //тест удаления контакта
            model.regDelContactListener((int type) -> {
                System.out.println(type);
            });
            Contact c = new Contact();
            c.login = "Alex";
            model.deleteContact(c);
        });
        model.loginMe("Tony", "123");



    }
}
