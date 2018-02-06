package oms.mmc.civiclibrary;

import android.content.Context;
import android.os.Build;
import android.support.annotation.MainThread;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import oms.mmc.civiclibrary.type.CivicType2Api21;
import oms.mmc.civiclibrary.type.CivicTypeLessApi24;
import oms.mmc.civiclibrary.type.ICivicType;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/5 09:50<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b>管理器
 */

public class CivicManager {
    public final static String CIVIC_TAG = "Civic";
    public final static String CIVIC_AVTION = "civic_action";
    private static final int JOB_ID = 1;
    private static CivicManager jobManager;
    private List<ICivicType> typeList;

    private CivicManager() {
        this.typeList = new ArrayList<>();
    }

    @MainThread
    public static CivicManager getJobSchedulerInstance() {
        if (jobManager == null) {
            jobManager = new CivicManager();
        }
        return jobManager;
    }

    public void startNormal(Context context) {
        jobManager.setTypeList(new CivicTypeLessApi24(context), new CivicType2Api21(context)).startJobScheduler();
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

    public CivicManager setTypeList(ICivicType... civicTypes) {
        if (typeList == null) {
            typeList = new ArrayList<>();
        } else {
            typeList.clear();
        }
        List<ICivicType> tmp = Arrays.asList(civicTypes);
        Iterator<ICivicType> it = tmp.iterator();
        while (it.hasNext()) {
            ICivicType type = it.next();
            if (isBelowLOLLIPOP()) {
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

    private boolean isAboveNougat() {
        // API< 21
        return Build.VERSION.SDK_INT >= 24;
    }
}
