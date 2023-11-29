package thread_;

import public_.FileMessage;
import public_.Message;
import public_.MessageType;
import view.View;

import java.io.*;
import java.net.Socket;

//该线程位于客户端，用来和服务器保持通讯
public class ClientConnectServerThread extends Thread {
    private Socket socket;
    private String useId;
    private static String path = "D:\\";

    public static void setPath(String path) {
        ClientConnectServerThread.path = path;
    }

    public ClientConnectServerThread(Socket socket, String useId) {
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
        try {
            Tag:
            while (true) {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message mes = (Message) ois.readObject();
                switch (mes.getMesType()) {
                    case MessageType.RETURN_ONLINE_USER:
                        String[] onlineUsers = mes.getContent().split(" ");
                        if (onlineUsers.length == 1) {
                            System.out.print("当前只有你自己在线");
                        } else {
                            System.out.print("除本人外，当前在线用户：");
                            for (String onlineUser : onlineUsers) {
                                if (!onlineUser.equals(useId)) {
                                    System.out.print(onlineUser + " ");
                                }
                            }
                        }
                        System.out.println();
                        break;
                    case MessageType.CLIENT_EXIT:
                        ManageThread.removeThread();
                        socket.close();
                        break Tag;
                    case MessageType.CHAT_MESSAGE:
                        System.out.println(mes.getSendTime() + " " + mes.getSender() + "对你说：" + mes.getContent());
                        break;
                    case MessageType.USER_NOT_EXIST:
                        System.out.println("该用户不存在");
                        View.setLoop(false);
                        break;
                    case MessageType.RETURN_LOGOUT_MESSAGE:
                        if (mes.getContent() != null && !mes.getContent().equals("")) {
                            String[] mesBufs = mes.getContent().split("__✉✉✉__");
                            for (String mesBuf : mesBufs) {
                                System.out.println(mesBuf);
                            }
                        }
                        break;
                    case MessageType.GROUP_CHAT_MESSAGE:
                        System.out.println(mes.getSendTime() + " " + mes.getSender() + "对大家说：" + mes.getContent());
                        break;
                    case MessageType.RETURN_FILE_NAME:
                        String[] mesBufs = mes.getContent().split("__✉✉✉__");
                        int fileNum = Integer.parseInt(mesBufs[mesBufs.length - 1]);
                        View.setFileNum(fileNum);
                        if (fileNum == 0) {
                            System.out.println("你没有收到任何文件");
                        } else {
                            for (int i = 0; i < mesBufs.length - 1; i++) {
                                System.out.println(mesBufs[i]);
                            }
                        }
                        break;
                    case MessageType.RETURN_FILE:
                        FileMessage fileMessage = (FileMessage) mes;
                        File file = new File(path + fileMessage.getFileName());
                        file.createNewFile();
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        fileOutputStream.write(fileMessage.getFileContent());
                        fileOutputStream.close();
                        System.out.println(fileMessage.getFileName() + " 已保存在目录" + path + "下");
                        break;
                    case MessageType.SERVICE_SHUTDOWN:
                        ManageThread.getThread().getSocket().close();
                        ManageThread.removeThread();
                        System.out.println("\n服务器已关闭");
                        System.exit(0);
                        break;
                    case MessageType.SERVICE_SEND_MESSAGE:
                        System.out.println("\n服务器向大家说：" + mes.getContent());
                        break;
                    case MessageType.LOGOUT:
                        ManageThread.getThread().getSocket().close();
                        ManageThread.removeThread();
                        System.out.println("\n你已被服务器强制下线");
                        System.exit(0);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
