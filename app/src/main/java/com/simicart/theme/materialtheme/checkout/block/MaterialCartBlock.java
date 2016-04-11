package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.price.TotalPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.MaterialCartAdapter;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialCartDelegate;
import com.simicart.theme.materialtheme.common.CustomLinearLayoutManager;

import java.util.ArrayList;

/**
 * Created by Sony on 4/11/2016.
 */
public class MaterialCartBlock extends SimiBlock implements MaterialCartDelegate {
    protected TableLayout layoutPrice;
    protected RecyclerView rcv_cart;
    protected MaterialCartAdapter mAdapter;

    public MaterialCartBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        rcv_cart = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("rcv_cart"));
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_cart.setHasFixedSize(true);
        rcv_cart.setLayoutManager(linearLayoutManager);
        layoutPrice = (TableLayout) mView.findViewById(Rconfig.getInstance()
                .id("ll_pricetotal"));
    }

    @Override
    public void drawView(SimiCollection collection) {
        QuoteEntity entity = (QuoteEntity) collection.getCollection().get(0);

        if (null != entity) {
            ArrayList<ProductEntity> productEntity = new ArrayList<ProductEntity>();
            if (entity.getProduct() != null && entity.getProduct().size() > 0) {
                for (int i = 0; i < entity.getProduct().size(); i++) {
                    SimiEntity simiEntity = entity.getProduct().get(i);
                    ProductEntity product = (ProductEntity) simiEntity;
                    productEntity.add(product);
                }
                if (productEntity.size() > 0) {
                    if (mAdapter == null) {
                        mAdapter = new MaterialCartAdapter(productEntity);
                        rcv_cart.setAdapter(mAdapter);
                    } else {
                        mAdapter.setListProduct(productEntity);
                        mAdapter.notifyDataSetChanged();
                    }
                    rcv_cart.setNestedScrollingEnabled(false);
                } else {
                    visiableView();
                }
            } else {
                visiableView();
            }
        } else {
            visiableView();
        }
    }

    @Override
    public void onUpdateTotalPrice(QuoteEntity totalPrice) {
        setPriceView(totalPrice);
    }

    protected void setPriceView(QuoteEntity totalPrice) {
        TotalPriceView viewPrice = new TotalPriceView(totalPrice);
        View view = viewPrice.createTotalView();
        if (null != view) {
            layoutPrice.removeAllViews();
            layoutPrice.addView(view);
        }
    }

    public void visiableView() {
        ((ViewGroup) mView).removeAllViewsInLayout();
        TextView tv_notify = new TextView(mContext);
        tv_notify.setTextColor(Config.getInstance().getContent_color());
        tv_notify.setText(Config.getInstance().getText(
                "Your shopping cart is empty"));
        tv_notify.setTypeface(null, Typeface.BOLD);
        if (DataLocal.isTablet) {
            tv_notify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        } else {
            tv_notify.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv_notify.setGravity(Gravity.CENTER);
        tv_notify.setLayoutParams(params);
        ((ViewGroup) mView).addView(tv_notify);
    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public void visibleAllView() {
        visiableView();
    }
}
