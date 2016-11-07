/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public class Model implements ModelOnClientInterface {

    RegistrationListener registrationListener;
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
                    Contact contact = (Contact) report.data;
                    addContactListener.handlerEvent(contact);
                }
            }
        };
        //Новый поток+

        subSystemMSG.addContact(contact,reportListener);
        //-Новый поток
    }

    @Override
    public void getListContact() {

    }

    @Override
    public void getListDialog(Contact contact) {

    }

    @Override
    public void loginMe(String login, String password) {

    }

    @Override
    public void registration(Contact contact, String password) {

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
}
