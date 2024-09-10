package com.example.wuziqi0902;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.text.MessageFormat;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoginActivity extends Activity {

    private final static String TAG = LoginActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }

    public void dologin(View view) {
        String phone = ((EditText) findViewById(R.id.et_phone)).getText().toString();
        String username = ((EditText) findViewById(R.id.et_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.et_password)).getText().toString();

        if(phone == null || phone.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"手机号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(username == null || username.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password == null || username.trim().isEmpty()){
            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(phone.trim().length()!=11 || !phone.startsWith("1")){
            Toast.makeText(getApplicationContext(),"手机号码不规范",Toast.LENGTH_SHORT).show();
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://127.0.0.1:5000/authenticate?mobile=" + phone + "&username=" + username + "&password=" + password;
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
            String content = response.body().string();
            Log.i(TAG,content);
            startActivity(new Intent(getApplicationContext(),OnlineActivity.class));
            }
        });
    }
}