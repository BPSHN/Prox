import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Android on 05.10.2016.
 */
public interface SubSystemBDInt {

    Report registration(Contact contact); // Регистрация ++++++++++
    Report auth(Contact contact, HttpServletRequest req, HttpServletResponse resp, String address); // Авторзация
    Report addContact(Contact contact, String from_user); //Добавить контакт
    Report addMessage(Message message, String from_user); //Добавить сообщение
    Report delContact(Contact contact, String from_user); // Удаление контакта конкретного пользователя
    public String getUserLoginByID(String id); // Достает логин юзера по id сессии
}
