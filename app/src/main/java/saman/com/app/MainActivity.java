package saman.com.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import oms.mmc.civiclibrary.CivicManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CivicManager
                .getJobSchedulerInstance()
                .startNormal(this);
    }
}
