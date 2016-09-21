package com.simicart.theme.matrixtheme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.theme.matrixtheme.home.fragment.HomeTheme1Fragment;

public class MatrixTheme {

    public MatrixTheme() {
        IntentFilter filter = new IntentFilter("com.simicart.core.home.fragment.HomeFragment.matrix");
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);
                HomeTheme1Fragment fragment = HomeTheme1Fragment.newInstance();
                entity.setFragmetn(fragment);
            }
        };
        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

}
