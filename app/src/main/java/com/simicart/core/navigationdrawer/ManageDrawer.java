package com.simicart.core.navigationdrawer;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.mikepenz.iconics.typeface.IIcon;
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
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.cms.entity.Cms;
import com.simicart.core.cms.fragment.CMSFragment;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.fragment.MyAccountFragment;
import com.simicart.core.customer.fragment.OrderHistoryFragment;
import com.simicart.core.customer.fragment.SignInFragment;
import com.simicart.core.home.fragment.HomeFragment;
import com.simicart.core.setting.fragment.SettingAppFragment;

import java.util.ArrayList;

/**
 * Created by MSI on 26/03/2016.
 */
public class ManageDrawer {

    protected Drawer mDrawer;
    protected Activity mContext;

    protected long HOME = 1;
    protected long CATEGORY = 2;
    protected long CMS = 3;
    protected long SETTING = 4;
    protected long ORDER_HISTORY = 5;

    protected AccountHeader headerResult;
    protected ProfileDrawerItem profileItem;


    public ManageDrawer() {
        mContext = SimiManager.getIntance().getCurrentActivity();
    }

    public Drawer initDrawer() {

        headerResult = createPersonal();
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

    private AccountHeader createPersonal() {

        profileItem = new ProfileDrawerItem().withIcon(R.drawable.ic_acc_profile);

        if (DataLocal.isSignInComplete()) {
            String name = DataLocal.getUsername();
            if (Utils.validateString(name)) {
                profileItem.withName(name);
            }
            String email = DataLocal.getEmail();
            if (Utils.validateString(email)) {
                profileItem.withEmail(email);
            }
        } else {
            String msg = "Signin";
            profileItem.withName(msg);

        }

        AccountHeader.OnAccountHeaderListener accountListener = new AccountHeader.OnAccountHeaderListener() {
            @Override
            public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                SimiFragment fragment;
                if (DataLocal.isSignInComplete()) {
                    // profile
                    fragment = MyAccountFragment.newInstance();
                } else {
                    // sign in
                    fragment = SignInFragment.newInstance();
                }
                SimiManager.getIntance().replacePopupFragment(fragment);

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

    public void createData() {
        Drawable ic_home = mContext.getResources().getDrawable(R.drawable.ic_menu_home);
        ic_home.setColorFilter(Color.parseColor("#000000"),
                PorterDuff.Mode.SRC_ATOP);
        SecondaryDrawerItem item_home = new SecondaryDrawerItem().withName("Home").withIcon(ic_home);
        item_home.withIdentifier(HOME);


        Drawable ic_cate = mContext.getResources().getDrawable(R.drawable.ic_menu_category);
        ic_cate.setColorFilter(Color.parseColor("#000000"),
                PorterDuff.Mode.SRC_ATOP);
        SecondaryDrawerItem item_category = new SecondaryDrawerItem().withName("Category").withIcon(ic_cate);
        item_category.withIdentifier(CATEGORY);


        mDrawer.addItem(item_home);
        mDrawer.addItem(item_category);

        addMoreItem();
        addSettingItem();


        mDrawer.setSelection(1);


        SimiManager.getIntance().setManageDrawer(this);

    }

    private void addMoreItem() {
        ArrayList<Cms> listCMS = DataLocal.listCms;
        if (null != listCMS && listCMS.size() > 0) {

            mDrawer.addItem(new DividerDrawerItem());
            SecondaryDrawerItem item_more = new SecondaryDrawerItem();
            item_more.withName("MORE");
            item_more.withEnabled(false);
            mDrawer.addItem(item_more);

            for (int i = 0; i < listCMS.size(); i++) {
                Cms cms = listCMS.get(i);
                drawCMSItem(cms);
            }
        }
    }

    private void addSettingItem() {
        SecondaryDrawerItem item_setting = new SecondaryDrawerItem();
        item_setting.withName("Setting");
        Drawable ic_settting = mContext.getResources().getDrawable(R.drawable.ic_menu_setting);
        ic_settting.setColorFilter(Color.parseColor("#000000"),
                PorterDuff.Mode.SRC_ATOP);
        item_setting.withIcon(ic_settting);
        item_setting.withIdentifier(SETTING);
        mDrawer.addItem(item_setting);
    }

    private void drawCMSItem(Cms cms) {
        final String name = cms.getTitle();
        final SecondaryDrawerItem item_cms = new SecondaryDrawerItem();
        item_cms.withName(name);
        item_cms.withIdentifier(CMS);


        mDrawer.addItem(item_cms);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                Bitmap bitmap = (Bitmap) message.obj;
                Resources resources = SimiManager.getIntance()
                        .getCurrentContext().getResources();
                if (bitmap != null) {
                    Drawable d = new BitmapDrawable(resources, bitmap);
                    item_cms.withIcon(d);
                    mDrawer.updateItem(item_cms);
                } else {
                    bitmap = BitmapFactory.decodeResource(resources, Rconfig
                            .getInstance().drawable("default_icon"));
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);
                    Drawable d = new BitmapDrawable(resources, bitmap);
                    item_cms.withIcon(d);
                    mDrawer.updateItem(item_cms);
                }

            }
        };

        String urlString = cms.getIconURL();

        DrawableManager.getBitmap(handler, urlString, 0);
    }


    private boolean onItemSelected(int position, IDrawerItem drawerItem) {
        SecondaryDrawerItem item = (SecondaryDrawerItem) drawerItem;

        long id = item.getIdentifier();
        if (HOME == id) {
            HomeFragment fragment = new HomeFragment().newInstance();
            SimiManager.getIntance().replaceFragment(fragment);
            return false;
        } else if (CATEGORY == id) {
            CategoryFragment fragment = CategoryFragment.newInstance("all categories", "-1");
            SimiManager.getIntance().replaceFragment(fragment);
            return false;
        } else if (ORDER_HISTORY == id) {
            OrderHistoryFragment fragment = OrderHistoryFragment.newInstance();
            SimiManager.getIntance().replaceFragment(fragment);
            return false;
        } else if (SETTING == id) {
            SettingAppFragment fragment = SettingAppFragment.newInstance();
            fragment.setShowPopup(true);
            SimiManager.getIntance().replaceFragment(fragment);
            return false;
        } else if (CMS == id) {
            String name = item.getName().getText();
            for (Cms cms : DataLocal.listCms) {
                if (name.equals(cms.getTitle())) {
                    String content = cms.getContent();
                    CMSFragment fragment = CMSFragment.newInstance();
                    fragment.setContent(content);
                    SimiManager.getIntance().replaceFragment(fragment);
                    return false;
                }
            }
        }

        return true;
    }

    public void updateSignIn() {
        if (null == profileItem) {
            profileItem = new ProfileDrawerItem().withIcon(R.drawable.ic_acc_profile);
        }
        if (DataLocal.isSignInComplete()) {
            String name = DataLocal.getUsername();
            if (Utils.validateString(name)) {
                profileItem.withName(name);
            }
            String email = DataLocal.getEmail();
            if (Utils.validateString(email)) {
                profileItem.withEmail(email);
            }
            addItemForPersonal();
        } else {
            String msg = "SignIn";
            profileItem.withName(msg);
            profileItem.withEmail(null);
            removeItemForpersonal();
        }


        headerResult.updateProfile(profileItem);
    }

    private void addItemForPersonal() {
        int pos_cate = mDrawer.getPosition(CATEGORY);

        SecondaryDrawerItem item_history = new SecondaryDrawerItem();
        item_history.withName("Order History");
        item_history.withIdentifier(ORDER_HISTORY);
        Drawable ic_order = mContext.getResources().getDrawable(R.drawable.ic_menu_order_history);
        ic_order.setColorFilter(Color.parseColor("#000000"),
                PorterDuff.Mode.SRC_ATOP);
        item_history.withIcon(ic_order);
        mDrawer.addItemAtPosition(item_history, pos_cate + 1);
    }

    private void removeItemForpersonal() {
        mDrawer.removeItem(ORDER_HISTORY);

    }


}
