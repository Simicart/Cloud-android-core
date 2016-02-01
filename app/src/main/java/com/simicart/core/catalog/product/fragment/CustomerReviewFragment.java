package com.simicart.core.catalog.product.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.catalog.product.block.CustomerReviewBlock;
import com.simicart.core.catalog.product.controller.CustomerReviewController;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;

public class CustomerReviewFragment extends SimiFragment {

    protected ArrayList<Integer> mRatingStar;
    protected String mID;
    protected CustomerReviewBlock mBlock = null;
    protected CustomerReviewController mController = null;

    public static CustomerReviewFragment newInstance() {
        CustomerReviewFragment fragment = new CustomerReviewFragment();
        return fragment;
    }

    public void setRatingStar(ArrayList<Integer> stars) {
        mRatingStar = stars;
    }

    public void setProductID(String id) {
        mID = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
                Rconfig.getInstance().layout("core_information_customer_review_layout"),
                container, false);
        Context context = getActivity();
        mBlock = new CustomerReviewBlock(view, context);

        // event
        Intent intent = new Intent("com.simicart.core.catalog.product.block.CustomerReviewBlock");
        SimiEventBlockEntity blockEntity = new SimiEventBlockEntity();
        blockEntity.setBlock(mBlock);
        blockEntity.setView(view);
        blockEntity.setContext(context);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, blockEntity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


        mBlock = (CustomerReviewBlock) blockEntity.getBlock();

        mBlock.initView();
        if (mController == null) {
            mController = new CustomerReviewController();
            mController.setProductId(mID);
            mController.setDelegate(mBlock);
            mController.setRatingStar(mRatingStar);
            mController.onStart();
        } else {
            mController.setDelegate(mBlock);
            mController.onResume();
        }

        mBlock.setonScroll(mController.getScroller());

        return view;

    }

}
