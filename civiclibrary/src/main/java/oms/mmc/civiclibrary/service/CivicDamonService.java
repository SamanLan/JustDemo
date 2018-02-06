package oms.mmc.civiclibrary.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import oms.mmc.civic.ICivicDamonAidlInterface;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/5 09:50<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b>一个binder服务，其实这个没什么鸟用。
 */

public class CivicDamonService extends Service {
    private static final String TAG = "CivicDamonService";
    private CivicDamonBinder civicDamonBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("守护服务bind");
        return civicDamonBinder.asBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("守护服务onCreate");
        civicDamonBinder = new CivicDamonBinder();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        System.out.println("守护服务unbindService，将重启doubleservice");
        startService(new Intent(this, CivicDoubleService.class));
    }

    private class CivicDamonBinder extends ICivicDamonAidlInterface.Stub{
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }
}
