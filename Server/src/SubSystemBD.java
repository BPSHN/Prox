import java.sql.Connection;
import java.sql.DriverManager;

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
            connectionDB = DriverManager.getConnection("jdbc:postgresql://localhost:6666/postgres",
                    "postgres", "iamadminpostgres");
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
        return  null;
    }
}
