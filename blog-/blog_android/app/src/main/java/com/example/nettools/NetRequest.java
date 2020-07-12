package com.example.nettools;


import android.util.Log;

import com.example.utils.Pair;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 最底层的网络访问
 * 1 打开链接并使用传入url 和 数据list 来 生成 request
 * 2 查看响应 成功接收响应 后读取相应内容
 *      根据响应内容 生成返回的String 或 byte[]
 */
public class NetRequest {
    //提供公用的访问

    /**
     *
     * @return "" 空串代表出错
     */
//    public static String RequestPost(String url, List<Pair> listP){
//        String respMsg = "";
//        try {
//            HttpURLConnection conn = requestSet(url, listP);
//            //7 处理响应此处针对 字符流   文件资源的读取待 再写
//            respMsg = StringRead(conn);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return respMsg;
//    }

    public static String netGetString(String url, List<Pair> listP){
        String respMsg = "";
        try {
            HttpURLConnection conn = requestSet(url, listP);
            //7 处理响应此处针对 字符流   文件资源的读取待 再写
            respMsg = StringRead(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respMsg;
    }

    /*public static String netGetStringWithUtf8(String url, List<Pair> listP){
        String respMsg = "";
        try {
            HttpURLConnection conn = requestSetWithUTF8(url, listP);
            //7 处理响应此处针对 字符流   文件资源的读取待 再写
            respMsg = StringRead(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return respMsg;
    }*/

    public static byte[] netGetByte(String url, List<Pair> listP){
        byte[] bytes = null;
        try {
            HttpURLConnection conn = requestSet(url, listP);
            //7 处理响应此处针对 字符流   文件资源的读取待 再写

            bytes = byteRead(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static HttpURLConnection requestSet(String url, List<Pair> listP) throws IOException {
        //1 使用URL打开连接
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        //2 设置方法 POST 请求超时时间
        conn.setRequestMethod("POST");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
//        conn.setRequestProperty("Content-type","UTF-8");
        //3 设置运行输入,输出:
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //4 Post方式不能缓存,需手动设置为false
        conn.setUseCaches(false);
        //5 我们请求的数据:
        //builder
        String data = "";
        for (int i = 0; i < listP.size(); i++) {
            Pair pair = listP.get(i);
            data += (pair.getKey() + "=" + URLEncoder.encode(pair.getValue(), "UTF-8"));
//            data += (pair.getKey() + "=" + pair.getValue());
            if(i != (listP.size() - 1)){
                data += "&";
            }
        }
        Log.d(TAG, "RequestSet: "+data);
        Log.d(TAG, "RequestSet: "+url);

//        data = URLEncoder.encode(data,"UTF-8");
        //6 获取输出流
        OutputStream out = conn.getOutputStream();
        out.write(data.getBytes());
        out.flush();
        return conn;
    }


    /*public static HttpURLConnection requestSetWithUTF8(String url, List<Pair> listP) throws IOException {
        //1 使用URL打开连接
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        //2 设置方法 POST 请求超时时间
        conn.setRequestMethod("POST");
        conn.setReadTimeout(5000);
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-type","text/html");
        conn.setRequestProperty("charset","UTF-8");
        //3 设置运行输入,输出:
        conn.setDoOutput(true);
        conn.setDoInput(true);
        //4 Post方式不能缓存,需手动设置为false
        conn.setUseCaches(false);
        //5 我们请求的数据:
        //builder
        String data = "";
        for (int i = 0; i < listP.size(); i++) {
            Pair pair = listP.get(i);
//            data += (pair.getKey() + "=" + URLEncoder.encode(pair.getValue(), "UTF-8"));
            data += (pair.getKey() + "=" + pair.getValue());
            if(i != (listP.size() - 1)){
                data += "&";
            }
        }
        Log.d(TAG, "RequestSet: "+data);
        Log.d(TAG, "RequestSet: "+url);

//        data = URLEncoder.encode(data,"UTF-8");
        //6 获取输出流
        OutputStream out = conn.getOutputStream();
        out.write(data.getBytes());
        out.flush();
        return conn;
    }*/

    /**
     * 处理 字符
     * @param conn
     * @return "" 如果过程中出错
     * @throws IOException
     */
    public static String StringRead(HttpURLConnection conn) throws IOException {
        String respMsg = "";
        if (conn.getResponseCode() == 200) {
            // 1 获取响应的输入流对象
            InputStream is = conn.getInputStream();
            // 2 创建字节输出流对象
            ByteArrayOutputStream message = new ByteArrayOutputStream();
            // 定义读取的长度
            int len = 0;
            // 定义缓冲区
            byte buffer[] = new byte[1024];
            // 3 按照缓冲区的大小，循环读取
            while ((len = is.read(buffer)) != -1) {
                // 根据读取的长度写入到os对象中
                message.write(buffer, 0, len);
            }
            // 4 释放资源
            is.close();
            message.close();
            // 5 返回字符串
            respMsg =  new String(message.toByteArray());
        }
        return respMsg;
    }

    /**
     * 处理字节流 可用于处理图片等
     * @param conn
     * @return null 当响应头不为200 或出错时
     * @throws IOException
     */
    public static byte[] byteRead(HttpURLConnection conn) throws IOException {
        byte[] bytes = null;
        if (conn.getResponseCode() == 200) {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            // 1 获取响应的输入流对象
            InputStream is = conn.getInputStream();
            // 2 创建字节输出流对象
            int len = 0;
            while((len = is.read(buffer)) != -1) {
                outStream.write(buffer,0,len);
            }
            is.close();
            bytes = outStream.toByteArray();
            return bytes;
        }
        return bytes;
    }

//    public static void uploadFile()
//    {
//        String end = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//        try
//        {
//            URL url =new URL(actionUrl);
//            HttpURLConnection con=(HttpURLConnection)url.openConnection();
//            /* 允许Input、Output，不使用Cache */
//            con.setDoInput(true);
//            con.setDoOutput(true);
//            con.setUseCaches(false);
//            /* 设置传送的method=POST */
//            con.setRequestMethod("POST");
//            /* setRequestProperty */
//            con.setRequestProperty("Connection", "Keep-Alive");
//            con.setRequestProperty("Charset", "UTF-8");
//            con.setRequestProperty("Content-Type",
//                    "multipart/form-data;boundary="+boundary);
//            /* 设置DataOutputStream */
//            DataOutputStream ds =
//                    new DataOutputStream(con.getOutputStream());
//            ds.writeBytes(twoHyphens + boundary + end);
//            ds.writeBytes("Content-Disposition: form-data; " +
//                    "name=\"file1\";filename=\"" +
//                    newName +"\"" + end);
//            ds.writeBytes(end);
//
//            /* 取得文件的FileInputStream */
//            FileInputStream fStream = new FileInputStream(uploadFile);
//            /* 设置每次写入1024bytes */
//            int bufferSize = 1024;
//            byte[] buffer = new byte[bufferSize];
//
//            int length = -1;
//            /* 从文件读取数据至缓冲区 */
//            while((length = fStream.read(buffer)) != -1)
//            {
//                /* 将资料写入DataOutputStream中 */
//                ds.write(buffer, 0, length);
//            }
//            ds.writeBytes(end);
//            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
//
//            /* close streams */
//            fStream.close();
//            ds.flush();
//
//            /* 取得Response内容 */
//            InputStream is = con.getInputStream();
//            int ch;
//            StringBuffer b =new StringBuffer();
//            while( ( ch = is.read() ) != -1 )
//            {
//                b.append( (char)ch );
//            }
//            /* 将Response显示于Dialog */
//            showDialog("上传成功"+b.toString().trim());
//            /* 关闭DataOutputStream */
//            ds.close();
//        } catch(Exception e) {
//            showDialog("上传失败"+e);
//        }
//    }
}

//            String data = "";
//            for ( Map.Entry<String,String> entry : map.entrySet()) {
//                entry.getKey();
//                String value = entry.getValue();
//                data += "&" + entry.getKey() + "=" + URLEncoder.encode(value, "UTF-8");
//            }



//            if (conn.getResponseCode() == 200) {
//                // 1 获取响应的输入流对象
//                InputStream is = conn.getInputStream();
//                // 2 创建字节输出流对象
//                ByteArrayOutputStream message = new ByteArrayOutputStream();
//                // 定义读取的长度
//                int len = 0;
//                // 定义缓冲区
//                byte buffer[] = new byte[1024];
//                // 3 按照缓冲区的大小，循环读取
//                while ((len = is.read(buffer)) != -1) {
//                    // 根据读取的长度写入到os对象中
//                    message.write(buffer, 0, len);
//                }
//                // 4 释放资源
//                is.close();
//                message.close();
//                // 5 返回字符串
//                respMsg = new String(message.toByteArray());
//            }


//            String data = "username=" + URLEncoder.encode(username, "UTF-8") +
//                    "&password=" + URLEncoder.encode(password, "UTF-8");
