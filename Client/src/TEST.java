import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
            /*Contact c2 = new Contact();
            c2.login = "To";
            model.regAddContactListener((Contact con) -> {
                if(con == null)
                    System.out.println("Контакт не добавлен");
                else
                    System.out.println(con.login);
            });
            model.addContact(c2);*/

            //тест отправки сообщения
            /*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            System.out.println(dateFormat.format(date));
            Contact c3 = new Contact();
            c3.login = "Misha";
            model.regSendingCallBack((int type) -> {
                System.out.println(type);
            });
            Message message = new Message();
            message.contact = c3;
            message.text = "HELLO4!!!!";
            message.date = dateFormat.format(date);
            message.time = new SimpleDateFormat("HH:mm:ss").format(date);
            model.sendMessage(message);*/

            /*
            //тест получения всего диалога
            Contact c5 = new Contact();
            c5.login = "Alex";
            model.regGetListDialogListener((ArrayList<Message> mes) ->{
                System.out.println(mes.size());
            });
            model.getListDialog(c5);*/

            //тест обновления диалога
            Contact c5 = new Contact();
            c5.login = "Tony";
            model.getUpdateDialog(c5, (ArrayList<Message> mes) ->{
                System.out.println(mes.size());
            });



        });
        model.loginMe("Alex", "123");




    }
}
