package view;

import public_.Message;
import public_.MessageType;
import service.*;
import thread_.ClientConnectServerThread;
import thread_.ManageThread;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    private static final Scanner in = new Scanner(System.in);
    private static boolean loop;
    private static int fileNum;

    public static void setLoop(boolean loop) {
        View.loop = loop;
    }

    public static void setFileNum(int fileNum) {
        View.fileNum = fileNum;
    }

    public void checkUserISExist(String useId) {
        ClientConnectServerThread ccst = ManageThread.getThread();
        Socket socket = ccst.getSocket();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Message mes = new Message();
            mes.setGetter(useId);
            mes.setMesType(MessageType.CHECK_USER_IS_EXIST);
            oos.writeObject(mes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPath(String path) {
        char driveLetter = path.charAt(0);
        File[] files = File.listRoots();
        boolean b = false;
        for (File file : files) {
            if(file.toString().charAt(0) == driveLetter) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void showView() {
        Outer:
        while(true) {
            System.out.println("=========多用户通讯系统==========");
            System.out.println("\t1.用户登录");
            System.out.println("\t2.注册账号");
            System.out.println("\t9.退出系统");
            System.out.print("请输入你的选择：");
            char c = in.next().charAt(0);
            switch (c) {
                case '1':
                    System.out.print("请输入你的用户名(uid)：");
                    String userId = in.next();
                    System.out.print("请 输 入 你 的 密  码：");
                    String passwd = in.next();
                    Boolean b = SignInService.checkUidAndPwd(userId, passwd);
                    if (b == null) {
                        System.out.println("登录失败，账号已在异地登录");
                    } else if(b) {
                        System.out.println("登录成功");
                        GetLogoutLetterService.getLetter(userId);
                        Inner:
                        while (true) {
                            System.out.println("======二级菜单(当前用户:" + userId + ")======");
                            System.out.println("\t1.显示在线用户列表");
                            System.out.println("\t2.群发消息");
                            System.out.println("\t3.私发消息");
                            System.out.println("\t4.收发文件");
                            System.out.println("\t9.退出登录");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.print("请输入你的选择：");
                            c = in.next().charAt(0);
                            switch (c) {
                                case '1':
                                    ShowOnlineUserService.display();
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    break;
                                case '2':
                                    System.out.println("你正在群发消息");
                                    System.out.println("输入 /exit 可退出群发");
                                    while(true) {
                                        String content = in.next();
                                        if("/exit".equals(content)) break;
                                        ChatService.chatToGroup(content, userId);
                                    }
                                    break;
                                case '3':
                                    loop = true;
                                    System.out.print("请输入和谁聊天：");
                                    String getter = in.next();
                                    checkUserISExist(getter);
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    if(loop) {
                                        System.out.println("你正在和"+getter+"聊天");
                                        System.out.println("输入 /exit 可退出聊天");
                                        while(true) {
                                            String content = in.next();
                                            if("/exit".equals(content)) break;
                                            ChatService.chatToOne(userId, getter, content);
                                        }
                                    }
                                    break;
                                case '4':
                                    InInner:
                                    while(true) {
                                        loop = true;
                                        System.out.println("\n========文件收发模块========");
                                        System.out.println("\t1.发送文件");
                                        System.out.println("\t2.群发文件");
                                        System.out.println("\t3.接收到的文件");
                                        System.out.println("\t4.更改文件保存路径");
                                        System.out.println("\t9.回到二级菜单");
                                        System.out.print("请输入你的选择：");
                                        c = in.next().charAt(0);
                                        switch (c) {
                                            case '1':
                                                System.out.print("请输入给谁发文件：");
                                                getter = in.next();
                                                checkUserISExist(getter);
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                if(!loop) break;
                                                System.out.print("请输入文件路径：");
                                                String filePath = in.next();
                                                FileService.sendFile(userId, getter, filePath);
                                                break;
                                            case '2':
                                                System.out.print("请输入文件路径：");
                                                filePath = in.next();
                                                FileService.sendGroupFile(userId, filePath);
                                                break;
                                            case '3':
                                                FileService.getFileName(userId);
                                                try {
                                                    Thread.sleep(500);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                                if(fileNum == 0) break;
                                                System.out.println(fileNum + 1 + ") 回退");
                                                System.out.print("请输入你的选择：");
                                                int key;
                                                try {
                                                    key = in.nextInt();
                                                }catch (InputMismatchException e) {
                                                    break;
                                                }
                                                if(key <= 0 || key > fileNum + 1) break;
                                                if(key == fileNum + 1) break;
                                                System.out.println("1) 接收");
                                                System.out.println("2) 删除");
                                                System.out.print("请输入你的选择：");
                                                c = in.next().charAt(0);
                                                if(c == '1') {
                                                    FileService.getFile(userId, key);
                                                    try {
                                                        Thread.sleep(500);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }else if(c == '2') {
                                                    FileService.delFile(userId, key);
                                                    System.out.println("文件已删除");
                                                }
                                                break;
                                            case '4':
                                                System.out.print("输入文件存放路径：");
                                                String path = in.next();
                                                if(checkPath(path)) {
                                                    ClientConnectServerThread.setPath(path);
                                                    System.out.println("路径设置成功");
                                                }else {
                                                    System.out.println("该路径不存在");
                                                }
                                                break;
                                            case '9':
                                                break InInner;
                                        }
                                    }
                                    break;
                                case '9':
                                    UserLogOutService.logOut();
                                    System.out.println("你已下线");
                                    break Inner;
                            }
                        }
                    }else {
                        System.out.println("登录失败，用户名或密码错误");
                    }
                    break;
                case '2':
                    System.out.print("请输入新用户名：");
                    String newUserId = in.next();
                    System.out.print("请 输 入 密 码：");
                    String pwd1 = in.next();
                    System.out.print("请 确 认 密 码：");
                    String pwd2 = in.next();
                    SignUpService.checkUidAndPwd(newUserId, pwd1, pwd2);
                    break;
                case '9':
                    System.out.println("Bye~");
                    break Outer;
            }
        }
    }

    public static void main(String[] args) {
        new View().showView();
    }
}
