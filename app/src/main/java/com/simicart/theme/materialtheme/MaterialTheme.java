package com.simicart.theme.materialtheme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Constants;
import com.simicart.core.event.fragment.SimiEventFragmentEntity;
import com.simicart.theme.materialtheme.home.fragment.MaterialHomeFragment;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialTheme {
    public MaterialTheme()
    {
        IntentFilter filter = new IntentFilter("com.simicart.core.home.fragment.HomeFragment");
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getBundleExtra(Constants.DATA);
                SimiEventFragmentEntity entity = (SimiEventFragmentEntity) bundle.getSerializable(Constants.ENTITY);
                MaterialHomeFragment fragment = MaterialHomeFragment.newInstance();
                entity.setFragmetn(fragment);
            }
        };
        Context context = SimiManager.getIntance().getCurrentContext();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver,filter);
    }
}
