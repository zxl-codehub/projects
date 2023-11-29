package DB.javabean;

public class MesBuf {
    private String sender;
    private String getter;
    private String content;
    private String sendTime;
    private boolean isGroupMes;

    public MesBuf() { }

    public MesBuf(String sender, String getter, String content, String sendTime, boolean isGroupMes) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.sendTime = sendTime;
        this.isGroupMes = isGroupMes;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isGroupMes() {
        return isGroupMes;
    }

    public void setGroupMes(boolean isGroupMes) {
        this.isGroupMes = isGroupMes;
    }

    @Override
    public String toString() {
        return "MesBuf{" +
                "sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", content='" + content + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", isGroupMes=" + isGroupMes +
                '}';
    }
}
