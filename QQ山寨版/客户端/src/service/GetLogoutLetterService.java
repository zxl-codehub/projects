package service;

import public_.Message;
import public_.MessageType;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//用户登录后启动该服务，获取用户离线时收到的信息
public class GetLogoutLetterService {
    public static void getLetter(String userId) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setMesType(MessageType.GET_LOGOUT_MESSAGE);
            message.setSender(userId);
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
