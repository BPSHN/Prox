import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Android on 05.10.2016.
 */
public interface SubSystemBDInt {

    Report registration(Contact contact, String addr); // Регистрация ++++++++++
    Report auth(Contact contact, HttpServletRequest req, HttpServletResponse resp, String address); // Авторзация
    Report addContact(Contact contact, String from_user); //Добавить контакт
    Report addMessage(Message message, String sID); //Добавить сообщение
    Report delContact(Contact contact, String from_user); // Удаление контакта конкретного пользователя
    Report showContact(String sID); // Отображение контактов
    String getUserLoginByID(String id); // Достает логин юзера по id сессии
}
