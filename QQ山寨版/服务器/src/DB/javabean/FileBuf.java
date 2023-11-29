package DB.javabean;

public class FileBuf {
    private String sender;
    private String getter;
    private String fileName;
    private int fileId;
    private String sendTime;
    private boolean isGroupFile;

    public FileBuf() { }

    public FileBuf(String sender, String getter, String fileName, int fileId, String sendTime, boolean isGroupFile) {
        this.sender = sender;
        this.getter = getter;
        this.fileName = fileName;
        this.fileId = fileId;
        this.sendTime = sendTime;
        this.isGroupFile = isGroupFile;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public boolean isGroupFile() {
        return isGroupFile;
    }

    public void setGroupFile(boolean groupFile) {
        isGroupFile = groupFile;
    }

    @Override
    public String toString() {
        return "MesBuf{" +
                "sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileId='" + fileId + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", isGroupFile=" + isGroupFile +
                '}';
    }
}
