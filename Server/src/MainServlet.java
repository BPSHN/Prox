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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Report report = new Report(); // Этот отчет мы будем отправлять клиенту
        if(!(MCookies.checkCookies(req, resp)).isCorUser) // Проверка куков
        {
            //хе-хе-хе

        }
        SubSystemBDInt subSystemBD = SubSystemBD.getInstance(); // Доступ к базе данных
        Contact contact = new Contact();
        contact.login = "Tony_Puk";
        contact.name = "Misha_Puk";
        String password = "asd";
        report = subSystemBD.auth(contact, password, req, resp, null);
        //report = subSystemBD.registration(contact, password);
        System.out.println(report.type);
        System.out.println(JSONCoder.encode(contact));
        System.out.println(JSONCoder.encode((Contact) JSONCoder.decode(JSONCoder.encode(contact), 2)));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.toString());
    }
}
