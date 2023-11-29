package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtils {
    private static Properties properties = new Properties();
    static {
        try {
            properties.load(new FileInputStream("C:\\Users\\25238\\Desktop\\文件夹\\javaweb代码\\oa3\\resources\\db.properties"));
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static String url = "jdbc:mysql://"+properties.getProperty("ip")+":"+properties.getProperty("port")+"/" + properties.getProperty("db");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, properties.getProperty("user"), properties.getProperty("pwd"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(Object... objs) {
        if(objs == null) throw new NullPointerException();
        if(objs.length == 0) return;
        try {
            for (Object o : objs) {
                if(o instanceof Connection) {
                    ((Connection) o).close();
                }else if (o instanceof ResultSet) {
                    ((ResultSet) o).close();
                } else if (o instanceof Statement) {
                    ((Statement) o).close();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
