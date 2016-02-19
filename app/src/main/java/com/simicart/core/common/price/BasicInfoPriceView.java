package com.simicart.core.common.price;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
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
 * Created by Sony on 12/23/2015.
 */
public class BasicInfoPriceView {
    protected ProductEntity mProductEntity;
    protected Context mContext;
    protected LinearLayout view_price;
    protected LinearLayout ll_price;
    protected TextView tv_first;
    protected TextView tv_second;
    protected LinearLayout ll_price_tax;
    protected LinearLayout ll_regular;
    protected TextView lb_regular_in_tax;
    protected TextView tv_regular_in_tax;
    protected LinearLayout ll_special;
    protected TextView lb_special;
    protected TextView lb_spec_ex_tax;
    protected TextView tv_spec_ex_tax;
    protected TextView lb_spec_in_tax;
    protected TextView tv_spec_in_tax;

    private double mPrice;
    private double mSalePrice;
    private double mPriceTax;
    private double mSalePriceTax;

    public BasicInfoPriceView(ProductEntity product) {
        mProductEntity = product;
        mContext = SimiManager.getIntance().getCurrentContext();
    }

    public View createView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view_price = (LinearLayout) inflater.inflate(Rconfig.getInstance().layout("core_price_basic_layout"), null);
        tv_first = (TextView) view_price.findViewById(Rconfig.getInstance().id("tv_fist_price"));
        tv_second = (TextView) view_price.findViewById(Rconfig.getInstance().id("tv_second_price"));
        ll_price = (LinearLayout) view_price.findViewById(Rconfig.getInstance().id("ll_price"));
        ll_price_tax = (LinearLayout) view_price.findViewById(Rconfig.getInstance().id("ll_price_tax"));
        ll_regular = (LinearLayout) view_price.findViewById(Rconfig.getInstance().id("ll_regular"));
        ll_special = (LinearLayout) view_price.findViewById(Rconfig.getInstance().id("ll_special"));
        lb_regular_in_tax = (TextView) view_price.findViewById(Rconfig.getInstance().id("lb_regular_in_tax"));
        lb_regular_in_tax.setText(Config.getInstance().getText("Regular Price") + ":");
        tv_regular_in_tax = (TextView) view_price.findViewById(Rconfig.getInstance().id("tv_regular_in_tax"));
        lb_special = (TextView) view_price.findViewById(Rconfig.getInstance().id("lb_special"));
        lb_special.setText(Config.getInstance().getText("Special Price") + ":");
        lb_spec_ex_tax = (TextView) view_price.findViewById(Rconfig.getInstance().id("lb_spec_ex_tax"));
        lb_spec_ex_tax.setText(Config.getInstance().getText("Excl.Tax") + ":");
        tv_spec_ex_tax = (TextView) view_price.findViewById(Rconfig.getInstance().id("tv_spec_ex_tax"));
        lb_spec_in_tax = (TextView) view_price.findViewById(Rconfig.getInstance().id("lb_spec_in_tax"));
        lb_spec_in_tax.setText(Config.getInstance().getText("Incl.Tax") + ":");
        tv_spec_in_tax = (TextView) view_price.findViewById(Rconfig.getInstance().id("tv_spec_in_tax"));

        mPriceTax = mProductEntity.getPriceIncludeTax();
        mSalePriceTax = mProductEntity.getPriceSaleIncludeTax();
        mPrice = mProductEntity.getPrice();
        mSalePrice = mProductEntity.getSalePrice();

        if(mPriceTax >= 0 && mSalePriceTax >= 0 && mPriceTax > mSalePriceTax){
            ll_price.setVisibility(View.GONE);
            tv_regular_in_tax.setText(getPrice(mPriceTax));
            Log.e("BasicInfoPriceView", mSalePrice + ":" + mSalePriceTax);
            tv_spec_ex_tax.setText(getPrice(mSalePrice));
            tv_spec_in_tax.setText(getPrice(mSalePriceTax));
        }else if(mPriceTax >= 0 && mPriceTax < mPrice){
            ll_price.setVisibility(View.GONE);
            ll_regular.setVisibility(View.GONE);
            lb_special.setVisibility(View.GONE);
            tv_spec_ex_tax.setText(getPrice(mPrice));
            tv_spec_in_tax.setText(getPrice(mPriceTax));
        }else if(mSalePrice >= 0 && mSalePrice < mPrice){
            ll_price_tax.setVisibility(View.GONE);
            tv_second.setText(getPrice(mSalePrice));
            tv_first.setPaintFlags(tv_first.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            tv_first.setText(getPrice(mPrice));
        }else{
            ll_price_tax.setVisibility(View.GONE);
            tv_second.setVisibility(View.GONE);
            String sPrice = getPrice(mPrice);
            tv_first.setText(sPrice);
        }

        return view_price;
    }

    protected String getPrice(double price) {
        return Config.getInstance().getPrice(price);
    }
}
