package com.hongkun.until;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

public class Base64Util {

    /**
     * 将二进制转换成Base64字符串
     *
     * @param buf
     * @return
     */
    public static String toBase64Str(String buf) {
        return new Base64().encodeAsString(buf.getBytes());
    }

    /**
     * 将Base64字符串转换为二进制
     *
     * @param base64Str
     * @return
     */
    public static String base64StrToString(String base64Str) {
        return new String(new Base64().decode(base64Str));
    }

    /**
     * 去除字符串前后的空格 包括半角空格和全角空格(中文) java的string.trim()只能去英文半角空格
     *
     * @param str
     */
    public static String trim(String str) {
        if (StringUtils.isBlank(str)) {
            return str;
        }

        char[] val = str.toCharArray();
        int st = 0;
        int len = val.length;
        //半角空格
        while ((st < len) && (val[st] <= ' ')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= ' ')) {
            len--;
        }
        //全角空格
        while ((st < len) && (val[st] <= '　')) {
            st++;
        }
        while ((st < len) && (val[len - 1] <= '　')) {
            len--;
        }
        return ((st > 0) || (len < val.length)) ? str.substring(st, len) : str;
    }

    public static String deleteBlank(String str) {
        char[] array = str.toCharArray();
        int start = 0, end = array.length - 1;
        while (array[start] == ' ') start++;
        while (array[end] == ' ') end--;
        return new String(array, start, end - start);
    }

    public static void main(String[] args) {

        //   	String str ="HKDC       ";


//    	char[] c=str.toCharArray(); 
//    	for(int i=0;i<c.length;i++) { 
//    		System.out.println(c[i]+" : " +(int)c[i]); 
//    	} 


//    	str = str.replace((char) 12288, ' ');
//    	str=str.trim();
        //   	System.out.println(str.replaceAll("[\\u00A0]+", "")+",str");
//        【"userId":"msgId"】
        System.out.println(Base64Util.toBase64Str("hxopensys-task:0164b529e36d7f51543cc3dbb49793b8:" + System.currentTimeMillis()));

        String[] string = Base64Util.base64StrToString("emhhbmdzYW46ZTEwYWRjMzk0OWJhNTlhYmJlNTZlMDU3ZjIwZjg4M2U=").split(":");
        System.out.println(string[0]);
//
//        
        try {
            System.out.println("md5加密:" + MD5Util.getMD5("hxopensys"));
        } catch (Exception e) {
            e.printStackTrace();
        }
//    	System.out.println("str:"+str);
//    	System.out.println("str1:"+str1);
//    	System.out.println("str2:"+str2);

    }

}
