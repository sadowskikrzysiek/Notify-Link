package com.sg.notifylinkclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu extends AppCompatActivity {
    APIInterface apiInterface;
    String tokenName;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       if(sharedPref.getString("token", "0").equals("0"))
        {
            Intent intent = new Intent();
            intent.setClass(Menu.this, MainActivity.class);
            startActivity(intent);
        }
       else {
           tokenName = sharedPref.getString("token", "0");
           userName = sharedPref.getString("username", "0");
       }
       startTimer();
    }
    public void logout(View view) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", "0");
        editor.putString("username", "0");
        editor.commit();
        Intent intent = new Intent();
        intent.setClass(Menu.this, MainActivity.class);
        startActivity(intent);
    }
    public void manualRefresh(View view)
    {
        refresh();
    }
    public void refresh() {

        apiInterface = APIClient.getClient().create(APIInterface.class);
         UserToken userToken=new UserToken();
          userToken.name=tokenName;
        userToken.username=userName;
         Call<List<Notify>> call = apiInterface.doGetNotifications(userToken);
            call.enqueue(new Callback<List<Notify>>() {
         @Override
        public void onResponse(Call<List<Notify>> call, Response<List<Notify>> response) {
           List<Notify> resource = response.body();
            String text = String.valueOf(resource.size());
             Log.e("testGAMA",text);
             pushNotifications(resource);


         }

        @Override
         public void onFailure(Call<List<Notify>> call, Throwable t) {
             call.cancel();
        }

         });
    }
    public void pushNotifications(List<Notify> notifications)
    {
        for(Notify notify: notifications) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.notify)
                            .setContentTitle(notify.name)
                            .setContentText(notify.description+" data wysłania: "+notify.createdAt+" wysłane przez: "+notify.creator);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int lastNotifyId=loadLastNotifyId();
            mNotificationManager.notify(lastNotifyId, mBuilder.build());
            saveLastNotifyId(++lastNotifyId);

        }
    }
    public int loadLastNotifyId()
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      int lastNotifyId= sharedPref.getInt("lastId", 0);
       return lastNotifyId;
    }
    public void saveLastNotifyId(int lastId)
    {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("lastId", lastId);
        editor.commit();
    }
    public void startTimer()
    {
        Timer timer = new Timer ();

        TimerTask hourlyTask = new TimerTask () {
            @Override
            public void run ()
            {
                refresh();
                Log.e("refresh","Odświeżanie");
            }};
        timer.schedule (hourlyTask, 0l, 1000*60*5);
    }

}
