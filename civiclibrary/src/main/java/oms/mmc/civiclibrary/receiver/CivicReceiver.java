package oms.mmc.civiclibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import oms.mmc.civiclibrary.service.CivicDoubleService;

import static oms.mmc.civiclibrary.JobSchedulerManager.CIVIC_TAG;

/**
 * Author：zixin on 2018/2/5 09:22
 * E-mail：lanshenming@linghit.com
 */

public class CivicReceiver extends BroadcastReceiver {

    private final static String[] ACTIONS = {"android.net.conn.CONNECTIVITY_CHANGE",
            "android.bluetooth.adapter.action.STATE_CHANGED",
            "oms.mmc.almanac.ACTION_WETH_UPDATE", "android.intent.action.TIME_SET",
            "android.intent.action.DATE_CHANGED", "android.intent.action.TIMEZONE_CHANGED",
            "android.intent.action.USER_PRESENT", "android.intent.action.BOOT_COMPLETED",
            "android.intent.action.ACTION_POWER_CONNECTED", "android.intent.action.ACTION_POWER_DISCONNECTED",
            "android.intent.action.LOCALE_CHANGED", "android.intent.action.MEDIA_UNMOUNTED",
            "android.intent.action.MEDIA_REMOVED", "android.intent.action.MEDIA_CHECKING",
            "android.intent.action.MEDIA_EJECT"};

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(CIVIC_TAG, "收到广播啦");
        context.startService(new Intent(context, CivicDoubleService.class));
    }
}
