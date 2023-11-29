package thread_;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//这个类用来管理服务器的线程
//使用HashMap集合来管理线程
public class ManageThread {
    private static final HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    public static void addThread(String useId, ServerConnectClientThread scct) {
        hm.put(useId,scct);
    }

    public static ServerConnectClientThread getThread(String useId) {
        return hm.get(useId);
    }

    public static void removeThread(String useId) {
        hm.remove(useId);
    }

    public static String getOnlineUserId() {
        String res = "";
        Set<String> set = hm.keySet();
        Iterator<String> iter = set.iterator();
        while(iter.hasNext()) {
            res += iter.next() + " ";
        }
        return res;
    }

    public static boolean isOnline(String userId) {
        String[] onlineUsers = getOnlineUserId().split(" ");
        for(String onlineUser : onlineUsers) {
            if(onlineUser.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean notAnyoneOnline() {
        return hm.isEmpty();
    }
}
