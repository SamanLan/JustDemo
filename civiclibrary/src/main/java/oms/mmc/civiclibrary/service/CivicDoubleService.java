package oms.mmc.civiclibrary.service;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import oms.mmc.civic.ICivicDamonAidlInterface;
import oms.mmc.civiclibrary.util.AbstractWeakHandler;

import static oms.mmc.civiclibrary.CivicManager.CIVIC_AVTION;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/5 09:50<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b>7.0以下是隐藏通知栏的前台服务，7.0以上是两个循环启动的服务
 */

public class CivicDoubleService extends Service {
    private static final int NOTICE_ID = 100;
    private static final String TAG = "CivicDoubleService";
    private ICivicDamonAidlInterface stub;
    private ServiceConnection conn;
    private CivicDoubleHandler handler = new CivicDoubleHandler(this);
    /**
     * 链接断开（死亡）回调
     */
    IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (stub != null) {
                stub.asBinder().unlinkToDeath(deathRecipient, 0);
                stub = null;
                System.out.println("死亡回调" + Thread.currentThread().getName());
                bindDamonService();
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("双重前台服务bind");
        return null;
    }

    @Override
    public void onCreate() {
        System.out.println("双重前台服务creat");
        bindDamonService();
        super.onCreate();
        handler.sendEmptyMessageDelayed(0, 10000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("双重前台服务startCommand");
        if (Build.VERSION.SDK_INT >= 24) {
            // none
        } else if (Build.VERSION.SDK_INT < 18) {
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
        Intent intent = new Intent(getApplicationContext(), Build.VERSION.SDK_INT >= 24 ? InnerService.class : CivicDoubleService.class);
        startService(intent);

    }

    private void bindDamonService() {
        System.out.println("双重前台服务bind服务端守护进程服务");
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
        private CivicDoubleHandler handler = new CivicDoubleHandler(this);
        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            if (Build.VERSION.SDK_INT >= 24) {
                handler.sendEmptyMessageDelayed(0, 10000);
            }
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            System.out.println("双重前台服务inner---startcommand");
            if (Build.VERSION.SDK_INT >= 24) {
                return START_STICKY;
            } else {
                startForeground(NOTICE_ID, new Notification());
                stopForeground(true);
                stopSelf();
                return super.onStartCommand(intent, flags, startId);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (Build.VERSION.SDK_INT >= 24) {
                Intent intent = new Intent(getApplicationContext(), CivicDoubleService.class);
                startService(intent);
            }
        }
    }

    private static class CivicDoubleHandler extends AbstractWeakHandler<Service>{

        private Intent intent;

        public CivicDoubleHandler(Service service) {
            super(service);
            intent = new Intent();
            intent.setAction(CIVIC_AVTION);
        }

        @Override
        public void handleMessage(Message msg, @NonNull Service service) {
            service.sendBroadcast(intent);
            sendEmptyMessageDelayed(0, 10000);
        }
    }
}
