package com.example.user.sitemonitorapp.activity;

/**
 * Created by user on 30-10-2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent pushIntent = new Intent(context, MainActivity.class);
            pushIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(pushIntent);}
    }

}
