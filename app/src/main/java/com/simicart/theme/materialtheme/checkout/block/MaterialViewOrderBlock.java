package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.entity.CouponCodeEntity;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.price.TotalPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialViewOrderAdapter;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialViewOrderDelegate;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialViewOrderBlock extends SimiBlock implements MaterialViewOrderDelegate {
    protected RecyclerView rcv_view_order;
    protected TableLayout tb_price_total;
    protected MaterialViewOrderAdapter mAdapter;
    protected EditText edt_couponCode;
    protected ImageView image_remove_coupon;
    private int SHOW_REMOVE_COUPONCODE = 0;
    private int HIDEN_REMOVE_COUPONCODE = 1;

    public MaterialViewOrderBlock(View view, Context context) {
        super(view, context);
    }

    public void setCouponCodeListener(TextView.OnEditorActionListener listener) {
        edt_couponCode.setOnEditorActionListener(listener);
    }

    public void setCouponChange(TextWatcher listener) {
        edt_couponCode.addTextChangedListener(listener);
    }

    public void setOnClickRemoveListener(View.OnClickListener onClick) {
        image_remove_coupon.setOnClickListener(onClick);
    }

    @Override
    public void initView() {
        rcv_view_order = (RecyclerView) mView.findViewById(Rconfig.getInstance().id("rcv_view_order"));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext(), LinearLayoutManager.VERTICAL, false);
        rcv_view_order.setHasFixedSize(true);
        rcv_view_order.setLayoutManager(linearLayoutManager);

        tb_price_total = (TableLayout) mView.findViewById(Rconfig.getInstance().id("tb_price_total"));

        edt_couponCode = (EditText) mView.findViewById(Rconfig.getInstance()
                .id("edt_counponCode"));
        edt_couponCode.setHighlightColor(Color.parseColor("#b2b2b2"));
        edt_couponCode.setHint(Config.getInstance().getText(
                "Enter a coupon code"));
        image_remove_coupon = (ImageView) mView.findViewById(Rconfig.getInstance().id("image_remove_couponcode"));
        image_remove_coupon.setVisibility(View.GONE);
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

    private void showProducts(ArrayList<ProductEntity> products) {
        if (mAdapter == null) {
            mAdapter = new MaterialViewOrderAdapter(products);
            rcv_view_order.setAdapter(mAdapter);
        } else {
            mAdapter.setListProduct(products);
            mAdapter.notifyDataSetChanged();
        }
        rcv_view_order.setNestedScrollingEnabled(false);
    }

    private void showTotalPrice(QuoteEntity totalPrice) {
        TotalPriceView viewPrice = new TotalPriceView(totalPrice);
        View view = viewPrice.createTotalView();
        if (null != view) {
            tb_price_total.removeAllViewsInLayout();
            tb_price_total.addView(view);
        }
    }

    @Override
    public void showCouponCode(QuoteEntity quote) {
        ArrayList<CouponCodeEntity> listCouponCode = quote.getCouponCode();
        if (listCouponCode != null && listCouponCode.size() > 0) {
            CouponCodeEntity couponCode = listCouponCode.get(0);
            edt_couponCode.setText(couponCode.getCode());
        }
    }

    @Override
    public void removeTextCouponCode() {
        edt_couponCode.setText("");
        image_remove_coupon.setVisibility(View.GONE);
    }

    @Override
    public void showHidenRemoveCoupon(int type) {
        if (type == SHOW_REMOVE_COUPONCODE) {
            image_remove_coupon.setVisibility(View.VISIBLE);
        } else if (type == HIDEN_REMOVE_COUPONCODE) {
            image_remove_coupon.setVisibility(View.GONE);
        }
    }
}
