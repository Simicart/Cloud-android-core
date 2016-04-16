package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TableLayout;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.price.TotalPriceView;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialViewOrderAdapter;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialViewOrderBlock extends SimiBlock {
    protected RecyclerView rcv_view_order;
    protected TableLayout tb_price_total;
    protected MaterialViewOrderAdapter mAdapter;

    public MaterialViewOrderBlock(View view, Context context) {
        super(view, context);
    }

    @Override
    public void initView() {
        rcv_view_order = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("rcv_view_order"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_view_order.setHasFixedSize(true);
        rcv_view_order.setLayoutManager(linearLayoutManager);

        tb_price_total = (TableLayout) mView.findViewById(Rconfig.getInstance().id("tb_price_total"));
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (collection != null && collection.getCollection().size() > 0) {
            QuoteEntity entity = (QuoteEntity) collection.getCollection().get(0);

            // show Products
            ArrayList<ProductEntity> products = entity.getProduct();
            if (null != products && products.size() > 0) {
                showProducts(products);
            }

            // show Total Price
            showTotalPrice(entity);
        }
    }

    private void showProducts(ArrayList<ProductEntity> products){
        if(mAdapter == null){
            mAdapter = new MaterialViewOrderAdapter(products);
            rcv_view_order.setAdapter(mAdapter);
        }else{
            mAdapter.setListProduct(products);
            mAdapter.notifyDataSetChanged();
        }
        rcv_view_order.setNestedScrollingEnabled(false);
    }

    private void showTotalPrice(QuoteEntity totalPrice){
        TotalPriceView viewPrice = new TotalPriceView(totalPrice);
        View view = viewPrice.createTotalView();
        if (null != view) {
            tb_price_total.removeAllViewsInLayout();
            tb_price_total.addView(view);
        }
    }
}
