import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Android on 05.10.2016.
 */
public class MCookies {

    public boolean isCorUser;


    static private MCookies checkCookies(HttpServletRequest req, HttpServletResponse resp)
    {
        MCookies mCookies = new MCookies();

        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    if (cookie.getValue().equals(req.getSession().getId())) {
                        mCookies.isCorUser = true;
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
