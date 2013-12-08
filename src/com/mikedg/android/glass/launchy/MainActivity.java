/*
Copyright 2013 Michael DiGiovanni glass@mikedg.com

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
//A good 80% of this app is from the Android SDK home app sample
package com.mikedg.android.glass.launchy;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;
//import com.google.android.glass.timeline.LiveCard;
//import com.google.android.glass.timeline.TimelineManager;

public class MainActivity extends Activity {

    private AppHelper mAppHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppHelper = new AppHelper(this);
        mAppHelper.loadApplications(true);

        mAppHelper.bindApplications();
        mAppHelper.registerIntentReceivers();

        // setupTestReceiver();

        final ListView list = (ListView) findViewById(android.R.id.list);
        list.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                list.smoothScrollToPositionFromTop(position, 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addDataScheme("package");
        registerReceiver(mPackageBroadcastReciever, intentFilter);

//        publishCard(this);
    }

    // Cached instance of the LiveCard created by the publishCard() method.
    private LiveCard mLiveCard;

    private void publishCard(Context context) {
        if (mLiveCard == null) {
            String cardId = "my_card";
            TimelineManager tm = TimelineManager.from(context);
            mLiveCard = tm.getLiveCard(cardId);

            mLiveCard.setViews(new RemoteViews(context.getPackageName(),
                    R.layout.activity_main));
            Intent intent = new Intent(context, MainActivity.class);
            mLiveCard.setAction(PendingIntent.getActivity(context, 0,
                    intent, 0));
            
//          if you want Glass to automatically display the live card after publishing, call setNonSilent(boolean) before publish().
            mLiveCard.setNonSilent(true);
            
            mLiveCard.publish();
           
        } else {
            // Card is already published.
        	
            return;
        }
    }

    private void unpublishCard(Context context) {
        if (mLiveCard != null) {
            mLiveCard.unpublish();
            mLiveCard = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAppHelper.onDestroy();
        unregisterReceiver(mPackageBroadcastReciever);
    }

    BroadcastReceiver mPackageBroadcastReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mAppHelper.loadApplications(false);
        }
    };

    // Just some junk I was investigating
    // private void setupTestReceiver() {
    // BroadcastReceiver receiver = new BroadcastReceiver() {
    // @Override
    // public void onReceive(Context context, Intent intent) {
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // Log.d("Launcher", "********((((((");
    // }
    // };
    // IntentFilter filter = new IntentFilter();
    // filter.addAction("com.google.glass.LOG_HEAD_GESTURE");
    // filter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
    // filter.addAction("com.google.glass.action.TOUCH_GESTURE"); //not working? wtf... said, I was
    // hoping to be able to itnercept this, nothing in logs
    // registerReceiver(receiver, filter);
    // }
}
