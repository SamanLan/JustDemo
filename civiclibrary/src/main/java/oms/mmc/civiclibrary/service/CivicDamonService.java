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
 * Author：zixin on 2018/2/5 15:07
 * E-mail：lanshenming@linghit.com
 */

public class CivicDamonService extends Service {
    private static final String TAG = "CivicDamonService";
    private CivicDamonBinder civicDamonBinder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "守护服务bind");
        return civicDamonBinder.asBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "守护服务onCreate");
        civicDamonBinder = new CivicDamonBinder();
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        super.unbindService(conn);
        startService(new Intent(this, CivicDoubleService.class));
    }

    private class CivicDamonBinder extends ICivicDamonAidlInterface.Stub{
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }
}
