package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
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
public class MaterialBillingInformationBlock extends SimiBlock {
    protected Spinner sp_billing_information;
    protected TextView tv_choose_ship_to_address;
    protected TextView tv_ship_to_different_address;
    protected MaterialAddressAdapter mAdapter;

    public MaterialBillingInformationBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnSelectedBilling(AdapterView.OnItemSelectedListener listener){
        sp_billing_information.setOnItemSelectedListener(listener);
    }

    public void setOnClickShipToAddress(View.OnClickListener listener){
        tv_choose_ship_to_address.setOnClickListener(listener);
    }

    public void setOnClickShipToDifferentAdress(View.OnClickListener listener){
        tv_ship_to_different_address.setOnClickListener(listener);
    }

    @Override
    public void initView() {
        sp_billing_information = (Spinner) mView.findViewById(Rconfig.getInstance().id("sp_billing_information"));

        tv_choose_ship_to_address = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_choose_ship_to_address"));
        tv_choose_ship_to_address.setText(Config.getInstance().getText("Ship to this address"));

        tv_ship_to_different_address = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_ship_to_different_address"));
        tv_ship_to_different_address.setText(Config.getInstance().getText("Ship to different address"));
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
                            if(mAdapter == null){
                                mAdapter = new MaterialAddressAdapter(mContext, listAddress);
                                sp_billing_information.setAdapter(mAdapter);
                                sp_billing_information.setSelection(0);
                            }else{
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
