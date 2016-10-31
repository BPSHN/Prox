import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class MainServlet extends HttpServlet {

    private InputStream sin;
    private OutputStream sout;
    private String User_Login = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if(!(MCookies.checkCookies(req, resp)).isCorUser) // Проверка куков, при авторизации и регистрации false
        //{}
        // !!! Достаю JSON-строку из запроса
        sin = req.getInputStream();
        sout = resp.getOutputStream();
        int contentLength = req.getContentLength(); // Количество байт в содержимом запроса
        byte[] jsonC = new byte[contentLength]; //
        sin.read(jsonC, 0, contentLength);
        String jsonStr = new String(jsonC, "UTF-8");
        // !!!
        // ЗАГЛУШКА
        Contact contact = new Contact();
        contact.login = "Tony_Puk";
        contact.name = "Misha_Puk";
        String password = "asd";
        ////
        SubSystemBDInt subSystemBD = SubSystemBD.getInstance(); // Доступ к базе данных
        if (!(MCookies.checkCookies(req, resp)).isCorUser) // Проверка куков, при авторизации и регистрации false
        {
            User_Login = subSystemBD.getUserLoginByID((String)req.getSession().getId());
        }
        if (jsonStr != null || jsonStr != "")
        {
                Report report = new Report(); // Этот отчет мы будем отправлять клиенту
                report = JSONCoder.decode(jsonStr);
                switch (report.type) {
                    case 1: // Пришло сообщение
                        report = subSystemBD.addMessage((Message)report.data, User_Login);
                        break;
                    case 2: // Добавление контакта
                        report = subSystemBD.addContact((Contact)report.data, User_Login);
                        break;
                    case 30: // Регистрация
                        report = subSystemBD.registration(contact, password);
                        break;
                    case 31: // Авторизация
                        report = subSystemBD.registration(contact, password);
                        break;
                }
        }


        //report = subSystemBD.auth(contact, password, req, resp, null);
        //report = subSystemBD.registration(contact, password);
        //System.out.println(report.type);
        //System.out.println(JSONCoder.encode(contact));
        //System.out.println(JSONCoder.encode((Contact) JSONCoder.decode(JSONCoder.encode(contact), 2)));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.toString());
    }
}
