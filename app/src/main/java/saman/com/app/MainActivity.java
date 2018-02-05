package saman.com.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import oms.mmc.civiclibrary.JobSchedulerManager;
import oms.mmc.civiclibrary.type.CivicTypeLessApi21;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JobSchedulerManager
                .getJobSchedulerInstance()
                .setTypeList(new CivicTypeLessApi21(this))
                .startJobScheduler();
    }
}
