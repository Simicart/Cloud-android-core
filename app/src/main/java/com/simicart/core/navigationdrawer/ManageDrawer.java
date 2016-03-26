package com.simicart.core.navigationdrawer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.simicart.R;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.common.Utils;
import com.simicart.core.home.fragment.HomeFragment;

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

        AccountHeader headerResult = createPersonal();
        Drawer.OnDrawerItemClickListener itemListener = createItemListener();

        mDrawer = new DrawerBuilder().withActivity(mContext).withAccountHeader(headerResult).withOnDrawerItemClickListener(itemListener).build();
        return mDrawer;
    }


    private Drawer.OnDrawerItemClickListener createItemListener() {
        return new Drawer.OnDrawerItemClickListener() {
            @Override
            public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                return onItemSelected(position, drawerItem);

            }
        };
    }

    public void createData() {
        Drawable ic_home = mContext.getResources().getDrawable(R.drawable.ic_menu_home);
        ic_home.setColorFilter(Color.parseColor("#000000"),
                PorterDuff.Mode.SRC_ATOP);
        SecondaryDrawerItem item_home = new SecondaryDrawerItem().withName("Home").withIcon(ic_home);


        Drawable ic_cate = mContext.getResources().getDrawable(R.drawable.ic_menu_category);
        ic_cate.setColorFilter(Color.parseColor("#000000"),
                PorterDuff.Mode.SRC_ATOP);
        SecondaryDrawerItem item_category = new SecondaryDrawerItem().withName("Category").withIcon(ic_cate);


        mDrawer.addItem(item_home);
        mDrawer.addItem(item_category);

        mDrawer.setSelection(1);
    }


    private AccountHeader createPersonal() {

        ProfileDrawerItem profileItem = new ProfileDrawerItem().withName("Mike Penz").withEmail("mikepenz@gmail.com").withIcon(R.drawable.ic_acc_profile);
        AccountHeader.OnAccountHeaderListener accountListener = new AccountHeader.OnAccountHeaderListener() {
            @Override
            public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                return false;
            }
        };
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(mContext)
                .withHeaderBackground(R.drawable.bg_personal_navigation)
                .addProfiles(profileItem).withOnAccountHeaderListener(accountListener)
                .build();

        return headerResult;
    }

    private boolean onItemSelected(int position, IDrawerItem drawerItem) {
        SecondaryDrawerItem item = (SecondaryDrawerItem) drawerItem;
        String name = item.getName().getText();
        if (Utils.validateString(name)) {
            if (name.equals("Home")) {
                HomeFragment fragment = new HomeFragment().newInstance();
                SimiManager.getIntance().replaceFragment(fragment);
            }
        }

        return false;
    }


}
