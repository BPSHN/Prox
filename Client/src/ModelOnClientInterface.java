/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public interface ModelOnClientInterface {
    void addContact(Contact contact);
    void getListContact();
    void getListDialog(Contact contact);
    void loginMe(String login,String password);
    void registration(Contact contact);
    void sendMessage(Message message);
    void regGetListContactListener(GetListContactListener listener);
    void regGetListDialogListener(GetListDialogListener listener);
    void regRegistrationListener(RegistrationListener listener);
    void regAddContactListener(AddContactListener listener);
    void regDelContactListener(UniversalListener delContactListener);
    void regLoginMeListener(LoginMeListener listener);
    void deleteContact(Contact contact);
}
