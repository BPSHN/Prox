import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

/**
 * Created by Android on 05.10.2016.
 */
public class SubSystemBD implements SubSystemBDInt {
// Сеанс работы с БД
    private Connection DB; // Класс java.sql.Connection представляет в JDBC сеанс работы с базой данных.

    static SubSystemBDInt instance = null;
    private SubSystemBD()
    {
        try {
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            DB = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres", "ihsc2504");
            System.out.println("Соединение установлено");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static SubSystemBDInt getInstance() {
        if(instance == null)
        {
            instance = new SubSystemBD();
        }
        return instance;
    }

    @Override
    public Report registration(Contact contact, String addr) {
        // Проверка, сущестует ли пользователь с таким логином
        Report report = new Report();
        report.data = contact;
        String checkSqlReq = "select count(case when U.login = ? then 1 else null end) from users_table as U";
        String sqlReq = "insert into users_table (login, password, name, address) values (?, ?, ?, ?)";
        try {
            PreparedStatement reqBD = DB.prepareStatement(checkSqlReq);
            reqBD.setString(1, contact.login);
            if(!reqBD.execute())
                report.type = Report.SQL_EXCEPTION;
            else
                report.type = Report.SUCCESSFUL_SQL;
            ResultSet s = reqBD.getResultSet();
            s.next();
            if(s.getInt("count") == 1) {
                report.type = Report.THE_USER_EXIST;
                return report;
            }
            reqBD = DB.prepareStatement(sqlReq);
            reqBD.setString(1, contact.login);
            reqBD.setString(2, contact.password);
            reqBD.setString(3, contact.name);
            reqBD.setString(4, addr);
            reqBD.execute();
            report.type = Report.SUCCESSFUL_REG;//SUCCESSFUL_SQL;
        } catch (SQLException e) {
            System.out.println(e.toString());
            report.type = Report.SQL_EXCEPTION;
            return report;
        }
        return report;
    }

    @Override
    public Report auth(Contact contact, HttpServletRequest req, HttpServletResponse resp, String address){
        Report report = new Report();
        if(!(MCookies.checkCookies(req, resp)).isCorUser) // Проверка куков
        {
            if((contact.login != null || contact.login != "") || (contact.password != null || contact.password != "")) {
                String checkSqlReq = "select count(case when U.login = ? and U.password = ? then 1 else null end) from users_table as U";
                try {
                    PreparedStatement reqBD = DB.prepareStatement(checkSqlReq);
                    reqBD.setString(1, contact.login);
                    reqBD.setString(2, contact.password);
                    if (!reqBD.execute())
                        report.type = Report.SQL_EXCEPTION;
                    else
                        report.type = Report.SUCCESSFUL_SQL;
                    ResultSet s = reqBD.getResultSet();
                    s.next();
                    if (s.getInt("count") == 1) {
                        HttpSession session = req.getSession();
                        String SqlReq = "insert into users_table (session_id) values (?) where login = ?";
                        reqBD.setString(1, session.getId());
                        reqBD.setString(2, contact.login);
                        reqBD.execute();
                        report.type = Report.SUCCESSFUL_AUTH;
                    }
                    else
                    {
                        report.type = Report.THE_USER_IS_NOT_EXIST;
                        return report;
                    }
                }catch(SQLException e) {
                        System.out.println(e.toString());
                        report.type = Report.SQL_EXCEPTION;
                        return report;
                    }
            }
            else {
                report.type = Report.COOKIE_FAIL;
                System.out.println("Ваш логин или пароль неверный");
                return report;
            }
        }
        else {
            // ЗДЕСЬ МОЖНО СДЕЛАТЬ ЕСЛИ ЕСТЬ КУКИ
        }
        return report;
    }
    public Report addContact(Contact contact, String from_user)//Добавить контакт
    {
        System.out.println("Получилось!!!");// Либо такого нет, либо да, он есть (контакт)
        Report report = new Report();
        report.data = contact;
        report.type = 2;
        return report;
    }
    public Report addMessage(Message message, String from_user) //Добавить сообщение
    {
        Report report = new Report();
        report.data = message; // записываем сообщение в репорт
        report.type = Report.MESSAGE;
        String sqlReq = "insert into messages (from_user, to_user, message, date, time) values (?, ?, ?, ?, ?)";
        String sqlAddr = "select address from users_table where login = ?";
        try {
            // Помещение в БД сообщения
            PreparedStatement reqBD = DB.prepareStatement(sqlReq);
            reqBD.setString(1, from_user);
            reqBD.setString(2, message.contact.login);
            reqBD.setString(3, message.text);
            reqBD.setString(4, message.date);
            reqBD.setString(5, message.time);
            reqBD.execute();
            // Получение адреса получателя
            reqBD = DB.prepareStatement(sqlAddr);
            reqBD.setString(1, message.contact.login);
            ResultSet s = reqBD.getResultSet();
            s.next();
            String addr = s.getString("address");
            // Отправление нужному пользователю
            String stringReport = JSONCoder.encode(report);
            try
            {
                URL url = new URL("http://" + addr + ":8080");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                System.out.println("Метод запроса: " + connection.getRequestMethod());
                connection.setRequestProperty("User-Agent", "fff");
                connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                connection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(stringReport);
                wr.flush();
                wr.close();
                /*OutputStream outputStream = connection.getOutputStream();

                //Прослушка ответа+
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                is = connection.getInputStream();

                byte[] buffer = new byte[8192]; // Задаем размер буфера
                // Далее читаем ответ
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                answerData = baos.toByteArray();
                String JSONstr = new String(answerData, "UTF-8");
                Report answerReport = JSONCoder.decode(JSONstr);
                //int answerCode = answerReport.type;
                int responseCode = connection.getResponseCode();
                System.out.println("Код ошибки : " + responseCode);
                System.out.println("Строка : " + stringReport);
                System.out.println("Строка : " + JSONstr);
                reportListener.handler(answerReport);
                *///-Прослушка ответа
            }
            catch (Exception e) {}
        } catch (SQLException e) {
            System.out.println(e.toString());
            report.type = Report.SQL_EXCEPTION;
            return report;
        }
        report.type = Report.SUCCESSFUL_SEND_MES;
        return report;
    }
    public Report delContact(Contact contact, String from_user) // Удаление контакта конкретного пользователя
    {
        return null;
    }

    @Override
    public Report showContact(String login) {
        String sqlShow = "select friend from contact_list";
        return null;
    }

    public String getUserLoginByID(String id)
    {
        String login;
        String checkSqlReq = "select login from users_table where sessio_id = ?";
        try {
            PreparedStatement reqBD = DB.prepareStatement(checkSqlReq);
            reqBD.setString(1, id);
            if (!reqBD.execute())
                login = null;
            else {
                ResultSet s = reqBD.getResultSet();
                s.next();
                login = s.getString("login");
            }
        }catch(SQLException e) {
            System.out.println(e.toString());
            login = null;
            return null;
        }
        return login;
    }
}
