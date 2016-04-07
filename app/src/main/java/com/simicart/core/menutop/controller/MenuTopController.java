package com.simicart.core.menutop.controller;

import android.annotation.SuppressLint;
import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.mikepenz.materialdrawer.Drawer;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.checkout.fragment.CartFragment;
import com.simicart.core.common.Utils;
import com.simicart.core.menutop.delegate.MenuTopDelegate;
import com.simicart.core.slidemenu.fragment.SlideMenuFragment;

public class MenuTopController extends SimiController {
    protected OnTouchListener mTouchCart;
    protected OnTouchListener mTouchMenu;
    protected SearchView.OnQueryTextListener mSearchListener;
    protected MenuTopDelegate mDelegate;
    //	protected SlideMenuFragment mNavigationDrawerFragment;
    protected Drawer mDrawer;

    public void setDelegate(MenuTopDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        init();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

    }

    public void init() {
        mTouchCart = new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        mDelegate.updateBackground(0x80CACACA);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        Utils.hideKeyboard(v);
                        clickCart();
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        mDelegate.updateBackground(0);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        };

        mTouchMenu = new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(0x80CACACA);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
//					mNavigationDrawerFragment.openMenu();
                        Utils.hideKeyboard(v);
                        mDrawer.openDrawer();
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        v.setBackgroundColor(0);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        };
    }

    public void updateCartQty(String qty) {
        mDelegate.updateCartQty(qty);
    }

    public void showSearch(boolean show) {
        if(show == true) {
            mDelegate.showSearchView();
        } else {
            mDelegate.hideSearchView();
        }
    }

    public void querySearch(String query) {
        mDelegate.showSearchScreen(query);
    }

    public void refreshSearchField() {
        mDelegate.clearSearchField();
    }

    private void clickCart() {
        SimiFragment fragment = null;
        // initial fragment cart
        fragment = CartFragment.newInstance();
        if (null != fragment) {
            SimiManager.getIntance().replaceFragment(fragment);
        }
    }

    public OnTouchListener getTouchCart() {
        return mTouchCart;
    }


    public OnTouchListener getTouchMenu() {
        return mTouchMenu;
    }

    public void setDrawer(Drawer drawer)
    {
        mDrawer = drawer;
    }

    public void setSlideMenu(SlideMenuFragment mNavigationDrawerFragment) {
//		this.mNavigationDrawerFragment = mNavigationDrawerFragment;

    }

    public void showCartLayout(boolean show) {
        mDelegate.showCartLayout(show);
    }


    public void hideMenuTop(boolean isHiden) {
        mDelegate.hideMenuTop(isHiden);
    }
}
