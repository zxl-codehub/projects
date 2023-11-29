package DB.dao;

import DB.javabean.MesBuf;

import java.util.ArrayList;

public class MesBufDAO extends Basic<MesBuf>{
    public void addMes(String sender, String getter, String content, String sendTime, boolean isGroup) {
        String sql = "insert into mes_buf values(?, ?, ?, ?, ?)";
        super.dml(sql, sender, getter, content, sendTime, isGroup);
    }

    private ArrayList<MesBuf> getAllMes() {
        String sql = "select * from mes_buf";
        return super.query(sql, MesBuf.class);
    }

    //删除接收方的私信
    public void delMes(String getter) {
        String sql = "delete from mes_buf where getter = ?";
        super.dml(sql, getter);
    }

    //获取UserId离线时收到的消息
    public String getLogoutMes(String userId) {
        ArrayList<MesBuf> allMes = getAllMes();
        String res = "";
        String letter = "";
        for(MesBuf mesBuf : allMes) {
            if (mesBuf.getGetter().equals(userId)) {
                if(mesBuf.isGroupMes()) {
                    letter = mesBuf.getSendTime() + " " + mesBuf.getSender() + "对大家说：" + mesBuf.getContent();
                }else{
                    letter = mesBuf.getSendTime() + " " + mesBuf.getSender() + "对你说：" + mesBuf.getContent();
                }
                res += letter + "__✉✉✉__";
            }
        }
        return res;
    }

    public boolean isEmpty() {
        String sql = "select count(*) from mes_buf";
        long num = (long)super.querySingleObject(sql);
        return num == 0;
    }
}
