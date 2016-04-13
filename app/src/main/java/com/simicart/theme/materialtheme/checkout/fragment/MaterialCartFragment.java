package com.simicart.theme.materialtheme.checkout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paypal.android.sdk.co;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.block.MaterialCartBlock;
import com.simicart.theme.materialtheme.checkout.controller.MaterialCartController;

/**
 * Created by Sony on 4/11/2016.
 */
public class MaterialCartFragment extends SimiFragment {
    protected MaterialCartBlock mBlock;
    protected MaterialCartController mController;

    public static MaterialCartFragment newInstance() {
        MaterialCartFragment fragment = new MaterialCartFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimiManager.getIntance().showCartLayout(false);
    }

    @Override
    public void onStart() {
        SimiManager.getIntance().showCartLayout(false);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_cart_layout"), container, false);
        Context context = getActivity();
        mBlock = new MaterialCartBlock(rootView, context);
        mBlock.initView();
        if (mController == null) {
            mController = new MaterialCartController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        mBlock.setCheckoutClicker(mController.getCartListener());
        return rootView;
    }

    @Override
    public void onDestroy() {
        SimiManager.getIntance().showCartLayout(true);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        SimiManager.getIntance().showCartLayout(true);
    }

    @Override
    public void onResume() {
        SimiManager.getIntance().showCartLayout(false);
        super.onResume();
    }
}
