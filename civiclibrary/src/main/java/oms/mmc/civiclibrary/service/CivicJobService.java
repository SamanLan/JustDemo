package oms.mmc.civiclibrary.service;

import android.annotation.TargetApi;
import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import static oms.mmc.civiclibrary.CivicManager.CIVIC_AVTION;
import static oms.mmc.civiclibrary.CivicManager.CIVIC_TAG;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/5 09:50<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b>5.0以上使用的东西
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class CivicJobService extends JobService {
    // 告知编译器，这个变量不能被优化
    private volatile static Service civicJobService = null;

    public static boolean isJobServiceAlive(){
        return civicJobService != null;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(CIVIC_TAG, "思域保活模式，jobService被启动");
        civicJobService = this;
        // 发广播激活APP
//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        Intent intent = new Intent();
        intent.setAction(CIVIC_AVTION);
        sendBroadcast(intent);
        // 工作完成
        jobFinished(params, false);
        // 返回false，系统假设这个方法返回时任务已经执行完毕；
        // 返回true，系统假定这个任务正要被执行
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(CIVIC_TAG, "思域保活模式，jobService结束");
        return false;
    }
}
