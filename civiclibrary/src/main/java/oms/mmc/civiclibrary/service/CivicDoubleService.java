package oms.mmc.civiclibrary.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import oms.mmc.civic.ICivicDamonAidlInterface;

/**
 * Author：zixin on 2018/2/5 14:30
 * E-mail：lanshenming@linghit.com
 */

public class CivicDoubleService extends Service {
    private static final int NOTICE_ID = 100;
    private static final String TAG = "CivicDoubleService";
    private ICivicDamonAidlInterface stub;
    private ServiceConnection conn;
    /**
     * 链接断开（死亡）回调
     */
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (stub != null) {
                stub.asBinder().unlinkToDeath(deathRecipient, 0);
                stub = null;
                Log.d(TAG, "死亡回调" + Thread.currentThread().getName());
                bindDamonService();
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "双重前台服务bind");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "双重前台服务creat");
        bindDamonService();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "双重前台服务startCommand");
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(NOTICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, InnerService.class);
            startService(innerIntent);
            startForeground(NOTICE_ID, new Notification());
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"双重前台service被杀死");
        if (conn != null) {
            unbindService(conn);
        }
        // 重启自己
        Intent intent = new Intent(getApplicationContext(),CivicDoubleService.class);
        startService(intent);
    }

    private void bindDamonService() {
        Log.d(TAG, "双重前台服务bind服务端守护进程服务");
        conn = new ServiceConnection(){

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                stub = ICivicDamonAidlInterface.Stub.asInterface(service);
                try {
                    service.linkToDeath(deathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                stub = null;
            }
        };
        Intent intent = new Intent(this, CivicDamonService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    public static class InnerService extends Service{
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d(TAG, "双重前台服务inner---startcommand");
            startForeground(NOTICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
    }
}
