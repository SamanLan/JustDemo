package saman.com.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;

import oms.mmc.civiclibrary.service.CivicDoubleService;
import oms.mmc.civiclibrary.util.AbstractWeakHandler;

import static oms.mmc.civiclibrary.CivicManager.CIVIC_AVTION;

public class Service1 extends Service {
    private ServiceHandler handler = new ServiceHandler(this);
    public Service1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    private static class ServiceHandler extends AbstractWeakHandler<Service> {

        private Intent intent;

        public ServiceHandler(Service service) {
            super(service);
            intent = new Intent();
            intent.setAction(CIVIC_AVTION);
        }

        @Override
        public void handleMessage(Message msg, @NonNull Service service) {
            service.sendBroadcast(intent);
            sendEmptyMessageDelayed(0, 1000);
        }
    }
}
