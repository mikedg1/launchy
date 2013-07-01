package com.mikedg.android.glass.launchy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LongPressBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);

        // FIXME: I'm fairly sure that I butchered these flags
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
        
        abortBroadcast();
    }
}
