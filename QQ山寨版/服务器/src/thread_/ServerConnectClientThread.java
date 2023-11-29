package thread_;

import DB.dao.FileBufDAO;
import DB.dao.MesBufDAO;
import DB.dao.UserDAO;
import public_.FileMessage;
import public_.Message;
import public_.MessageType;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

//与服务端进行交互的线程
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String useId;

    public ServerConnectClientThread(String useId, Socket socket) {
        this.socket = socket;
        this.useId = useId;
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUseId() {
        return useId;
    }

    @Override
    public void run() {
        ObjectInputStream ois;
        try {
            Tag:
            while (true) {
                try {
                    ois = new ObjectInputStream(socket.getInputStream());
                }catch (SocketException e) {
                    break;
                }
                Message mes = (Message) ois.readObject();
                switch (mes.getMesType()) {
                    case MessageType.GET_ONLINE_USER:
                        mes = new Message();
                        mes.setMesType(MessageType.RETURN_ONLINE_USER);
                        String onlineUsers = ManageThread.getOnlineUserId();
                        mes.setContent(onlineUsers);
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(mes);
                        break;
                    case MessageType.CLIENT_EXIT:
                        mes = new Message();
                        mes.setMesType(MessageType.CLIENT_EXIT);
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(mes);
                        ManageThread.removeThread(useId);
                        socket.close();
                        break Tag;
                    case MessageType.CHAT_MESSAGE:
                        if (ManageThread.isOnline(mes.getGetter())) {
                            ServerConnectClientThread scct = ManageThread.getThread(mes.getGetter());
                            Socket socket = scct.getSocket();
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(mes);
                        } else {
                            MesBufDAO mesBufDAO = new MesBufDAO();
                            mesBufDAO.addMes(mes.getSender(), mes.getGetter(), mes.getContent(), mes.getSendTime(), false);
                        }
                        break;
                    case MessageType.CHECK_USER_IS_EXIST:
                        UserDAO userDAO = new UserDAO();
                        if (userDAO.seek(mes.getGetter()) == null) {
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            mes = new Message();
                            mes.setMesType(MessageType.USER_NOT_EXIST);
                            oos.writeObject(mes);
                        }
                        break;
                    case MessageType.GET_LOGOUT_MESSAGE:
                        MesBufDAO mesBufDAO = new MesBufDAO();
                        String sender = mes.getSender();
                        mes = new Message();
                        mes.setContent(mesBufDAO.getLogoutMes(sender));
                        mes.setMesType(MessageType.RETURN_LOGOUT_MESSAGE);
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(mes);
                        mesBufDAO.delMes(sender);
                        break;
                    case MessageType.GROUP_CHAT_MESSAGE:
                        userDAO = new UserDAO();
                        mesBufDAO = new MesBufDAO();
                        String[] allUserId = userDAO.getAllUserId();
                        String[] onlineUserId = ManageThread.getOnlineUserId().split(" ");
                        String[] logoutUserId = getLogoutUserId(allUserId, onlineUserId);
                        for (int i = 0; i < onlineUserId.length; i++) {
                            if (!onlineUserId[i].equals(useId)) {
                                ServerConnectClientThread scct = ManageThread.getThread(onlineUserId[i]);
                                Socket socket = scct.getSocket();
                                oos = new ObjectOutputStream(socket.getOutputStream());
                                oos.writeObject(mes);
                            }
                        }
                        for (int i = 0; i < logoutUserId.length; i++) {
                            mesBufDAO.addMes(mes.getSender(), logoutUserId[i], mes.getContent(), mes.getSendTime(), true);
                        }
                        break;
                    case MessageType.SEND_FILE_MESSAGE:
                        FileMessage fileMes = (FileMessage) mes;
                        FileBufDAO fileBufDAO = new FileBufDAO(fileMes);
                        fileBufDAO.addFile(false);
                        break;
                    case MessageType.GET_FILE_NAME:
                        fileBufDAO = new FileBufDAO();
                        String fileName = fileBufDAO.getFileName(mes.getGetter());
                        mes = new Message();
                        mes.setMesType(MessageType.RETURN_FILE_NAME);
                        mes.setContent(fileName);
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(mes);
                        break;
                    case MessageType.GET_FILE:
                        fileBufDAO = new FileBufDAO();
                        FileMessage fileMessage =
                                fileBufDAO.getFile(mes.getGetter(), Integer.parseInt(mes.getContent()));
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(fileMessage);
                        fileBufDAO.delFile(mes.getGetter(), Integer.parseInt(mes.getContent()));
                        break;
                    case MessageType.DELETE_FILE:
                        fileBufDAO = new FileBufDAO();
                        fileBufDAO.delFile(mes.getGetter(), Integer.parseInt(mes.getContent()));
                        break;
                    case MessageType.SEND_GROUP_FILE:
                        allUserId = new UserDAO().getAllUserId();
                        for (String userId : allUserId) {
                            if(!userId.equals(mes.getSender())) {
                                fileBufDAO = new FileBufDAO(new FileMessage((FileMessage)mes, userId));
                                fileBufDAO.addFile(true);
                            }
                        }
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean inArray(String str, String[] arr) {
        boolean b = false;
        for (int i = 0; i < arr.length; i++) {
            if (str.equals(arr[i])) {
                b = true;
                break;
            }
        }
        return b;
    }

    private String[] getLogoutUserId(String[] allUserId, String[] onlineUserid) {
        String[] tempArr = new String[allUserId.length];
        for (int i = 0; i < allUserId.length; i++) {
            tempArr[i] = allUserId[i];
        }
        for (int i = 0; i < tempArr.length; i++) {
            if (inArray(tempArr[i], onlineUserid)) {
                tempArr[i] = null;
            }
        }
        String[] notOnlineUserid = new String[allUserId.length - onlineUserid.length];
        for (int i = 0, j = 0; i < tempArr.length; i++) {
            if (tempArr[i] != null) {
                notOnlineUserid[j] = tempArr[i];
                j++;
            }
        }
        return notOnlineUserid;
    }
}
