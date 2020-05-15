package com.sg.notifylinkclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
EditText login;
EditText password;
TextView communique;
    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=findViewById(R.id.login);
        password=findViewById(R.id.password);
        communique=findViewById(R.id.communiqueTextView );
        communique.setText("");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(!sharedPref.getString("token", "0").equals("0"))
        {
            Intent intent = new Intent();
            intent.setClass( MainActivity.this, Menu.class);
            startActivity(intent);
        }

    }
    public void login(View view)
    {
        apiInterface = APIClient.getClient().create(APIInterface.class);

        User user = new User();
        user.username= login.getText().toString();
        user.password=password.getText().toString();
        Call<UserToken> call1 = apiInterface.doLogin(user);
        call1.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                UserToken resource = response.body();
                if(resource!=null)
                {  String token = resource.name;
                    String username = resource.username;
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("token", String.valueOf(token));
                    editor.putString("username", String.valueOf(username));
                    editor.commit();
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, Menu.class);
                    startActivity(intent);
                }
                else
                    communique.setText("Spróbuj jeszcze raz.");

            }


            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                call.cancel();
            }

        });
    }



}
