package public_;

import java.util.Arrays;

public class FileMessage extends Message{
    private byte[] fileContent;
    private String fileName;

    public FileMessage() { }

    public FileMessage(String sender, String getter, String sendTime, String fileName, int mesType, byte[] fileContent) {
        super(sender, getter, sendTime, null, mesType);
        this.fileContent = fileContent;
        this.fileName = fileName;
    }

    public FileMessage(FileMessage oldFileMessage, String getter) {
        this(oldFileMessage.getSender(), getter, oldFileMessage.getSendTime(), oldFileMessage.getFileName(), oldFileMessage.getMesType(), oldFileMessage.getFileContent());
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String toString() {
        return "FileMessage{" +
                super.toString() + '\'' +
                "fileContent=" + Arrays.toString(fileContent) +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
