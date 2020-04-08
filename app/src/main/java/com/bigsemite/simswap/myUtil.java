package com.bigsemite.simswap;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

/**
 * Created by amaose on 12/9/2017.
 */

public class myUtil {

    public static void sched(Context context){

        ComponentName cn = new ComponentName(context, myService3.class);
        JobInfo.Builder builder = new JobInfo.Builder(20,cn)
                .setMinimumLatency(7000)
                .setPersisted(true)

                .setOverrideDeadline(10000);
        JobInfo jobInfo = builder.build();
        JobScheduler js = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        js.schedule(jobInfo);
    }
}
