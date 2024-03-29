package com.simicart.core.slidemenu.controller;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.category.fragment.CategoryFragment;
import com.simicart.core.cms.entity.Cms;
import com.simicart.core.cms.fragment.CMSFragment;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.fragment.MyAccountFragment;
import com.simicart.core.customer.fragment.OrderHistoryFragment;
import com.simicart.core.customer.fragment.SignInFragment;
import com.simicart.core.event.slidemenu.EventSlideMenu;
import com.simicart.core.event.slidemenu.SlideMenuData;
import com.simicart.core.home.fragment.HomeFragment;
import com.simicart.core.setting.fragment.SettingAppFragment;
import com.simicart.core.slidemenu.adapter.TabSlideMenuAdapter;
import com.simicart.core.slidemenu.delegate.CloseSlideMenuDelegate;
import com.simicart.core.slidemenu.delegate.SlideMenuDelegate;
import com.simicart.core.slidemenu.entity.ItemNavigation;
import com.simicart.core.slidemenu.entity.ItemNavigation.TypeItem;
import com.simicart.core.style.PagerSlidingTabStrip;

public class PhoneSlideMenuController {

    private final String HOME = "Home";
    private final String CATEGORY = "Category";
    private final String ORDER_HISTORY = "Order History";
    private final String MORE = "More";
    private final String SETTING = "Setting";
    protected HashMap<String, String> mPluginFragment;
    protected OnItemClickListener mListener;
    protected OnClickListener onClickPersonal;
    protected ArrayList<ItemNavigation> mItems = new ArrayList<ItemNavigation>();
    protected Context mContext;
    protected SlideMenuDelegate mDelegate;
    protected int DEFAULT_POSITION = 0;
    protected CloseSlideMenuDelegate mCloseDelegate;
    private boolean check_keyboard_first;
    protected TabSlideMenuAdapter tabSlideMenuAdapter;
    protected ViewPager mViewPager;
    protected ArrayList<SimiFragment> mListFragment;
    protected FragmentManager fmanager;
    PagerSlidingTabStrip titletab;

    public void setTabSlideMenuAdapter(TabSlideMenuAdapter tabSlideMenuAdapter) {
        this.tabSlideMenuAdapter = tabSlideMenuAdapter;
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    public void setListFragment(ArrayList<SimiFragment> mListFragment) {
        this.mListFragment = mListFragment;
    }

    public void setFmanager(FragmentManager fmanager) {
        this.fmanager = fmanager;
    }

    public void setTitletab(PagerSlidingTabStrip titletab) {
        this.titletab = titletab;
    }

    public void setCloseDelegate(CloseSlideMenuDelegate delegate) {
        mCloseDelegate = delegate;
    }

    public OnItemClickListener getListener() {
        return mListener;
    }

    public void setListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public OnClickListener getOnClickPersonal() {
        return onClickPersonal;
    }

    public void closeSlideMenuTablet() {
        mCloseDelegate.closeSlideMenu();
    }

    public PhoneSlideMenuController(SlideMenuDelegate delegate, Context context) {
        mDelegate = delegate;
        mContext = context;
        mItems = new ArrayList<ItemNavigation>();
        mPluginFragment = new HashMap<String, String>();
    }

    public void create() {
        mListener = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onNaviagte(position);
            }

        };

        onClickPersonal = new OnClickListener() {

            @Override
            public void onClick(View v) {
                SimiFragment fragment = null;
                mCloseDelegate.closeSlideMenu();
                if (DataLocal.isSignInComplete()) {
                    // profile
                    fragment = MyAccountFragment.newInstance();
                } else {
                    // sign in
                    fragment = SignInFragment.newInstance();
                }
                SimiManager.getIntance().replacePopupFragment(fragment);
            }
        };
        initial();
    }

    private void initial() {
        initDataAdapter();
        if (null != mDelegate) {
            mDelegate.setAdapter(mItems);
        }
        // open home page
        onNaviagte(DEFAULT_POSITION);
    }

    public void initDataAdapter() {
        addPersonal();
        addHome();
        addCategory();
        if (DataLocal.isSignInComplete()) {
            addItemRelatedPersonal();
        }
        addMoreItems();
        addSetting();

    }

    public void addPersonal() {

        String name = "";

        if (DataLocal.isSignInComplete()) {
            name = DataLocal.getUsername();
        } else {
            name = Config.getInstance().getText("Sign in");
            removeItemRelatedPersonal();
        }
        mDelegate.setUpdateSignIn(name);
        mDelegate.setAdapter(mItems);
    }

    public void addItemRelatedPersonal() {

        // order history
        int index = checkElement(ORDER_HISTORY);
        ArrayList<ItemNavigation> mItemsAccount = new ArrayList<>();
        if (index == -1) {
            ItemNavigation item = new ItemNavigation();
            item.setType(TypeItem.NORMAL);
            item.setName(ORDER_HISTORY);
            int id_icon = Rconfig.getInstance().drawable(
                    "ic_menu_order_history");
            Drawable icon = mContext.getResources().getDrawable(id_icon);
            icon.setColorFilter(Color.parseColor("#ffffff"),
                    PorterDuff.Mode.SRC_ATOP);
            item.setIcon(icon);
            mItemsAccount.add(item);

            int index_category = checkElement(CATEGORY);
            if (DataLocal.isTablet) {
                index_category = checkElement(HOME);
            }
            insertItemAfter(index_category, mItemsAccount);

        }

    }

    public void removeItemRelatedPersonal() {

        // order history
        int index = checkElement(ORDER_HISTORY);
        if (index != -1) {
            mItems.remove(index);
        }
    }

    public void addHome() {
        int index = checkElement(HOME);
        if (index == -1) {
            ItemNavigation item = new ItemNavigation();
            item.setType(TypeItem.NORMAL);
            item.setName(HOME);
            int id_icon = Rconfig.getInstance().drawable("ic_menu_home");
            Drawable icon = mContext.getResources().getDrawable(id_icon);
            icon.setColorFilter(Color.parseColor("#ffffff"),
                    PorterDuff.Mode.SRC_ATOP);
            item.setIcon(icon);
            mItems.add(item);
        }
    }

    public void addCategory() {
        if (!DataLocal.isTablet) {
            int index = checkElement(CATEGORY);
            if (index == -1) {
                ItemNavigation item = new ItemNavigation();
                item.setType(TypeItem.NORMAL);
                item.setName(CATEGORY);
                int id_icon = Rconfig.getInstance()
                        .drawable("ic_menu_category");
                Drawable icon = mContext.getResources().getDrawable(id_icon);
                icon.setColorFilter(Color.parseColor("#ffffff"),
                        PorterDuff.Mode.SRC_ATOP);
                item.setIcon(icon);

                mItems.add(item);
            }
        }
    }

    public void addMoreItems() {
        // more
        ItemNavigation item = new ItemNavigation();
        item.setType(TypeItem.NORMAL);
        item.setSparator(true);
        item.setName(MORE);
        mItems.add(item);

        SlideMenuData slideMenuData = new SlideMenuData();
        slideMenuData.setItemNavigations(mItems);
        slideMenuData.setPluginFragment(mPluginFragment);

        Intent intent = new Intent("com.simicart.add.navigation.more.qrbarcode");
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, slideMenuData);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(mContext).sendBroadcastSync(intent);

        // CMS
        addCMS();
    }

    public void addCMS() {
        if ((null != DataLocal.listCms) && (DataLocal.listCms.size() > 0)) {
            for (Cms cms : DataLocal.listCms) {
                ItemNavigation item = new ItemNavigation();
                String name = cms.getTitle();
                if (null != name && !name.equals("null")) {
                    item.setName(name);
                }
                String url = cms.getIconURL();
                if (null != url && !url.equals("null")) {
                    item.setUrl(url);
                }
                item.setType(TypeItem.CMS);
                mItems.add(item);
            }
        }
    }

    public void addSetting() {
        ItemNavigation item = new ItemNavigation();
        item.setExtended(true);
        item.setType(TypeItem.NORMAL);
        item.setName(SETTING);
        int id_icon = Rconfig.getInstance().drawable("ic_menu_setting");
        Drawable icon = mContext.getResources().getDrawable(id_icon);
        icon.setColorFilter(Color.parseColor("#ffffff"),
                PorterDuff.Mode.SRC_ATOP);
        item.setIcon(icon);

        mItems.add(item);
    }

    public void onNaviagte(int position) {
        SimiManager.getIntance().showCartLayout(true);
        ItemNavigation item = mItems.get(position);
        if (null != item) {
            if (!item.isSparator()) {
                String nameItem = item.getName();

                Intent intent = new Intent("com.simicart.leftmenu.slidemenucontroller.onnavigate.clickitem");
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ENTITY, nameItem);
                intent.putExtra(Constants.DATA, bundle);
                LocalBroadcastManager.getInstance(mContext).sendBroadcastSync(intent);

                TypeItem type = item.getType();
                SimiFragment fragment = null;
                if (type == TypeItem.NORMAL) {
                    fragment = navigateNormal(item);
                } else if (type == TypeItem.PLUGIN) {
                    fragment = navigatePlugin(item);
                    fragment.setShowPopup(item.isShowPopup());
                } else if (type == TypeItem.CMS) {
                    fragment = navigateCMS(item);
                }
                if (null != fragment) {
                    if (!DataLocal.isTablet) {
                        // replace fragment for phone
                        SimiManager.getIntance().replaceFragment(fragment);
                        if (check_keyboard_first == true) {
                            SimiManager.getIntance().hideKeyboard();
                        }
                        check_keyboard_first = true;
                    } else {
                        // replace for tablet
                        if (fragment.isShowPopup()) {
                            SimiManager.getIntance().replacePopupFragment(
                                    fragment);
                        } else {
                            SimiManager.getIntance().replaceFragment(fragment);
                        }
                    }

                }
                mDelegate.onSelectedItem(position);
                if (mCloseDelegate != null) {
                    mCloseDelegate.closeSlideMenu();
                }
            }
        }
    }

    public SimiFragment navigateNormal(ItemNavigation item) {
        SimiFragment fragment = null;
        String name = item.getName();

        switch (name) {
            case "Home":
                fragment = HomeFragment.newInstance();
                break;
            case "Category":
                fragment = CategoryFragment.newInstance("all categories", "-1");
                break;
            case "Order History":
                fragment = OrderHistoryFragment.newInstance();
                break;
            case "Setting":
                fragment = SettingAppFragment.newInstance();
                fragment.setShowPopup(true);
                break;
            default:
                break;
        }

        return fragment;
    }

    public SimiFragment navigatePlugin(ItemNavigation item) {
        SimiFragment fragment = null;
        String name = item.getName();
        if (null != name) {
            for (String key : mPluginFragment.keySet()) {
                if (name.contains(key)) {
                    String fullname = mPluginFragment.get(key);
                    fragment = (SimiFragment) Fragment.instantiate(mContext,
                            fullname);
                }
            }
        }
        return fragment;
    }

    public SimiFragment navigateCMS(ItemNavigation item) {

        SimiFragment fragment = null;
        String name = item.getName();
        for (Cms cms : DataLocal.listCms) {
            if (name.equals(cms.getTitle())) {
                String content = cms.getContent();
                fragment = CMSFragment.newInstance();
                ((CMSFragment) fragment).setContent(content);
            }
        }
        // initial CMSFragment by using content field.

        return fragment;
    }

    protected int checkElement(String name) {
        if (null != mItems && mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                ItemNavigation item = mItems.get(i);
                if (item.getName().equals(name)) {
                    return i;
                }
            }
            return -1;
        }
        return -1;
    }

    protected ItemNavigation getElement(String name) {
        if (null != mItems || mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                ItemNavigation item = mItems.get(i);
                if (item.getName().equals(name)) {
                    return item;
                }
            }
            return null;
        }
        return null;
    }

    public boolean removeItemElement(String name) {
        int position = checkElement(name);
        if (position > -1) {
            mItems.remove(position);
            return true;
        }

        return false;
    }

    public boolean replaceItemElement(String nameOldElement,
                                      ItemNavigation newItem) {
        int position = checkElement(nameOldElement);
        if (position > -1) {
            mItems.set(position, newItem);
            return true;
        }

        return false;
    }

    public boolean insertItemAfter(int index,
                                   ArrayList<ItemNavigation> mItemsAccount) {
        if (index != -1 && mItemsAccount.size() > 0) {
            ArrayList<ItemNavigation> list1 = new ArrayList<ItemNavigation>();
            ArrayList<ItemNavigation> list2 = new ArrayList<ItemNavigation>();

            for (int i = 0; i <= index; i++) {
                list1.add(mItems.get(i));
            }

            for (int i = index + 1; i < mItems.size(); i++) {
                list2.add(mItems.get(i));
            }

            // if (list1.size() == 0 || list2.size() == 0) {
            // return false;
            // }

            mItems.clear();

            for (int i = 0; i < list1.size(); i++) {
                mItems.add(list1.get(i));
            }
            for (ItemNavigation itemNavigation : mItemsAccount) {
                mItems.add(itemNavigation);
            }
            for (int i = 0; i < list2.size(); i++) {
                mItems.add(list2.get(i));
            }

            return true;

        }

        return false;
    }

    public void updateSignIn() {
        String name = Config.getInstance().getText("My Account");

        if (DataLocal.isSignInComplete()) {
            name = DataLocal.getUsername();
            addItemRelatedPersonal();
        } else {
            name = Config.getInstance().getText("Sign in");
            removeItemRelatedPersonal();
        }
        mDelegate.setUpdateSignIn(name);
        mDelegate.setAdapter(mItems);
    }

    public void notifiChangeAdapterSlideMenu() {
        if (DataLocal.isTablet) {
            if (mViewPager != null) {
                TabSlideMenuAdapter tabSlideMenuAdapter = new TabSlideMenuAdapter(fmanager, mListFragment);
                mViewPager.setAdapter(tabSlideMenuAdapter);
                titletab.setViewPager(mViewPager);
            }
        }
        mDelegate.notifiChangeAdapter();
    }

}
