package oms.mmc.civiclibrary.type;

import android.content.Context;
import android.content.Intent;

import oms.mmc.civiclibrary.service.CivicDoubleService;

/**
 * Author：zixin on 2018/2/5 14:23
 * E-mail：lanshenming@linghit.com
 */

public class CivicTypeLessApi21 implements ICivicType{
    private Context context;

    public CivicTypeLessApi21(Context context) {
        this.context = context;
    }

    @Override
    public String getName() {
        return "思域保活模式--小于API21";
    }

    @Override
    public void start() {
        context.startService(new Intent(context, CivicDoubleService.class));
    }

    @Override
    public void stop() {

    }
}
