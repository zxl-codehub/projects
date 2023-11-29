package DB.dao;

import DB.utils.DruidUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class Basic<T> {
    //通用的增删改方法，返回受影响的行数
    int dml(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        int rows;
        try {
            connection = DruidUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setArgs(preparedStatement, args);
            rows = preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.close(connection, preparedStatement);
        }
        return rows;
    }

    //本方法用于查询操作，返回包含查找结果的集合
    ArrayList<T> query(String sql, Class<T> cls, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<T> arrayList;
        try{
            connection = DruidUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setArgs(preparedStatement, args);
            resultSet = preparedStatement.executeQuery();
            arrayList = resultSetToArrayList(resultSet, cls);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.close(connection, preparedStatement, resultSet);
        }
        return arrayList;
    }

    //如果只要结果表的第一行记录，可以使用此方法
    T querySingleRecord(String sql, Class<T> cls, Object... args) {
        return query(sql, cls, args).get(0);
    }

    //如果只要结果表第一行第一列的数据，可以使用此方法
    Object querySingleObject(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Object res;
        try{
            connection = DruidUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            setArgs(preparedStatement, args);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            res = resultSet.getObject(1);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.close(connection, preparedStatement, resultSet);
        }
        return res;
    }

    //getObject方法如果把?替换成字符串，会在这个子串两端加单引号
    //这可能会导致sql语句异常，所以此方法慎用
    private void setArgs(PreparedStatement preparedStatement, Object... args) throws SQLException {
        for (int i = 1; i <= args.length; i++) {
            preparedStatement.setObject(i, args[i - 1]);
        }
    }

    private ArrayList<T> resultSetToArrayList(ResultSet resultSet, Class<T> cls) {
        try {
            Constructor<T> declaredConstructor = cls.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
            }
            ArrayList<T> arrayList = new ArrayList<>();
            while(resultSet.next()) {
                T t = declaredConstructor.newInstance();
                for (int i = 0; i < fields.length; i++) {
                    fields[i].set(t, resultSet.getObject(i + 1));
                }
                arrayList.add(t);
            }
            return arrayList;
        } catch (ReflectiveOperationException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
