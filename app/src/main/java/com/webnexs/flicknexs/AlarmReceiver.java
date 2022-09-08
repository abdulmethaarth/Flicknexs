package com.webnexs.flicknexs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.WindowManager;

public class AlarmReceiver extends BroadcastReceiver {

    MainActivity mainActivity;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent();
        i.setClassName("com.webnexs.flicknexs", "com.webnexs.flicknexs.MainActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
