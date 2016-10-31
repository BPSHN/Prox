/**
 * Created by IHaveSomeCookies on 17.10.2016.
 */
public class Model implements ModelOnClientInterface {

    RegistrationListener registrationListener;
    GetListDialogListener getListDialogListener;
    GetListContactListener getListContactListener;

    @Override
    public void findContact(Contact contact) {

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
    public void regRegistrationListener(RegistrationListener listener) {
        registrationListener = listener;
    }
}
