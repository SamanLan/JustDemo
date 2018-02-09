package saman.com.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class Service2 extends Service {
    public Service2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}
