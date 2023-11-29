package service;

import public_.FileMessage;
import public_.Message;
import public_.MessageType;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static utils.Utils.getNowTime;

public class FileService {
    public static void sendFile(String sender, String getter, String filePath) {
        File file = new File(filePath);
        if(!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if(file.isDirectory()) {
            System.out.println("无法发送文件夹");
            return;
        }
        if(file.length() > (int)Math.pow(1024, 3)) {
            System.out.println("无法发送超过1GB的文件");
            return;
        }

        byte[] arr = new byte[(int)file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileMessage fileMessage = new FileMessage();
        fileMessage.setFileContent(arr);
        fileMessage.setMesType(MessageType.SEND_FILE_MESSAGE);
        fileMessage.setSender(sender);
        fileMessage.setGetter(getter);
        fileMessage.setSendTime(getNowTime());
        fileMessage.setFileName(file.getName());

        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(fileMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getName() + " 发送成功~");
    }

    public static void getFileName(String getter) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setMesType(MessageType.GET_FILE_NAME);
            message.setGetter(getter);
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getFile(String getter, int key) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setMesType(MessageType.GET_FILE);
            message.setGetter(getter);
            message.setContent(String.valueOf(key));
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delFile(String getter, int key) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message message = new Message();
            message.setMesType(MessageType.DELETE_FILE);
            message.setGetter(getter);
            message.setContent(String.valueOf(key));
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendGroupFile(String sender, String filePath) {
        File file = new File(filePath);
        if(!file.exists()) {
            System.out.println("文件不存在");
            return;
        }
        if(file.isDirectory()) {
            System.out.println("无法发送文件夹");
            return;
        }
        if(file.length() > (int)Math.pow(1024, 3)) {
            System.out.println("无法发送超过1GB的文件");
            return;
        }

        byte[] arr = new byte[(int)file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(arr);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileMessage fileMessage = new FileMessage();
        fileMessage.setFileContent(arr);
        fileMessage.setMesType(MessageType.SEND_GROUP_FILE);
        fileMessage.setSender(sender);
        fileMessage.setSendTime(getNowTime());
        fileMessage.setFileName(file.getName());

        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(fileMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(file.getName() + " 群发成功~");
    }
}
