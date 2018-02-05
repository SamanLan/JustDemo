package oms.mmc.civiclibrary.type;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import oms.mmc.civiclibrary.service.CivicJobService;

import static oms.mmc.civiclibrary.JobSchedulerManager.CIVIC_TAG;

/**
 * Author：zixin on 2018/2/5 12:15
 * E-mail：lanshenming@linghit.com
 */
@TargetApi(21)
public class CivicType2Api21 implements ICivicType {
    private static final int JOB_ID = 1;
    private JobScheduler jobScheduler;
    private Context context;

    public CivicType2Api21(Context context) {
        this.context = context.getApplicationContext();
        jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
    }

    @Override
    public String getName() {
        return "思域保活模式--大于等于API21";
    }

    @Override
    public void start() {
        Log.e(CIVIC_TAG, "调用启动");
        if (CivicJobService.isJobServiceAlive()) {
            return;
        }
        Log.e(CIVIC_TAG, "构建job");
        // 构建JobInfo对象，传递给JobSchedulerService
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(context, CivicJobService.class));
        // 设置每30秒执行一下任务
        builder.setPeriodic(3000);
        // 设置设备重启时，执行该任务
        builder.setPersisted(true);
        // 当插入充电器，执行该任务
        builder.setRequiresCharging(true);
        JobInfo info = builder.build();
        //开始定时执行该系统任务
        jobScheduler.schedule(info);
    }

    @Override
    public void stop() {
        jobScheduler.cancelAll();
    }
}
