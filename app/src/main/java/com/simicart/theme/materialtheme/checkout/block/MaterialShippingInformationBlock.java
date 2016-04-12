package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialAddressAdapter;
import java.util.ArrayList;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialShippingInformationBlock extends SimiBlock {
    protected Spinner sp_shipping_information;
    protected TextView tv_use_billing_address;
    protected TextView tv_continue;
    protected MaterialAddressAdapter mAdapter;

    public MaterialShippingInformationBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        sp_shipping_information = (Spinner) mView.findViewById(Rconfig.getInstance().id("sp_billing_information"));

        tv_use_billing_address = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_choose_ship_to_address"));
        tv_use_billing_address.setText(Config.getInstance().getText("Use Billing Address"));

        tv_continue = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_ship_to_different_address"));
        tv_continue.setText(Config.getInstance().getText("Continue"));
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (collection != null && collection.getCollection().size() > 0) {
            ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
            if (null != entity) {
                ArrayList<MyAddress> listAddress = new ArrayList<MyAddress>();
                if (entity.getAddress() != null) {
                    if (entity.getAddress().size() > 0) {
                        for (SimiEntity simiEntity : entity.getAddress()) {
                            MyAddress myAddress = (MyAddress) simiEntity;
                            listAddress.add(myAddress);
                        }
                        if (listAddress.size() > 0) {
                            if (mAdapter == null) {
                                mAdapter = new MaterialAddressAdapter(mContext, listAddress);
                                sp_shipping_information.setAdapter(mAdapter);
                                sp_shipping_information.setSelection(0);
                            } else {
                                mAdapter.setListAddress(listAddress);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        }
    }
}
