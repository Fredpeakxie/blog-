package com.fred.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @auther fred
 * @create 2020-06-03 15:24
 */
public class FileService {
    public static final String PROJECT_URI = "D:\\android_blog_project\\out/artifacts/blogServer_war_exploded";
    public static final String IMAGE_URI = "/image/";
    public static final String BLOG_URI = "/blogs/";
    public static final String ARTICLE_PIC_URI = "/blogpic/";




    public static final String HEAD = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Title</title>\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
            "</head>\n" +
            "<body>";
    public static final String FOOT = "\n" +
            "</body>\n" +
            "</html>";
    /**
     * 将获取到的 base64 加密的字符串解析成 字节数组并写入到 磁盘文件中
     * @param str
     * @param userId
     */
    public static void getPicFormatBASE64(String str, String userId) {
        RandomAccessFile inOut = null;
        try {
            byte[] result = Base64.getMimeDecoder().decode(str.trim());
//            byte[] result = new sun.misc.BASE64Decoder().decodeBuffer(str.trim());
            String userIdS = String.format("%05d", Integer.parseInt(userId));
            inOut = new RandomAccessFile(PROJECT_URI + IMAGE_URI+"i"+userIdS+".jpg", "rw"); // r,rw,rws,rwd
            inOut.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeHtml(int articleId,String html){
        RandomAccessFile inOut = null;
        try {
            String articleIds = String.format("%05d", articleId);
            System.out.println(BLOG_URI+"fb"+articleIds+".html");
            inOut = new RandomAccessFile(PROJECT_URI + BLOG_URI+"fb"+articleIds+".html", "rw");
            byte[] byteHead = HEAD.getBytes("utf-8");
            inOut.write(byteHead);
            byte[] bytes = html.getBytes("utf-8");
            inOut.write(bytes);
            byte[] byteFoot = FOOT.getBytes("utf-8");
            inOut.write(byteFoot);
            System.out.println("成功上传文件");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //还有问题 文件名
    public static Boolean setArticlePic( int articleId,int picId,String str){
        RandomAccessFile inOut = null;
        try {
            byte[] result = Base64.getMimeDecoder().decode(str.trim());
//            byte[] result = new sun.misc.BASE64Decoder().decodeBuffer(str.trim());
            String articleIdS = String.format("%05d", articleId);
            String picIdS = String.format("%02d", picId);
//            String src = "../blogpic/b"+articleIdS+"p"+picS+".jpg";

            inOut = new RandomAccessFile(PROJECT_URI + ARTICLE_PIC_URI+"b"+articleIdS+"p"+picIdS+".jpg", "rw"); // r,rw,rws,rwd
            inOut.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inOut != null){
                try {
                    inOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
