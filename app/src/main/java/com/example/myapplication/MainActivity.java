package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WebView page = findViewById(R.id.page);

        OkHttpRequester requester = new OkHttpRequester(new OkHttpRequester.OnResponseComplited() {
            @Override
            public void onComplited(String contenet) {

                        page.loadData(contenet, "text/html; charset=utf-8", "utf-8");

            }
        });
        requester.run("https://www.youtube.com/");
        }
    }


