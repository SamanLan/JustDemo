package oms.mmc.civiclibrary.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import oms.mmc.civiclibrary.service.CivicDoubleService;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/5 09:50<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b>广播中转。
 */

public class CivicReceiver extends BroadcastReceiver {

//    private final static String[] ACTIONS = {"android.net.conn.CONNECTIVITY_CHANGE",
//            "android.bluetooth.adapter.action.STATE_CHANGED",
//            "oms.mmc.almanac.ACTION_WETH_UPDATE", "android.intent.action.TIME_SET",
//            "android.intent.action.DATE_CHANGED", "android.intent.action.TIMEZONE_CHANGED",
//            "android.intent.action.USER_PRESENT", "android.intent.action.BOOT_COMPLETED",
//            "android.intent.action.ACTION_POWER_CONNECTED", "android.intent.action.ACTION_POWER_DISCONNECTED",
//            "android.intent.action.LOCALE_CHANGED", "android.intent.action.MEDIA_UNMOUNTED",
//            "android.intent.action.MEDIA_REMOVED", "android.intent.action.MEDIA_CHECKING",
//            "android.intent.action.MEDIA_EJECT"};

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("收到广播啦");
        Toast.makeText(context, "收到广播啦", Toast.LENGTH_SHORT).show();
        context.startService(new Intent(context, CivicDoubleService.class));
        context.sendBroadcast(new Intent("com.mmc.action.ACTION_UPDATE_IN_TIMES"));
    }
}
