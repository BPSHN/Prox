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

    //add 06.12
    private UniversalListener sendingCallBack;

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
                        //System.out.println(arr.toString());
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


    //add 06.12
    //для сокращения повтора кода getListDialog и getUpdateDialog
    private ReportListener getReportListenerForListDialog(GetListDialogListener listener)
    {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.SUCCESSFUL_MES){
                    ArrayList<Message> messages = new ArrayList<>();
                    String strListArr = (String) report.data;
                    try {
                        JSONObject jsonObj;
                        JSONParser parser = new JSONParser();
                        Object obj = parser.parse(strListArr);
                        jsonObj = (JSONObject) obj;

                        JSONArray arr = (JSONArray) jsonObj.get("messages");// new JSONArray();
                        Iterator iter = arr.iterator();
                        String cont;
                        Message msg;
                        while (iter.hasNext()) {
                            cont = (String) iter.next();
                            msg = (Message) JSONCoder.decode(cont, Report.MESSAGE);
                            messages.add(msg);
                        }

                        // System.out.println(arr.toString());
                        listener.handlerEvent(messages);
                    }
                    catch (Exception e) {
                        System.out.println("public void getListContact()" + e.toString());
                    }
                }
                else
                    listener.handlerEvent(null);
            }
        };
        return reportListener;
    }

    @Override
    public void getListDialog(final Contact contact) {
        ReportListener reportListener = getReportListenerForListDialog(getListDialogListener);
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestDialog(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getUpdateDialog(Contact contact, GetListDialogListener listener) {
        ReportListener reportListener = getReportListenerForListDialog(listener);
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestUpdateDialog(contact,reportListener);
            }
        });
        myThready.start();	//Запуск потока
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

    //add 06.12
    @Override
    public void sendMessage(Message message) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.SUCCESSFUL_SEND_MES){
                    sendingCallBack.handlerEvent(Report.SUCCESSFUL_SEND_MES);
                }
                else
                    sendingCallBack.handlerEvent(0);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.sendMessage(message,reportListener);
            }
        });
        myThready.start();	//Запуск потока
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

    @Override
    public void regSendingCallBack(UniversalListener listener) {
        sendingCallBack = listener;
    }

    @Override
    public void setMyStatus(int status, UniversalListener listener) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                listener.handlerEvent(report.type);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.sendStatus(status,reportListener); // поменять константы
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getMyContact(UniversalListenerWithObject listener) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                listener.handlerEvent(report.type,report.data);
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestMyContact(reportListener);
            }
        });
        myThready.start();	//Запуск потока
    }

    @Override
    public void getUpdateContacts(final GetListContactListener listener) {
        ReportListener reportListener = new ReportListener() {
            @Override
            public void handler(Report report) {
                if (report.type == Report.NO_UPDATES)
                    listener.handleEvent(null);
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
                        while (iter.hasNext()) {
                            cont = (String) iter.next();
                            contact = (Contact) JSONCoder.decode(cont, 2);
                            contactArrayList.add(contact);
                        }
                        //System.out.println(arr.toString());
                    } catch (Exception e) {
                        System.out.println("public void getListContact()" + e.toString());
                    }
                    ;
                    listener.handleEvent(contactArrayList);
                }
            }
        };
        //Создание потока
        Thread myThready = new Thread(new Runnable()
        {
            public void run() //Этот метод будет выполняться в побочном потоке
            {
                subSystemMSG.requestUpdateContacts(reportListener);
            }
        });
        myThready.start();	//Запуск потока
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
