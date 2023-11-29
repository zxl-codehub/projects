package service;

import public_.Message;
import public_.MessageType;
import public_.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.Utils.KMP;

//负责用户注册的服务
public class SignUpService {
    public static void checkUidAndPwd(String userId, String pwd1, String pwd2) {
        if(userId == null || pwd1 == null || pwd2 == null) {
            System.out.println("用户名和密码不能为空");
            return;
        }
        if(!pwd1.equals(pwd2)) {
            System.out.println("两次输入的密码不一致");
            return;
        }
        if(userId.length() > 12 || pwd1.length() > 20) {
            System.out.println("用户名不超过12个字符");
            System.out.println("密码不能超过20个字符");
            return;
        }
        if(KMP("__✉✉✉__", userId) != -1) {
            System.out.println("用户名中含有非法字符串");
            return;
        }

        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            //连接到该ip的9999端口
            socket = new Socket("192.168.10.20", 9999);
            oos = new ObjectOutputStream(socket.getOutputStream());
            Message mes = new Message();
            mes.setMesType(MessageType.SIGN_UP);
            oos.writeObject(mes);
            User user = new User(userId, pwd1);
            oos.writeObject(user);
            ois = new ObjectInputStream(socket.getInputStream());
            mes = (Message)ois.readObject();
            if(mes.getMesType() == MessageType.SIGN_UP_SUCCESS) {
                System.out.println("注册成功，你的用户名是"+userId+"，密码是" + pwd1);
            }else {
                System.out.println("注册失败，用户"+userId+"已存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                socket.close();
                oos.close();
                ois.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
