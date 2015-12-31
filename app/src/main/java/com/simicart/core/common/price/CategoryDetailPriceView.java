package com.simicart.core.common.price;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

/**
 * Created by MSI on 10/12/2015.
 */
public class CategoryDetailPriceView {
    protected ProductEntity mProductEntity;
    protected Context mContext;
    protected LinearLayout ll_price;
    protected TextView tv_first;
    protected TextView tv_second;
    protected boolean isShowOnePrice = false;

    private float mPrice;
    private float mSalePrice;
    private float mPriceTax;
    private float mSalePriceTax;

    public CategoryDetailPriceView(ProductEntity product) {
        mProductEntity = product;
        mContext = SimiManager.getIntance().getCurrentContext();
    }

    public void setShowOnePrice(boolean showOne) {
        isShowOnePrice = showOne;
    }

    public View createView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ll_price = (LinearLayout) inflater.inflate(Rconfig.getInstance().layout("core_price_layout"), null);
        tv_first = (TextView) ll_price.findViewById(Rconfig.getInstance().id("tv_fist_price"));
        tv_second = (TextView) ll_price.findViewById(Rconfig.getInstance().id("tv_second_price"));
        mPrice = mProductEntity.getPrice();
        mSalePrice = mProductEntity.getSalePrice();
        createPriceWithoutTax();
        return ll_price;
    }

    protected void createPriceWithoutTax() {
        if (mPrice == mSalePrice) {
            tv_second.setVisibility(View.GONE);
            String sPrice = getPrice(mPrice);
            if (Utils.validateString(sPrice)) {
                tv_first.setText(sPrice);
            }
        } else {
            if (mSalePrice == 0) {
                tv_second.setVisibility(View.GONE);
                tv_first.setText(getPrice(mPrice));
            } else {
                if(isShowOnePrice){
                    tv_first.setText(getPrice(mSalePrice));
                    tv_second.setVisibility(View.GONE);
                }else{
                    tv_second.setText(getPrice(mSalePrice));
                    tv_first.setPaintFlags(tv_first.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tv_first.setText(getPrice(mPrice));
                }
            }
        }
    }

    protected String getPrice(float price) {
        return Config.getInstance().getPrice(price);
    }
}
