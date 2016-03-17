package com.simicart.core.checkout.block;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.simicart.core.adapter.CartListAdapter;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.checkout.controller.CartListenerController;
import com.simicart.core.checkout.delegate.CartDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.common.price.TotalPriceView;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.material.ButtonRectangle;

public class CartBlock extends SimiBlock implements CartDelegate {

    protected ButtonRectangle btn_Checkout;
    protected ListView lv_product;
    protected TableLayout layoutPrice;
    protected CartListAdapter mAdapter;
    protected CartListenerController mListenerController;

    protected ProgressDialog pp_checkout;

    private View line_price;
    private View line_bottom;
    private View line_vertical;

    protected static String message = "";

    public CartBlock(View view, Context context) {
        super(view, context);
        mListenerController = new CartListenerController();
        mListenerController.setCartDelegate(this);
    }

    @Override
    public void initView() {
        // check out button
        btn_Checkout = (ButtonRectangle) mView.findViewById(Rconfig
                .getInstance().id("checkout"));
        btn_Checkout.setText(Config.getInstance().getText("CHECKOUT"));
        btn_Checkout.setTextColor(Color.parseColor("#FFFFFF"));
        btn_Checkout.setTextSize(Constants.SIZE_TEXT_BUTTON);

        // list product
        lv_product = (ListView) mView.findViewById(Rconfig.getInstance().id(
                "cart_item_list"));
        lv_product.setDivider(null);

        // price
        layoutPrice = (TableLayout) mView.findViewById(Rconfig.getInstance()
                .id("ll_pricetotal"));
        if (DataLocal.isTablet) {
            line_price = mView.findViewById(Rconfig.getInstance()
                    .id("line_price"));
            line_bottom = mView.findViewById(Rconfig.getInstance()
                    .id("line_bottom"));
            line_vertical = mView.findViewById(Rconfig.getInstance()
                    .id("line_vertical"));
            Utils.changeColorLine(line_price);
            Utils.changeColorLine(line_bottom);
            Utils.changeColorLine(line_vertical);
        }
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
                    mListenerController.setListCart(productEntity);
                    if (null == mAdapter) {
                        mAdapter = new CartListAdapter(mContext, productEntity);
                        lv_product.setAdapter(mAdapter);
                        mAdapter.setDelegate(mListenerController);
                    } else {
                        mAdapter.setListCart(productEntity);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                visiableView();
                return;
            }
        } else {
            visiableView();
            return;
        }

        mListenerController.setmMessage(message);
    }

    protected boolean isShown() {
        return mView.isShown();
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

    public void setCheckoutClicker(OnClickListener clicker) {
        btn_Checkout.setOnClickListener(clicker);
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
    public void showPopupCheckout(ProgressDialog popup) {
        pp_checkout = popup;
        pp_checkout.show();
    }

    @Override
    public void dismissPopupCheckout() {
        pp_checkout.dismiss();
    }

    @Override
    public void setMessage(String message) {
        CartBlock.message = message;
        mListenerController.setmMessage(message);
    }

    @Override
    public void visibleAllView() {
        visiableView();
    }

}
