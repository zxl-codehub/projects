package server;

import DB.dao.UserDAO;
import public_.Message;
import public_.MessageType;
import thread_.ManageThread;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class View {
    private final Scanner in = new Scanner(System.in);
    private boolean isOpen;

    public void showView() {
        Outer:
        while(true) {
            System.out.println("=======服务器菜单(当前状态:"+(isOpen ? "开启" : "关闭")+")========");
            System.out.println("\t1)启动服务器");
            System.out.println("\t2)关闭服务器");
            System.out.println("\t3)获取在线用户列表");
            System.out.println("\t4)推送消息");
            System.out.println("\t5)踢人");
            System.out.println("\t9)退出菜单");
            System.out.print("请输入你的选择：");
            char key = in.next().charAt(0);
            switch (key) {
                case '1':
                    if(isOpen) {
                        System.out.println("服务器已经启动了");
                    }else{
                        new QQServer().start();
                        isOpen = true;
                        System.out.println("服务器启动成功~");
                    }
                    break;
                case '2':
                    if(isOpen) {
                        shutdown();
                        isOpen = false;
                        System.out.println("服务器成功关闭~");
                    }else {
                        System.out.println("服务器已经关闭了");
                    }
                    break;
                case '3':
                    if(!isOpen) {
                        System.out.println("服务器未启动");
                        break;
                    }
                    showOnlineUserList();
                    break;
                case '4':
                    if(!isOpen) {
                        System.out.println("服务器未启动");
                        break;
                    }
                    System.out.print("请输入推送消息：");
                    String mes = in.next();
                    sendMessageToOnlineUser(mes);
                    System.out.println("消息已发送");
                    break;
                case '5':
                    if(!isOpen) {
                        System.out.println("服务器未启动");
                        break;
                    }
                    System.out.print("请输入要踢除的用户名：");
                    String userId = in.next();
                    logout(userId);
                    System.out.println(userId + " 已下线");
                    break;
                case '9':
                    if(isOpen) shutdown();
                    System.out.println("Bye~");
                    break Outer;
            }
        }
    }

    private void shutdown() {
        try {
            QQServer.getServerSocket().close();
            if(ManageThread.notAnyoneOnline()) return;
            String[] onlineUserId = ManageThread.getOnlineUserId().split(" ");
            for (String user : onlineUserId) {
                Socket socket = ManageThread.getThread(user).getSocket();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                message.setMesType(MessageType.SERVICE_SHUTDOWN);
                oos.writeObject(message);

                ManageThread.removeThread(user);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showOnlineUserList() {
        if(ManageThread.notAnyoneOnline()) {
            System.out.println("当前没有任何用户在线");
            return;
        }
        String[] onlineUsers = ManageThread.getOnlineUserId().split(" ");
        int i = 1;
        System.out.println("当前在线用户：");
        for (String onlineUser : onlineUsers) {
            System.out.println("\t" + i++ + ") " + onlineUser);
        }
    }

    private void sendMessageToOnlineUser(String mes) {
        try {
            String[] onlineUsers = ManageThread.getOnlineUserId().split(" ");
            for (String onlineUser : onlineUsers) {
                Socket socket = ManageThread.getThread(onlineUser).getSocket();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                message.setMesType(MessageType.SERVICE_SEND_MESSAGE);
                message.setContent(mes);
                oos.writeObject(message);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout(String userId) {
        if(!checkUserId(userId)) return;
        Message message = new Message();
        message.setMesType(MessageType.LOGOUT);
        Socket socket = ManageThread.getThread(userId).getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
            socket.close();
            ManageThread.removeThread(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkUserId(String userId) {
        UserDAO userDAO = new UserDAO();
        String[] allUserId = userDAO.getAllUserId();
        boolean b = true;
        for (String uid : allUserId) {
            if(userId.equals(uid)) {
                b = false;
                break;
            }
        }
        if(b){
            System.out.println(userId + " 不存在");
            return false;
        }
        b = true;
        String[] onlineUserId = ManageThread.getOnlineUserId().split(" ");
        for (String uid : onlineUserId) {
            if(userId.equals(uid)) {
                b = false;
                break;
            }
        }
        if(b) {
            System.out.println(userId + " 未登录");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        new View().showView();
    }
}
