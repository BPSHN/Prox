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

public class MyClass extends HttpServlet {

    private InputStream sin;
    private OutputStream sout;
    Connection connectionDB = null;

    String dbTableName = "clients_table";
    @Override
    public void init() throws ServletException {
        super.init();

        try {
            Class.forName("org.postgresql.Driver");

            System.out.println("Драйвер подключен");
            connectionDB = DriverManager.getConnection("jdbc:postgresql://localhost:6666/postgres",
                    "postgres", "iamadminpostgres");
            System.out.println("Соединение установлено");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String json = req.getRequestedSessionId();
        System.out.println(json);
        System.out.println(req.toString());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.toString());
    }
}
