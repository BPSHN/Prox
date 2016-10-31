import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Android on 05.10.2016.
 */
public class MCookies { // Работа с куками

    public boolean isCorUser = false; // куки не прошли


    static public MCookies checkCookies(HttpServletRequest req, HttpServletResponse resp) // Проверка куков
    {
        MCookies mCookies = new MCookies();

        Cookie[] cookies = req.getCookies(); // Получение куков из запроса (куки: имя - значение)
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    if (cookie.getValue().equals(req.getSession().getId())) {
                        mCookies.isCorUser = true; // Куки сошлись
                    } else
                        break;
                }
                cookie.setMaxAge(30 * 60);
                resp.addCookie(cookie);
            }
        }
        return mCookies;
    }
}
