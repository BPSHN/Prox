/**
 * Created by Android on 05.10.2016.
 */
public class Report {
    // Ошибки

    static final int SQL_EXCEPTION = 601; // Ошибка выполнения запроса
    static final int THE_USER_EXIST = 602; // Ошибка: Пользователь существует
    static final int THE_USER_IS_NOT_EXIST = 603; // Такого пользователя несуществует
    static final int COOKIE_FAIL = 604; // Ошибка: У пользователя нет куков или они не верны
    static final int JSON_DECODE_FAIL = 605; // Ошибка декодирования json
    static final int SUCCESSFUL_SQL = 200;
    static final int SUCCESSFUL_AUTH = 201; // Удачная авторизация

    // TYPE
    static final int MESSAGE = 1; // Сообщение
    static final int CONTACT = 2; // Контакт

    public int type;
    public Object data;
}
