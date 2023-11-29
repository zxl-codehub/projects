package DB.dao;

import DB.javabean.FileBuf;
import public_.FileMessage;
import public_.MessageType;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class FileBufDAO extends Basic<FileBuf> {
    private FileMessage fileMessage;
    private final String fileBufPath = "C:\\Users\\25238\\Desktop\\文件夹\\JavaProject\\QQ山寨版\\file_buf\\";

    public FileBufDAO() { }

    public FileBufDAO(FileMessage fileMessage) {
        this.fileMessage = fileMessage;
    }

    public void addFile(boolean isGroupFile) {
        File[] files = new File(fileBufPath).listFiles();
        int[] arr = new int[files.length];
        for (int i = 0; i < files.length; i++) {
            arr[i] = Integer.parseInt(files[i].getName());
        }
        int index = checkArrContinuous(arr);
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(fileBufPath + index);
            file.createNewFile();
            byte[] buf = fileMessage.getFileContent();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(buf);
            String sql = "insert into file_buf values(?, ?, ?, ?, ?, ?)";
            super.dml(sql, fileMessage.getSender(), fileMessage.getGetter(), fileMessage.getFileName(), index, fileMessage.getSendTime(), isGroupFile);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(String useId) {
        String sql = "select * from file_buf where getter = ?";
        ArrayList<FileBuf> arrayList = super.query(sql, FileBuf.class, useId);
        String res = "";
        int i = 1;
        Iterator<FileBuf> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            FileBuf fileBuf = iterator.next();
            String str = fileBuf.isGroupFile() ? "大家" : "你";
            res += i++ + ") " + fileBuf.getSendTime() + " " + fileBuf.getSender() + "向"+str+"发送了文件：" + fileBuf.getFileName() + "__✉✉✉__";
        }
        res += arrayList.size();
        return res;
    }

    public FileMessage getFile(String useId, int key) {
        String sql = "select * from file_buf where getter = ?";
        ArrayList<FileBuf> arrayList = super.query(sql, FileBuf.class, useId);
        FileBuf fileBuf = arrayList.get(key - 1);
        String fileName = fileBuf.getFileName();
        int fileId = fileBuf.getFileId();
        FileInputStream fileInputStream = null;
        try {
            File file = new File(fileBufPath + fileId);
            fileInputStream = new FileInputStream(file);
            byte[] arr = new byte[(int)file.length()];
            fileInputStream.read(arr);
            FileMessage fileMessage = new FileMessage();
            fileMessage.setFileContent(arr);
            fileMessage.setFileName(fileName);
            fileMessage.setMesType(MessageType.RETURN_FILE);
            return fileMessage;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void delFile(String useId, int key) {
        String sql = "select * from file_buf where getter = ?";
        ArrayList<FileBuf> arrayList = super.query(sql, FileBuf.class, useId);
        FileBuf fileBuf = arrayList.get(key - 1);
        int fileId = fileBuf.getFileId();
        sql = "delete from file_buf where fileId = ?";
        super.dml(sql, fileId);
        new File(fileBufPath + fileId).delete();
    }

    public int checkArrContinuous(int[] arr) {
        if(arr == null) throw new NullPointerException("arr为空");
        if(arr.length == 0) return 0;
        int i = 0;
        while(i < arr.length - 1) {
            if(arr[i + 1] - arr[i] > 1) {
                return arr[i] + 1;
            }
            i++;
        }
        return arr[i] + 1;
    }
}
