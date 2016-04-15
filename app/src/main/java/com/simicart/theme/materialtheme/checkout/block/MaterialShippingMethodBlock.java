package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialShippingMethodAdapter;

import java.util.ArrayList;

/**
 * Created by Sony on 4/13/2016.
 */
public class MaterialShippingMethodBlock extends SimiBlock {
    protected RecyclerView rcv_shipping_method;
    protected MaterialShippingMethodAdapter mAdapter;
    MaterialShippingMethodAdapter.OnItemClickListener onSelectItemlistener;

    public MaterialShippingMethodBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnItemClick(MaterialShippingMethodAdapter.OnItemClickListener listener){
        this.onSelectItemlistener = listener;
    }

    @Override
    public void initView() {
        rcv_shipping_method = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("rcv_shipping_method"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_shipping_method.setHasFixedSize(true);
        rcv_shipping_method.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (collection != null && collection.getCollection().size() > 0) {
            ArrayList<SimiEntity> entity = collection.getCollection();
            if (entity != null && entity.size() > 0) {
                ArrayList<ShippingMethod> shippingMethodsArr = new ArrayList<ShippingMethod>();
                for (SimiEntity simiEntity : entity) {
                    ShippingMethod shippingMethod = (ShippingMethod) simiEntity;
                    shippingMethodsArr.add(shippingMethod);
                }

                if (shippingMethodsArr.size() > 0) {
                    if(mAdapter == null){
                        mAdapter = new MaterialShippingMethodAdapter(shippingMethodsArr, mContext);
                        rcv_shipping_method.setAdapter(mAdapter);
                    }else {
                        mAdapter.setListShippingMethod(shippingMethodsArr);
                        mAdapter.notifyDataSetChanged();
                    }
                    mAdapter.setOnItemClickListener(onSelectItemlistener);
                    rcv_shipping_method.setNestedScrollingEnabled(false);
                }
            }
        }
    }
}
