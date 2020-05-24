package com.szmy.noty.Utils;

import android.text.TextUtils;

import org.w3c.dom.Text;

public class StrUtils {
    /**
     *
     * @param origin 目标串
     * @param keyWord 模板串
     * @return ture，匹配成功，false，匹配失败
     */
    public static boolean kmp(String origin,String keyWord){

        return TextUtils.isEmpty(keyWord)||kmpMatch(origin,keyWord);
    }
    /**
     * 求出一个字符数组的next数组
     * @param t 字符数组
     * @return next数组
     */
    private static int[] getNextArray(char[] t) {
        int[] next = new int[t.length+1];
        next[0] = -1;
        next[1] = 0;
        int k;
        for (int j = 2; j < t.length; j++) {
            k=next[j-1];
            while (k!=-1) {
                if (t[j - 1] == t[k]) {
                    next[j] = k + 1;
                    break;
                }
                else {
                    k = next[k];
                }
                next[j] = 0;  //当k==-1而跳出循环时，next[j] = 0，否则next[j]会在break之前被赋值
            }
        }
        return next;
    }


    /**
     * 对主串s和模式串t进行KMP模式匹配
     * @param s 主串
     * @param t 模式串
     * @return 若匹配成功，返回t在s中的位置（第一个相同字符对应的位置），若匹配失败，返回-1
     */
    private static boolean kmpMatch(String s, String t){
        char[] s_arr = s.toCharArray();
        char[] t_arr = t.toCharArray();
        int[] next = getNextArray(t_arr);
        int i = 0, j = 0;
        while (i<s_arr.length && j<t_arr.length){
            if(j == -1 || s_arr[i]==t_arr[j]){
                i++;
                j++;
            }
            else
                j = next[j];
        }

        //-1;
        return j == t_arr.length;//i-j;
    }
}
