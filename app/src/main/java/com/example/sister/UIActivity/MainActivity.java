package com.example.sister.UIActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sister.Bean.Girl;
import com.example.sister.R;
import com.example.sister.Utils.GirlApi;
import com.example.sister.Utils.PictureLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button show;
    private Button refresh;
    private ImageView showImage;

    private ArrayList<Girl> data;
    private ArrayList<String> urls;
    private PictureLoader loader;
    private GirlApi api;
    private GirlStack girlStack;

    private int count=0;
    private int page =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader =new PictureLoader();
        api =new GirlApi();
        initData();
        initUI();
    }

    private void initData(){
//        urls =new ArrayList<String>();
//        urls.add("http://ww4.sinaimg.cn/large/610dc034jw1f6ipaai7wgj20dw0kugp4.jpg");
//        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f6gcxc1t7vj20hs0hsgo1.jpg");
//        urls.add("http://ww4.sinaimg.cn/large/610dc034jw1f6f5ktcyk0j20u011hacg.jpg");
//        urls.add("http://ww1.sinaimg.cn/large/610dc034jw1f6e1f1qmg3j20u00u0djp.jpg");
//        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f6aipo68yvj20qo0qoaee.jpg");
//        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f69c9e22xjj20u011hjuu.jpg");
//        urls.add("http://ww3.sinaimg.cn/large/610dc034jw1f689lmaf7qj20u00u00v7.jpg");
//        urls.add("http://ww3.sinaimg.cn/large/c85e4a5cjw1f671i8gt1rj20vy0vydsz.jpg");
//        urls.add("http://ww2.sinaimg.cn/large/610dc034jw1f65f0oqodoj20qo0hntc9.jpg");
//        urls.add("http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");

        data =new ArrayList<>();
    }

    private void initUI(){
        refresh =findViewById(R.id.refresh);
        showImage =findViewById(R.id.img);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show:
                if(data!=null && !data.isEmpty()){
                    if(count<9){
                        count =0;
                    }
                    loader.load(showImage,data.get(count).getUrl());
                    count++;
                }
                break;
            case R.id.refresh:
                page++;
                girlStack= new GirlStack();
                girlStack.execute();
                count =0;
                break;
        }
    }

    private class GirlStack extends AsyncTask<Void,Void,ArrayList<Girl>>{

        public GirlStack( ){ }

        @Override
        protected ArrayList<Girl> doInBackground(Void... voids) {
            try {
                return api.fetchGirl(10,page);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  null;
        }

        @Override
        protected void onPostExecute(ArrayList<Girl> girls) {
            super.onPostExecute(girls);
            data.clear();
            data.addAll(girls);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            girlStack =null;
        }


    }
}
