package saman.com.app;

import android.app.Application;
import android.content.Context;

import com.marswin89.marsdaemon.DaemonClient;
import com.marswin89.marsdaemon.DaemonConfigurations;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/9 10:39<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public class CoreApplication extends Application {
    private DaemonClient mDaemonClient ;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mDaemonClient = new DaemonClient(createDaemonConfigurations());
        mDaemonClient.onAttachBaseContext(base);
    }

    private DaemonConfigurations createDaemonConfigurations(){
        DaemonConfigurations.DaemonConfiguration configuration1 = new DaemonConfigurations.DaemonConfiguration(
                "com.marswin89.marsdaemon.demo:process1",
                Service1.class.getCanonicalName(),
                Receiver1.class.getCanonicalName());
        DaemonConfigurations.DaemonConfiguration configuration2 = new DaemonConfigurations.DaemonConfiguration(
                "com.marswin89.marsdaemon.demo:process2",
                Service2.class.getCanonicalName(),
                Receiver2.class.getCanonicalName());
        DaemonConfigurations.DaemonListener listener = new DaemonConfigurations.DaemonListener() {
            @Override
            public void onPersistentStart(Context context) {

            }

            @Override
            public void onDaemonAssistantStart(Context context) {

            }

            @Override
            public void onWatchDaemonDaed() {

            }
        };
        //return new DaemonConfigurations(configuration1, configuration2);//listener can be null
        return new DaemonConfigurations(configuration1, configuration2, listener);
    }
}
