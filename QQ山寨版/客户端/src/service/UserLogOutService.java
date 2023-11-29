package service;

import public_.Message;
import public_.MessageType;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//负责用户退出登录的服务
public class UserLogOutService {
    public static void logOut() {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        Message message = new Message();
        message.setMesType(MessageType.CLIENT_EXIT);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
