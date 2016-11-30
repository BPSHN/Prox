import simple.*;
import simple.parser.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public class Model implements ModelOnClientInterface {

    RegistrationListener registrationListener;
    LoginMeListener loginMeListener;
    GetListDialogListener getListDialogListener;
    GetListContactListener getListContactListener;
    AddContactListener addContactListener;
    SubSystemMSG subSystemMSG;
    public Model (){
        subSystemMSG = new SubSystemMSG();
    }

    @Override
    public void addContact(Contact contact) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.NOT_FIND_CONTACT){ //если не нашел контакт
                    addContactListener.handlerEvent(null);
                }
                if (report.type == Report.FIND_CONTACT){ //если нашел контакт
                    //String strContact = (String ) report.data;
                    Contact contact = (Contact) JSONCoder.decode((String) report.data,2);
                    addContactListener.handlerEvent(contact);
                }
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.addContact(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    //добавил 29
    @Override
    public void getListContact() {

        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if(report.type != Report.SUCCESSFUL_FRIENDS)
                    getListContactListener.handleEvent(null);
                else {
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    String strListArr = (String) report.data;
                    try {
                        JSONObject jsonObj;
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(strListArr);
                        jsonObj = (JSONObject) obj;

                        JSONArray arr = (JSONArray) jsonObj.get("friends");// new JSONArray();
                        Iterator iter = arr.iterator();
                        String cont;
                        Contact contact;
                        while(iter.hasNext())
                        {
                            cont = (String) iter.next();
                            contact = (Contact)JSONCoder.decode(cont, 2);
                            contactArrayList.add(contact);
                        }

                        System.out.println(arr.toString());

                    }
                    catch (Exception e) {
                        System.out.println("public void getListContact()" + e.toString());
                    };
                    getListContactListener.handleEvent(contactArrayList);
                }
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestListContacts(reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getListDialog(Contact contact) {

    }

    @Override
    public void loginMe(String login, String password) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                loginMeListener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.loginMe(login,password,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void registration(Contact contact) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                registrationListener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.registration(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public void regGetListContactListener(GetListContactListener listener) {
        getListContactListener = listener;
    }

    @Override
    public void regGetListDialogListener(GetListDialogListener listener) {
        getListDialogListener = listener;
    }

    @Override
    public void regAddContactListener(AddContactListener listener) {
        addContactListener = listener;
    }

    @Override
    public void regRegistrationListener(RegistrationListener listener) {
        registrationListener = listener;
    }

    @Override
    public void regLoginMeListener(LoginMeListener listener) {loginMeListener = listener; }
}
