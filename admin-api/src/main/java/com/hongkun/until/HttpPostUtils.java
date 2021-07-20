package com.hongkun.until;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpPostUtils {

    public static String httpPostJson(String param, String urlPath) {
        String result = "";
        // Post请求的url，与get不同的是不需要带参数
//	       param="UserName=v_fengxiaoyang&UserPwd=123456&NewPwd=321321&CheckNewPwd=321321&Token=22E0E2B6-A672-4F00-8DDF-E1DD8F22E3CF";
//	       urlPath = new String("http://192.168.0.203:8086/UpdateUserPwd.aspx"); 

        //建立连接
        URL url;
        HttpURLConnection httpConn = null;
        try {
            url = new URL(urlPath);
            httpConn = (HttpURLConnection) url.openConnection();
            //设置参数
            httpConn.setDoOutput(true); //需要输出
            httpConn.setDoInput(true);//需要输入
            httpConn.setUseCaches(false);//不允许缓存
            httpConn.setRequestMethod("POST");//设置POST方式连接
            //设置请求属性
            httpConn.setRequestProperty("Content-Type", "mappers/application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", "UTF-8");

            httpConn.setConnectTimeout(10000);
            httpConn.setReadTimeout(10000);


            //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
            httpConn.connect();
            //建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();
            //获得响应状态
            int resultCode = httpConn.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                result = changeInputStream(httpConn.getInputStream(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpConn != null) {
                httpConn.disconnect();
                httpConn = null;
            }
        }

        return result;
    }

    /**
     * get请求 参数拼接好
     *
     * @param path
     * @return
     */
    private static String startGet(String path) {

        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            //GET请求直接在链接后面拼上请求参数
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "mappers/application/x-www-form-urlencoded");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * 将一个输入流转换成指定编码的字符串
     *
     * @param inputStream
     * @param encode
     * @return
     */
    private static String changeInputStream(InputStream inputStream,
                                            String encode) {
        // 内存流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        String result = null;
        if (inputStream != null) {
            try {
                while ((len = inputStream.read(data)) != -1) {
                    byteArrayOutputStream.write(data, 0, len);
                }
                result = new String(byteArrayOutputStream.toByteArray(), encode);
                System.out.println(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
