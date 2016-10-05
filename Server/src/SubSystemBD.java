import java.sql.*;

/**
 * Created by Android on 05.10.2016.
 */
public class SubSystemBD implements SubSystemBDInt {

    private Connection DB;

    static SubSystemBDInt instance = null;
    private SubSystemBD()
    {
        try {
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
    public Report registration(Contact contact, String password) {
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
                report.type = Report.SUCCESSFULL;
            ResultSet s = reqBD.getResultSet();
            s.next();
            if(s.getInt("count") == 1) {
                report.type = Report.THE_USER_EXIST;
                return report;
            }
            reqBD = DB.prepareStatement(sqlReq);
            reqBD.setString(1, contact.login);
            reqBD.setString(2, password);
            reqBD.setString(3, contact.name);
            report.type = Report.SUCCESSFULL;
        } catch (SQLException e) {
            System.out.println(e.toString());
            report.type = Report.SQL_EXCEPTION;
            return report;
        }
        return report;
    }
}
