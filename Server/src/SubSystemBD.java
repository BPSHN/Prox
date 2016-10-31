import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    public Report registration(Contact contact) {
        // Проверка, сущестует ли пользователь с таким логином
        Report report = new Report();
        String checkSqlReq = "select count(case when U.login = ? then 1 else null end) from users_table as U";
        String sqlReq = "insert into users_table (login, password, name) values (?, ?, ?)";
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
            report.type = Report.SUCCESSFUL_SQL;
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
                        String SqlReq = "insert into users_table (session_id) values (?)";
                        reqBD.setString(1, session.getId());
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

        }
        return report;
    }
    public Report addContact(Contact contact, String from_user)//Добавить контакт
    {
        System.out.println("Получилось!!!");// Либо такого нет, либо да, он есть
        return null;
    }
    public Report addMessage(Message message, String from_user) //Добавить сообщение
    {

        return null;
    }
    public Report delContact(Contact contact, String from_user) // Удаление контакта конкретного пользователя
    {
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
