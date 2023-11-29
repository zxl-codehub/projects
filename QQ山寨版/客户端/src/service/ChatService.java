package service;

import public_.Message;
import public_.MessageType;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.Utils.getNowTime;

public class ChatService {
    public static void chatToGroup(String content, String sender) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setMesType(MessageType.GROUP_CHAT_MESSAGE);
            message.setContent(content);
            message.setSendTime(getNowTime());
            message.setSender(sender);
            oos.writeObject(message);
            System.out.println(message.getSendTime() + " 你对大家说：" + message.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void chatToOne(String sender, String getter, String content) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        Message mes = new Message(sender, getter, getNowTime(), content, MessageType.CHAT_MESSAGE);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(mes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
