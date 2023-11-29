package public_;

import java.io.Serializable;

public class User implements Serializable {
    private String userId;//用户名
    private String passwd;//密码

    public User() { }

    public User(String userId, String passwd) {
        this.userId = userId;
        this.passwd = passwd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", passwd='" + passwd + '\'' +
                '}';
    }
}
