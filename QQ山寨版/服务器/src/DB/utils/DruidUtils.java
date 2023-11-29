package DB.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtils {
    private static DataSource dataSource;

    //初始化Druid连接池
    static {
        Properties properties = new Properties();
        try{
            properties.load(new FileInputStream("src\\druid.properties"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取连接
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //关闭资源
    public static void close(Object... objects) {
        if(objects == null) throw new NullPointerException();
        try {
            //Connection, ResultSet, Statement, (PreparedStatement, CallableStatement)
            for (Object obj : objects) {
                if (obj instanceof Connection) {
                    ((Connection) obj).close();
                } else if (obj instanceof ResultSet) {
                    ((ResultSet) obj).close();
                } else if (obj instanceof Statement) {
                    ((Statement) obj).close();
                } else if (obj != null) {
                    throw new RuntimeException("未知实参: " + obj);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
