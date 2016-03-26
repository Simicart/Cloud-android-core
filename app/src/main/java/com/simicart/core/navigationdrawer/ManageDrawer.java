package com.simicart.core.navigationdrawer;

import android.app.Activity;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.simicart.core.base.manager.SimiManager;

/**
 * Created by MSI on 26/03/2016.
 */
public class ManageDrawer {

    protected Drawer mDrawer;
    protected Activity mContext;

    public ManageDrawer() {
        mContext = SimiManager.getIntance().getCurrentActivity();
    }

    public Drawer initDrawer() {
        mDrawer = new DrawerBuilder().withActivity(mContext).build();
        return mDrawer;
    }
}
