package com.simicart.theme.ztheme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.theme.ztheme.home.fragment.HomeZThemeFragment;
import com.simicart.theme.ztheme.home.fragment.HomeZThemeFragmentTablet;

public class ZTheme {


    public ZTheme() {
        Context context = SimiManager.getIntance().getCurrentContext();
        IntentFilter intentFilter = new IntentFilter("com.simicart.core.home.fragment.HomeFragment");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);
                HomeZThemeFragment fragment = HomeZThemeFragment.newInstance();
                entity.setFragmetn(fragment);
            }
        };

        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, intentFilter);
    }
}
