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
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Toast;

public class LaunchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = getIntent().getData();
        
        Intent i = new Intent();
        //getPath includes the leading / so skip it
        i.setComponent(new ComponentName(uri.getHost(), uri.getPath().substring(1)));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        
        finish();
        //Toast.makeText(this, "In LaunchActivity" + getIntent().getDataString(), Toast.LENGTH_LONG).show();
    }
}
