/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public interface ModelOnClientInterface {
    void findContact(Contact contact);
    void getListContact();
    void getListDialog(Contact contact);
    void loginMe(String login,String password);
    void registration(Contact contact, String password);
    void sendMessage(Message message);
    void regGetListContactListener(GetListContactListener listener);
    void regGetListDialogListener(GetListDialogListener listener);
    void regRegistrationListener(RegistrationListener listener);
}
