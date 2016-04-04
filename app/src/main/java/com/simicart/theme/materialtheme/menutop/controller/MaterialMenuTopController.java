package com.simicart.theme.materialtheme.menutop.controller;

import android.support.v7.widget.SearchView;
import android.view.MotionEvent;
import android.view.View;
import com.mikepenz.materialdrawer.Drawer;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.fragment.CartFragment;
import com.simicart.core.menutop.controller.MenuTopController;
import com.simicart.theme.materialtheme.menutop.delegate.MaterialMenuTopDelegate;

/**
 * Created by Sony on 3/27/2016.
 */
public class MaterialMenuTopController extends MenuTopController {
    protected View.OnTouchListener mTouchCart;
    protected View.OnTouchListener mTouchMenu;
    protected View.OnClickListener mOnClickSearch;
    protected SearchView.OnQueryTextListener mSearchListener;
    protected Drawer mDrawer;
    protected MaterialMenuTopDelegate mDelegate;

    public void setDelegate(MaterialMenuTopDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        init();
    }

    @Override
    public void onResume() {

    }

    public void init() {
        mTouchCart = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        mDelegate.updateBackground(0x80CACACA);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
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

        mTouchMenu = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(0x80CACACA);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
//					mNavigationDrawerFragment.openMenu();
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


        mOnClickSearch = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    public void updateCartQty(String qty) {
        mDelegate.updateCartQty(qty);
    }

    private void clickCart() {
        SimiFragment fragment = null;
        // initial fragment cart
        fragment = CartFragment.newInstance();
        if (null != fragment) {
            SimiManager.getIntance().replaceFragment(fragment);
        }
    }

    public View.OnTouchListener getTouchCart() {
        return mTouchCart;
    }

    public View.OnClickListener getOnClickSearch() {
        return mOnClickSearch;
    }

    public View.OnTouchListener getTouchMenu() {
        return mTouchMenu;
    }

    public void setDrawer(Drawer drawer) {
        mDrawer = drawer;
    }

    public void showCartLayout(boolean show) {
        mDelegate.showCartLayout(show);
    }


    public void hideMenuTop(boolean isHiden) {
        mDelegate.hideMenuTop(isHiden);
    }
}
