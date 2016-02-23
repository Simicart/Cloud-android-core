package com.simicart.core.checkout.block;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.MainActivity;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.delegate.ShippingMethodDelegate;
import com.simicart.core.checkout.delegate.ShippingMethodManageDelegate;
import com.simicart.core.checkout.entity.ShippingMethod;

import java.util.ArrayList;

/**
 * Created by MSI on 05/02/2016.
 */
public class ManageShippingMethodView implements ShippingMethodManageDelegate {


    private ArrayList<ShippingMethod> mShippings;
    private ArrayList<ShippingMethodItemView> mItems;
    private Context mContext;
    private ShippingMethodDelegate mDelegate;

    public ManageShippingMethodView(ArrayList<ShippingMethod> shippings, ShippingMethodDelegate delegate) {
        mShippings = shippings;
        mContext = SimiManager.getIntance().getCurrentContext();
        mItems = new ArrayList<ShippingMethodItemView>();
        mDelegate = delegate;
    }

    public View initView() {
        if (null != mShippings && mShippings.size() > 0) {
            LinearLayout ll_shipping = new LinearLayout(MainActivity.context);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll_shipping.setLayoutParams(param);
            ll_shipping.setOrientation(LinearLayout.VERTICAL);
            for (int i = 0; i < mShippings.size(); i++) {
                ShippingMethod shippingMethod = mShippings.get(i);
                ShippingMethodItemView item = new ShippingMethodItemView(shippingMethod, mContext, this);
                View view = item.initView();
                if (null != view) {
                    mItems.add(item);
                    ll_shipping.addView(view);
                }
            }


            return ll_shipping;

        }

        return null;
    }


    @Override
    public void updateShippingMethod(ShippingMethod shippingMethod) {
        if (mItems.size() > 0) {
            for (int i = 0; i < mItems.size(); i++) {
                ShippingMethodItemView item = mItems.get(i);
                ShippingMethod cShipping = item.getShippingMethod();
                if(!cShipping.equals(shippingMethod)){
                    item.updateChecked(false);
                }
            }
        }
        mDelegate.updateShippingMehtod(shippingMethod);

    }
}
