package server;

import DB.dao.UserDAO;
import public_.Message;
import public_.MessageType;
import public_.User;
import thread_.ManageThread;
import thread_.ServerConnectClientThread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

//这是服务器的主线程
public class QQServer extends Thread{
    private static ServerSocket serverSocket;

    {
        try {
            //让serverSocket与本机的9999端口关联起来
            serverSocket = new ServerSocket(9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ServerSocket getServerSocket() {
        return serverSocket;
    }

    @Override
    public void run() {
        //执行代码块
        Socket socket;
        try {
            while (true) {
                try{
                    //服务器在9999端口监听，如果有连接，就获取socket对象
                    //没有连接，就阻塞在这里
                    socket = serverSocket.accept();
                }catch (SocketException e) {
                    break;
                }
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message mes = (Message) ois.readObject();
                if(mes.getMesType() == MessageType.SIGN_IN) {
                    User u = (User)ois.readObject();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Message message = new Message();
                    Boolean b = checkUser(u);
                    if(b == null) {
                        message.setMesType(MessageType.ACCOUNT_REMOTE_SIGN_IN);
                        oos.writeObject(message);
                        socket.close();
                    }else if(b) {
                        message.setMesType(MessageType.SIGN_IN_SUCCESS);
                        oos.writeObject(message);
                        ServerConnectClientThread scct = new ServerConnectClientThread(u.getUserId(), socket);
                        ManageThread.addThread(u.getUserId(), scct);
                        scct.start();
                    }else {
                        message.setMesType(MessageType.SIGN_IN_FAIL);
                        oos.writeObject(message);
                        socket.close();
                    }
                }else if(mes.getMesType() == MessageType.SIGN_UP){
                    ObjectOutputStream oos = null;
                    try{
                        User user = (User)ois.readObject();
                        UserDAO userDAO = new UserDAO();
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        mes = new Message();
                        if(userDAO.addUser(user.getUserId(), user.getPasswd())) {
                            mes.setMesType(MessageType.SIGN_UP_SUCCESS);
                            oos.writeObject(mes);
                        }else {
                            mes.setMesType(MessageType.SIGN_UP_FAIL);
                            oos.writeObject(mes);
                        }
                    }finally {
                        socket.close();
                        ois.close();
                        oos.close();
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //去数据库中查看是否存在该用户，以及账号密码是否合法
    private Boolean checkUser(User user) {
        UserDAO userDAO = new UserDAO();
        User u = userDAO.seek(user.getUserId());
        String[] userIds = ManageThread.getOnlineUserId().split(" ");
        for (String userId : userIds) {
            if (user.getUserId().equals(userId)) {
                return null;
            }
        }
        return u != null && u.getPasswd().equals(userDAO.md5(user.getPasswd()));
    }
}
