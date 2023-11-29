package DB.test;

import DB.dao.MesBufDAO;
import DB.utils.DruidUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws SQLException {
        File file = new File("C:\\Users\\25238\\Desktop\\hhh.png");
        byte[] buf = new byte[(int)file.length()];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            fis.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Connection connection = DruidUtils.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("insert into t1 values(?)");
        preparedStatement.setObject(1, buf);
        preparedStatement.executeUpdate();
        connection.close();
        preparedStatement.close();
    }

    public static String getNowTime() {
        String now = LocalTime.now().toString();
        return now.substring(0, now.indexOf('.'));
    }
}
