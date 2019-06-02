package com.example.myapplication;

import android.content.SharedPreferences;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button bthRestore;
    Button bthRun;
    EditText request;

    private Links links;
    private SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialize();
        initPreferences();
        initRetrofit();
        pressSearch();

        }




    private void initialize() {
        bthRun = findViewById(R.id.bthRun);
        bthRestore = findViewById(R.id.bthRestore);
        request = findViewById(R.id.request);
    }



    private void initPreferences() {
        sharedPref = getPreferences(MODE_PRIVATE);
        loadPreferences();
    }

    private void loadPreferences() {

        String loadedApiKey = sharedPref.getString("key", "myPref76");
        request.setText(loadedApiKey);
    }




    private void savePreferences(){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("key", request.getText().toString());
        editor.commit();
    }


    private String scanRequest(EditText request){

        String requestText = request.getText().toString();

        return requestText ;
        }

    private void pressSearch() {

        final EditText request = findViewById(R.id.request);
        bthRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final WebView page = findViewById(R.id.page);
               savePreferences();

                OkHttpRequester requester = new OkHttpRequester(new OkHttpRequester.OnResponseComplited() {
                    @Override
                    public void onComplited(String contenet) {

                        page.loadData(contenet, "text/html; charset=utf-8", "utf-8");

                    }
                });

                requester.run(scanRequest(request));

            }
        });

        bthRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPreferences();
            }
        });

    }




    private void initRetrofit() {
        Retrofit retrofit;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://startandroid.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        links = retrofit.create(Links.class);

    }


}


