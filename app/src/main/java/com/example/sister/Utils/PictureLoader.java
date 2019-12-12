package com.example.sister.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class PictureLoader {

    private String imgUrl;
    private ImageView loadImg;
    private byte[] picByte;

    Handler handler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what ==0x123){
                if(picByte !=null){
                    //将转换完成的字节数组解析为Bitmap然后显示
                    Bitmap bitmap =BitmapFactory.decodeByteArray(picByte,0,picByte.length);
                    loadImg.setImageBitmap(bitmap);
                }
            }
        }
    };

    public void load(ImageView loadImg , String imgUrl){
        this.loadImg =loadImg;
        this.imgUrl =imgUrl;
        Drawable drawable =loadImg.getDrawable();
        if(drawable!=null && drawable instanceof BitmapDrawable){
            Bitmap bitmap =((BitmapDrawable) drawable).getBitmap();
            if(bitmap!=null && bitmap.isRecycled()){
            bitmap.recycle();
            }
        }
        new Thread(runnable).start();
    }

    Runnable runnable =new Runnable() {         //下载任务逻辑，将输入流变为字节数组
        @Override
        public void run() {
            try {
                URL url =new URL(imgUrl);
                try {
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(10000);
                    if(connection.getResponseCode() ==200){
                        InputStream is =connection.getInputStream();
                        ByteArrayOutputStream bao =new ByteArrayOutputStream();
                        int length =-1;
                        byte [] bytes =new byte[1024];
                        while((length =is.read(bytes))!=-1){
                            bao.write(bytes,0,length);
                        }
                        picByte =bao.toByteArray();
                        is.close();;
                        bao.close();
                        handler.sendEmptyMessage(0x123);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    };
}
