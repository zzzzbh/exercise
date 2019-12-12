package com.example.sister.Utils;

import android.util.Log;

import com.example.sister.Bean.Girl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

public class GirlApi {

    private static final String TAG ="NETWORK";
    private static final String BASE_URL ="http://gank.io/api/data/福利";

    public ArrayList<Girl> fetchGirl (int count,int page) throws Exception{
        String fetchUrl =BASE_URL + count +"/" +page ;
        ArrayList<Girl> girls =new ArrayList<>();
        try {
            URL url = new URL(fetchUrl);
            try {
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000);
                int code =connection.getResponseCode();
                Log.e("TAG","Server Response" +code);
                if(code ==200){
                    InputStream is =connection.getInputStream();
                    byte [] data =readFromStream(is);
                    String result =new String(data);
                     girls =parseGirls(result);
                }else{
                    Log.e("TAG","请求失败" +code);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return girls;
    }


    public ArrayList<Girl> parseGirls(String content){
        ArrayList<Girl> girls =new ArrayList<>();
        try {
            JSONObject object =new JSONObject();
            JSONArray array =object.getJSONArray(content);
            for(int i=0;i<array.length();i++){
                JSONObject result = (JSONObject) array.get(i);
                Girl girl =new Girl();
                girl.setId(result.getString("id"));
                girl.setId(result.getString("createAt"));
                girl.setId(result.getString("desc"));
                girl.setId(result.getString("source"));
                girl.setId(result.getString("type"));
                girl.setId(result.getString("url"));
                girl.setId(result.getString("who"));
                girls.add(girl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return girls;
    }

    public byte[] readFromStream (InputStream inputStream ) throws Exception{
        ByteArrayOutputStream bao =new ByteArrayOutputStream();
        byte [] buffer =new byte[1024];
        int len;
        while((len =inputStream.read(buffer))!=-1){
            bao.write(buffer,0,len);
        }
        inputStream.close();
        bao.close();
        return bao.toByteArray();
    }
}
