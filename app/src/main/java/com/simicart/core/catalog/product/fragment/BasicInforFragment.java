package com.simicart.core.catalog.product.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.price.ProductDetailPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;

public class BasicInforFragment extends SimiFragment {
    protected ProductEntity mProduct;
    protected ProductDetailPriceView priceViewBasic;

    public void setPriceViewBasic(ProductDetailPriceView priceView) {
        priceViewBasic = priceView;
    }

    public static BasicInforFragment newInstance() {
        BasicInforFragment fragment = new BasicInforFragment();
        return fragment;
    }

    public void setProduct(ProductEntity product) {
        mProduct = product;
    }

    public ProductEntity getProduct() {
        return mProduct;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                Rconfig.getInstance().layout(
                        "core_information_basic_inf_layout"), container, false);

        TextView tv_Name = (TextView) rootView.findViewById(Rconfig
                .getInstance().id("tv_Name"));
        tv_Name.setText(mProduct.getName().trim());
        tv_Name.setTextColor(Config.getInstance().getContent_color());

        // price
        LinearLayout ll_price = (LinearLayout) rootView.findViewById(Rconfig
                .getInstance().id("ll_price"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        View view = onShowPriceView();
        if (null != view) {
            if (DataLocal.isLanguageRTL) {
                params.gravity = Gravity.RIGHT;
                ll_price.setGravity(Gravity.RIGHT);
            }

            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);

            ll_price.removeAllViewsInLayout();
            ll_price.addView(view, params);
        }

        TextView tv_Stock = (TextView) rootView.findViewById(Rconfig
                .getInstance().id("tv_Stock"));
        tv_Stock.setTextColor(Config.getInstance().getContent_color());
        tv_Stock.setText(Config.getInstance().getText("In Stock") + ".");

        TextView tv_shortDescription = (TextView) rootView.findViewById(Rconfig
                .getInstance().id("tv_descrip"));
        tv_Name.setTextColor(Config.getInstance().getContent_color());
        if (mProduct.getShortDescription() != null
                && !mProduct.getShortDescription().toLowerCase().equals("null")) {
            tv_shortDescription.setText(Html.fromHtml("<font color='"
                    + Config.getInstance().getContent_color_string() + "'>"
                    + mProduct.getShortDescription() + "</font>"));
        }

        SimiCollection simiCollection = new SimiCollection();
        simiCollection.setJSON(mProduct.getJSONObject());
        Intent intent = new Intent("com.simicart.core.catalog.fragment.BasicInforFragment");
        SimiEventBlockEntity blockEntity = new SimiEventBlockEntity();
        blockEntity.setSimiCollection(simiCollection);
        blockEntity.setView(rootView);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, blockEntity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);


        rootView.setBackgroundColor(Config.getInstance().getApp_backrground());

        return rootView;
    }

    protected View onShowPriceView() {

        if (null == priceViewBasic) {
            Log.e("BasicInforFragment ", "onShowPriceView NULL");
            return null;
        }

        Log.e("BasicInforFragment ", "onShowPriceView ");


        return priceViewBasic.getView();


    }
}
