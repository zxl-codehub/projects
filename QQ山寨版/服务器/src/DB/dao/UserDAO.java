package DB.dao;

import public_.User;

import java.util.ArrayList;
import java.util.Iterator;

public class UserDAO extends Basic<User>{
    //添加用户
    public boolean addUser(String userId, String passwd) {
        String sql = "insert into user values(?, md5(?))";
        try {
            super.dml(sql, userId, passwd);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    //删除用户
    public boolean delUser(String userId) {
        String sql = "delete from user where userId = ?";
        try {
            super.dml(sql, userId);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    //查找用户是否存在，如果存在，返回该用户
    //不存在返回null
    public User seek(String userId) {
        String sql = "select * from user where userId = ?";
        try{
            return super.querySingleRecord(sql, User.class, userId);
        }catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    //把获得passwd加密后的字符串
    public String md5(String passwd) {
        String sql = "select md5(?) from dual";
        return (String) super.querySingleObject(sql, passwd);
    }

    public String[] getAllUserId() {
        String sql = "select * from user";
        ArrayList<User> arrayList = super.query(sql, User.class);
        String[] arr = new String[arrayList.size()];
        Iterator<User> iterator = arrayList.iterator();
        int i = 0;
        while(iterator.hasNext()) {
            arr[i++] = iterator.next().getUserId();
        }
        return arr;
    }
}
