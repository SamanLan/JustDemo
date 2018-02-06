package oms.mmc.civiclibrary.type;

import android.content.Context;
import android.content.Intent;

import oms.mmc.civiclibrary.service.CivicDoubleService;

/**
 * <b>Project:</b> ${file_name}<br>
 * <b>Create Date:</b> 2018/2/5 09:50<br>
 * <b>Author:</b> zixin<br>
 * <b>Description:</b>7.0以下使用
 */

public class CivicTypeLessApi24 implements ICivicType{
    private Context context;

    public CivicTypeLessApi24(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public String getName() {
        return "思域保活模式--小于API24";
    }

    @Override
    public void start() {
        context.startService(new Intent(context, CivicDoubleService.class));
    }

    @Override
    public void stop() {

    }
}
