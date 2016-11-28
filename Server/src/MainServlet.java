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
    private String idSession = null;
    static int countOfReq = 0;
    public void myLog(HttpServletRequest req, Report report)
    {
        ///////////My super log-file
        //Ip:
        // 25.20.88.73 - Саня
        // 25.18.132.96 - Миша
        // 25.20.162.83 - Антон
        countOfReq ++;
        System.out.println(countOfReq);
        System.out.println("<<<------------------------>>>");
        if(((String)req.getRemoteAddr()).equals("25.20.88.73"))
        {
            System.out.println("Имя: Саня");
            System.out.println("Адрес: " + req.getRemoteAddr());
        }
        if(((String)req.getRemoteAddr()).equals("25.18.132.96"))
        {
            System.out.println("Имя: Миша");
            System.out.println("Адрес: " + req.getRemoteAddr());
        }
        if(((String)req.getRemoteAddr()).equals("25.20.162.83"))
        {
            System.out.println("Имя: Антон");
            System.out.println("Адрес: " + req.getRemoteAddr());
        }
        switch(report.type)
        {
            case 17:
                System.out.println("Удачная Регистрация");
                break;
            case 18:
                System.out.println("Удачная Авторизация");
                break;
            case 602:
                System.out.println("Ошибка Регистрации: пользователь существует");
                break;
            case 603:
                System.out.println("Ошибка Авторизации: неправильный логин или пароль");
                break;
        }
        System.out.println(">>>>------------------------<<<<");
        System.out.println();

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // !!! Достаю JSON-строку из запрос
        sin = req.getInputStream();
        sout = resp.getOutputStream();
        int contentLength = req.getContentLength(); // Количество байт в содержимом запроса
        byte[] jsonC = new byte[contentLength]; //
        sin.read(jsonC, 0, contentLength);
        String jsonStr = new String(jsonC, "UTF-8");
        sin.close();
        Report report = new Report(); // Этот отчет мы будем отправлять клиенту

        //УДАЛИТЬ, для заглушки
        /*String jsonStr = "123";
        Report report = new Report();
        Message message = new Message();
        message.time = "15-12-13";
        message.text = "Hello World";
        message.date = "2016-12-12";
        Contact contact = new Contact();
        contact.login = "Tony";
        contact.name = "T";
        message.contact = contact;*/
        //УДАЛИТЬ, для заглушки
        SubSystemBDInt subSystemBD = SubSystemBD.getInstance(); // Доступ к базе данных
        if (jsonStr != null || jsonStr != "")
        {
            report = JSONCoder.decode(jsonStr);
            //Заглушка
           // report.type = 1;
            //report.data = message;
            //
            switch (report.type) {
            case 1: // Пришло сообщение
                MCookies.checkCookies(req, resp);
                report = subSystemBD.addMessage((Message)report.data, MCookies.idSession);
                break;
            case 2: // Добавление контакта
                MCookies.checkCookies(req, resp);
                report = subSystemBD.addContact((Contact)report.data, MCookies.idSession);
                break;
            case 30: // Регистрация
                report = subSystemBD.registration((Contact)report.data,(String)req.getRemoteAddr());
                break;
            case 31: // Авторизация
                report = subSystemBD.auth((Contact)report.data, req, resp, null);
                break;
            case 32:
                MCookies.checkCookies(req, resp);
                report = subSystemBD.showContact(MCookies.idSession);
                break;
            }
        }
        myLog(req,report);
        // System.out.println(jsonObject.toJSONString());
        sout.write(JSONCoder.encode(report).getBytes("UTF-8"));
        resp.setStatus(200);
        sout.close();
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
