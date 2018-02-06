package oms.mmc.civiclibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/6 09:27<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public class CivicUtil {
    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
