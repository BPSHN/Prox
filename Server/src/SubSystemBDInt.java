import com.sun.org.apache.regexp.internal.RE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Android on 05.10.2016.
 */
public interface SubSystemBDInt {

    Report registration(Contact contact, String password); // Регистрация
    Report auth(Contact contact, String password, HttpServletRequest req, HttpServletResponse resp, String address); // Авторзация
}
