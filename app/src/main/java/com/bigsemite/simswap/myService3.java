package com.bigsemite.simswap;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

/**
 * Created by amaose on 12/9/2017.
 */

public class myService3 extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        //call the broadcastreceiver
        BroadcastReceiver br = new Monitor();
        registerReceiver(br, intentFilter);

        Toast.makeText(getApplicationContext(),"SimSwap Service started", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
