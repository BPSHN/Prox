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
       /* model.regRegistrationListener(new RegistrationListener() {
            @Override
            public void handlerEvent(int typeResponse) {
                System.out.println(typeResponse);
            }
        });
        Contact contact = new Contact();
        contact.login = "Tony1245754252542";
        contact.password = "123";
        contact.name = "A";
        model.registration(contact);*/
        //тест входа в систему
        model.regLoginMeListener((int typeResponse) -> {
            System.out.println(typeResponse + " Вход выполнен");

            //тест получения списка контактов
           /* model.regGetListContactListener((ArrayList<Contact> contactArrayList) -> {
                System.out.println("Контакты получены " + contactArrayList.size());

                //тест получения списка найденных контактов
                Contact c = new Contact();
                c.login = "Misha";
                model.regFindContactsListener((ArrayList<Contact> contactArrayList2) ->{
                    System.out.println("Контакты найдены " + contactArrayList2.size());

                    //тест добавления контакта в список контактов
                    Contact c2 = new Contact();
                    c2.login = "Tony";
                    model.regAddContactListener((Contact con) -> {
                        if(con == null)
                            System.out.println("Контакт не добавлен");
                        else
                            System.out.println(con.login);
                    });
                    model.addContact(c2);

                });
                model.findContacts(c);

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
            /*Contact c = new Contact();
            c.login = "Misha";
            model.regFindContactsListener((ArrayList<Contact> contactArrayList) ->{
                System.out.println(contactArrayList.size());
            });
            model.findContacts(c);*/


            //тест добавления контакта в список контактов
            Contact c2 = new Contact();
            c2.login = "Tony";
            model.regAddContactListener((Contact con) -> {
                if(con == null)
                    System.out.println("Контакт не добавлен");
                else
                    System.out.println(con.login);
            });
            model.addContact(c2);
        });
        model.loginMe("Tony", "123");




    }
}
