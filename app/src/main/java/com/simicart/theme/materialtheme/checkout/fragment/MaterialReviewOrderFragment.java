package com.simicart.theme.materialtheme.checkout.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.block.MaterialReviewOrderBlock;
import com.simicart.theme.materialtheme.checkout.controller.MaterialReviewOrderController;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialReviewOrderFragment extends SimiFragment{
    protected MaterialReviewOrderController mController;
    protected MaterialReviewOrderBlock mBlock;

    public static MaterialReviewOrderFragment newInstance() {
        MaterialReviewOrderFragment fragment = new MaterialReviewOrderFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                Rconfig.getInstance().layout("material_cart_layout"), container, false);
        Context context = getActivity();
        mBlock = new MaterialReviewOrderBlock(rootView, context);
        mBlock.initView();
        if (mController == null) {
            mController = new MaterialReviewOrderController();
            mController.setDelegate(mBlock);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }
        return rootView;
    }

    @Override
    public void onDestroy() {
        SimiManager.getIntance().showCartLayout(true);
        super.onDestroy();
    }

}
