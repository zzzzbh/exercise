package com.example.sister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sister.Utils.PictureLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button refresh;
    private ImageView showImage;
    private ArrayList<String> urls;
    private PictureLoader loader;
    private int curPos=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh =findViewById(R.id.refresh);
        showImage =findViewById(R.id.show);
        loader =new PictureLoader();
        initData();
        initUI();
    }

    private void initData(){
        urls =new ArrayList<String>();
        urls.add("");
    }
}
