package oms.mmc.civiclibrary;

import android.os.Build;
import android.support.annotation.MainThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import oms.mmc.civiclibrary.type.CivicType2Api21;
import oms.mmc.civiclibrary.type.ICivicType;

/**
 * Author：zixin on 2018/2/5 10:24
 * E-mail：lanshenming@linghit.com
 */

public class JobSchedulerManager {
    public final static String CIVIC_TAG = "Civic";
    private static final int JOB_ID = 1;
    private static JobSchedulerManager jobManager;
    private List<ICivicType> typeList;

    private JobSchedulerManager() {
        this.typeList = new ArrayList<>();
    }

    @MainThread
    public static JobSchedulerManager getJobSchedulerInstance() {
        if (jobManager == null) {
            jobManager = new JobSchedulerManager();
        }
        return jobManager;
    }

    public void startJobScheduler() {
        Log.e(CIVIC_TAG, "调用启动");
        Log.e(CIVIC_TAG, "添加的保活list长度" + typeList.size());
        for (int i = 0; i < typeList.size(); i++) {
            ICivicType iCivicType = typeList.get(i);
            if (iCivicType!=null) {
                Log.e(CIVIC_TAG, iCivicType.getName());
                iCivicType.start();
            }
        }
    }

    public void stopJobScheduler() {
        Log.e(CIVIC_TAG, "调用关闭");
        Log.e(CIVIC_TAG, "保活list长度" + typeList.size());
        for (int i = 0; i < typeList.size(); i++) {
            ICivicType iCivicType = typeList.get(i);
            if (iCivicType!=null) {
                Log.e(CIVIC_TAG, iCivicType.getName());
                iCivicType.stop();
            }
        }
    }

    public JobSchedulerManager setTypeList(ICivicType... civicTypes) {
        if (typeList == null) {
            typeList = new ArrayList<>();
        } else {
            typeList.clear();
        }
        List<ICivicType> tmp = Arrays.asList(civicTypes);
        if (isBelowLOLLIPOP()) {
            Iterator<ICivicType> it = tmp.iterator();
            while (it.hasNext()) {
                ICivicType type = it.next();
                if (type instanceof CivicType2Api21) {
                    it.remove();
                }
            }
        }
        typeList.addAll(tmp);
        return this;
    }

    private boolean isBelowLOLLIPOP() {
        // API< 21
        return Build.VERSION.SDK_INT < 21;
    }
}
