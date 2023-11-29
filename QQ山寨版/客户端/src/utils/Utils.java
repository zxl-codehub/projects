package utils;

import java.time.LocalTime;

public class Utils {
    public static String getNowTime() {
        String now = LocalTime.now().toString();
        return now.substring(0, now.indexOf('.'));
    }

    //modeString:模式串，给定的字符串
    //targetString:目标串，待查找的字符串
    public static int KMP(String modeString, String targetString) {
        if(modeString == null || targetString == null) {
            throw new NullPointerException();
        }
        if(modeString.isEmpty() || targetString.isEmpty()) {
            throw new DataStructEmptyException("字符串为空");
        }
        if(modeString.length() > targetString.length()) {
            return -1;
        }
        int[] nextVal = getNextValArray(modeString);
        int i = 0, j = 0;
        while(i < targetString.length() && j < modeString.length()) {
            if(targetString.charAt(i) == modeString.charAt(j)) {
                i++;
                j++;
            }else {
                j = nextVal[j];
                if(j == -1) {
                    j = 0;
                    i++;
                }
            }
        }
        //j不可能大于模式串的长度
        return j == modeString.length() ? i - j : -1;
    }

    //获取str的next数组
    private static int[] getNextArray(String str) {
        int[] next = new int[str.length()];
        next[0] = -1;
        int i = 0, j = -1;
        while(i < str.length() - 1) {//6
            if(j == -1 || str.charAt(i) == str.charAt(j)) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    //一般地，整体上，nextVal中的元素 < next中的元素
    //这可以让模式串整体向右移动的更远，加快比较速度
    //获取nextVal数组
    private static int[] getNextValArray(String str) {
        int[] arr = getNextArray(str);//arr经过下面的循环调整后就变成nextVal数组了
        for(int i = 1; i < arr.length; i++) {
            if(str.charAt(arr[i]) == str.charAt(i)) {
                arr[i] = arr[arr[i]];
            }
        }
        return arr;
    }
}
