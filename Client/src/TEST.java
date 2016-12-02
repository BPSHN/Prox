import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader;

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

        //тест регистрации
        /*model.regRegistrationListener(new RegistrationListener() {
            @Override
            public void handlerEvent(int typeResponse) {

            }
        });
        Contact contact = new Contact();
        contact.login = "Tony1245";
        contact.password = "123";
        contact.name = "A";
        model.registration(contact);*/

        //тест входа в систему
        model.regLoginMeListener((int typeResponse) -> {
            System.out.println(typeResponse);

            //тест получения списка контактов
            /*model.regGetListContactListener((ArrayList<Contact> contactArrayList) -> {
                System.out.println(contactArrayList.size());
            });
            model.getListContact();*/


            //тест удаления контакта
            /*model.regDelContactListener((int type) -> {
                System.out.println(type);
            });
            Contact c = new Contact();
            c.login = "Misha";
            model.deleteContact(c);*/


            //тест получения списка найденных контактов
            Contact c = new Contact();
            c.login = "Mi";
            model.regFindContactsListener((ArrayList<Contact> contactArrayList) ->{
                System.out.println(contactArrayList.size());
            });
            model.findContacts(c);
        });
        model.loginMe("Tony", "123");




    }
}
