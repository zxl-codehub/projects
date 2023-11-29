package thread_;

//这个类用来管理线程，
//目前每个客户端只有一个线程，
//我们使用指针来指向该线程，便于使用
public class ManageThread {
    private static ClientConnectServerThread ccst;

    public static void addThread(ClientConnectServerThread ccst) {
        ManageThread.ccst = ccst;
    }

    public static ClientConnectServerThread getThread() {
        return ccst;
    }

    public static void removeThread() {
        ccst = null;
    }
}
