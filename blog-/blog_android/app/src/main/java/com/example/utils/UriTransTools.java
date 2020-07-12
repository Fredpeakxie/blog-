package com.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class UriTransTools {
     public static String UriTransToBase64String(Context context, Uri uri){
          String imgToBase64 = "";
          InputStream inputStream = null;
          try {
               inputStream = context.getContentResolver().openInputStream(uri);
               Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
               imgToBase64 = imgToBase64(bitmap);
          } catch (FileNotFoundException e) {
               e.printStackTrace();
          }finally {
               if(inputStream != null){
                    try {
                         inputStream.close();
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
               }
          }
          return imgToBase64;
     }

     public static String imgToBase64(Bitmap bitmap){
          if (bitmap != null){
               ByteArrayOutputStream out = null;
               try {
                    out = new ByteArrayOutputStream();
                    //进行格式压缩
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
                    out.flush();
                    byte[] imgBytes = out.toByteArray();
                    return Base64.encodeToString(imgBytes, Base64.DEFAULT);
               } catch (IOException e) {
                    e.printStackTrace();
               } finally {
                    try {
                         out.close();
                    } catch (IOException e) {
                         e.printStackTrace();
                    }
               }
          }
          return "bitmap null";
     }
}
