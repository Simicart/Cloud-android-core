package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialPaymentInformationAdapter;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialPaymentInformationBlock extends SimiBlock {
    protected RecyclerView rcv_payment_method;
    protected MaterialPaymentInformationAdapter mAdapter;
    protected MaterialPaymentInformationAdapter.OnItemClickListener onSelectItemlistener;

    public MaterialPaymentInformationBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnItemClick(MaterialPaymentInformationAdapter.OnItemClickListener listener){
        this.onSelectItemlistener = listener;
    }

    @Override
    public void initView() {
        rcv_payment_method = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("rcv_shipping_method"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_payment_method.setHasFixedSize(true);
        rcv_payment_method.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (collection != null && collection.getCollection().size() > 0) {
            ArrayList<SimiEntity> entity = collection.getCollection();
            if (entity != null && entity.size() > 0) {
                ArrayList<PaymentMethod> paymentMethodsArr = new ArrayList<PaymentMethod>();
                for (SimiEntity simiEntity : entity) {
                    PaymentMethod paymentMethod = (PaymentMethod) simiEntity;
                    paymentMethodsArr.add(paymentMethod);
                }

                if (paymentMethodsArr.size() > 0) {
                    if (mAdapter == null) {
                        mAdapter = new MaterialPaymentInformationAdapter(paymentMethodsArr, mContext);
                        rcv_payment_method.setAdapter(mAdapter);
                    } else {
                        mAdapter.setListPaymentMethod(paymentMethodsArr);
                        mAdapter.notifyDataSetChanged();
                    }
                    mAdapter.setOnItemClickListener(onSelectItemlistener);
                    rcv_payment_method.setNestedScrollingEnabled(false);
                }
            }
        }
    }
}
