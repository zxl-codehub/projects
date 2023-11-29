package service;

import public_.Message;
import public_.MessageType;
import public_.User;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

//负责用户登录的服务
public class SignInService {
    public static Boolean checkUidAndPwd(String userId, String passwd) {
        try {
            //连接到该ip地址的9999端口
            Socket socket = new Socket("192.168.10.20", 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setMesType(MessageType.SIGN_IN);
            oos.writeObject(message);
            oos.writeObject(new User(userId, passwd));
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            message = (Message)ois.readObject();
            if(message.getMesType() == MessageType.SIGN_IN_SUCCESS) {
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket, userId);
                ManageThread.addThread(ccst);
                ccst.start();
                return true;
            }else if(message.getMesType() == MessageType.ACCOUNT_REMOTE_SIGN_IN) {
                socket.close();
                return null;
            } else {
                socket.close();
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
