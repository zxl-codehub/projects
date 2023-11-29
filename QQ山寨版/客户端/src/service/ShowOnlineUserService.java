package service;

import public_.Message;
import public_.MessageType;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

//用于显示在线用户的服务
public class ShowOnlineUserService {
    public static void display() {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message mes = new Message();
            mes.setMesType(MessageType.GET_ONLINE_USER);
            oos.writeObject(mes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
