import simple.*;
import simple.parser.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public class Model implements ModelOnClientInterface {

    private RegistrationListener registrationListener;
    private LoginMeListener loginMeListener;
    private GetListDialogListener getListDialogListener;
    private GetListContactListener getListContactListener;
    private AddContactListener addContactListener;
    //add 02.12
    private UniversalListener delContactListener;
    private GetListContactListener findContactsListener;

    SubSystemMSG subSystemMSG;

    public Model (){
        subSystemMSG = new SubSystemMSG();
    }

    @Override
    public void addContact(Contact contact) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.SUCCESSFUL_ADD){ //если нашел контакт
                    Contact contact = (Contact) JSONCoder.decode((String) report.data,2);
                    addContactListener.handlerEvent(contact);
                }
                else
                    addContactListener.handlerEvent(null);
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

    @Override
    public void regFindContactsListener(GetListContactListener listener) {
        findContactsListener = listener;
    }

    //add 02.12
    @Override
    public void deleteContact(Contact contact) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                delContactListener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.delContact(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void findContacts(Contact contact) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if(report.data != null)
                {
                    ArrayList<Contact> contactArrayList = new ArrayList<>();
                    String strListArr = (String) report.data;
                    try {
                        JSONObject jsonObj;
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(strListArr);
                        jsonObj = (JSONObject) obj;

                        JSONArray arr = (JSONArray) jsonObj.get("findList");// new JSONArray();
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
                    findContactsListener.handleEvent(contactArrayList);
                }
                else
                    findContactsListener.handleEvent(null);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.findContact(contact, reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    //add 02.12
    @Override
    public void regDelContactListener(UniversalListener delContactListener) {
        this.delContactListener = delContactListener;
    }
}
