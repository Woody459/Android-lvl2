package com.example.myapplication;

import android.os.Handler;

import java.io.IOException;
import java.util.logging.LogRecord;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OkHttpRequester {
 public interface OnResponseComplited{
     void onComplited (String contenet);
 }

private OnResponseComplited listner;

 public OkHttpRequester(OnResponseComplited listner){
     this.listner = listner;
 }

 public void run(String url){
     OkHttpClient client = new OkHttpClient();
     Request.Builder builder = new Request.Builder();
     builder.url(url);
     Request request = builder.build();

     Call call = client.newCall(request);
     call.enqueue(new Callback() {

         final Handler handler = new Handler();

         @Override
         public void onFailure(Call call, IOException e) {

         }

         @Override
         public void onResponse(Call call, Response response) throws IOException {
            final String answer = response.body().string();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    listner.onComplited(answer);
                }
            });
         }
     });


 }

}


