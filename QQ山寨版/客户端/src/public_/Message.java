package public_;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender; //发送者
    private String getter;//接收者
    private String sendTime;//发送时间
    private String content;//内容
    private int mesType;//消息类型

    public Message() { }

    public Message(String sender, String getter, String sendTime, String content, int mesType) {
        this.sender = sender;
        this.getter = getter;
        this.sendTime = sendTime;
        this.content = content;
        this.mesType = mesType;
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

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMesType() {
        return mesType;
    }

    public void setMesType(int mesType) {
        this.mesType = mesType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", content='" + content + '\'' +
                ", mesType=" + mesType +
                '}';
    }
}
