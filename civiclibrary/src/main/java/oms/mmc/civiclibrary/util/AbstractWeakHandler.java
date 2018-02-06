package oms.mmc.civiclibrary.util;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/6 09:30<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b> <br>
 */

public abstract class AbstractWeakHandler<T> extends Handler {

    private WeakReference<T> weakReference;

    public AbstractWeakHandler(T t) {
        weakReference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (weakReference != null && weakReference.get() != null) {
            handleMessage(msg, weakReference.get());
        }
    }

    public abstract void handleMessage(Message msg, @NonNull T t);
}
