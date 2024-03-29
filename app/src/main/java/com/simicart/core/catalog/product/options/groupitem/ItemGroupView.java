package com.simicart.core.catalog.product.options.groupitem;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.common.ViewIdGenerator;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by MSI on 08/12/2015.
 */
public class ItemGroupView {
    private ItemGroupEntity mItem;
    protected float mScale;
    protected Context mContext;
    protected int qtyForCheckout = 0;
    protected ItemGroupDelegate mDelegate;
    protected ImageView img_expand;
    protected TextView tv_qty;
    protected RelativeLayout rlt_body;
    protected boolean isShow = true;
    protected TextView tv_price;
    protected TextView tv_salePrice;

    public void setDelegate(ItemGroupDelegate delegate) {
        mDelegate = delegate;
    }

    public ItemGroupView(ItemGroupEntity item, Context context) {
        mItem = item;
        mContext = context;
        mScale = mContext.getResources().getDisplayMetrics().density;
        qtyForCheckout = mItem.getDefaultQty();
    }

    public View createView() {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        LinearLayout ll_itemGroup = (LinearLayout) inflater.inflate(Rconfig.getInstance().layout("core_item_group_layout"), null, false);
        RelativeLayout rlt_header = (RelativeLayout) ll_itemGroup.findViewById(Rconfig.getInstance().id("rlt_header"));
        rlt_body = (RelativeLayout) ll_itemGroup.findViewById(Rconfig.getInstance().id("rlt_body"));
        rlt_body.setVisibility(View.VISIBLE);

        img_expand = (ImageView) rlt_header.findViewById(Rconfig.getInstance().id("img_expand"));
        tv_qty = (TextView) rlt_header.findViewById(Rconfig.getInstance().id("tv_qty"));
        tv_qty.setText("Qty: " + qtyForCheckout);
        TextView tv_name = (TextView) rlt_header.findViewById(Rconfig.getInstance().id("tv_name"));
        String name = mItem.getName();
        tv_name.setText(name);

        if (qtyForCheckout > 0) {
            int temQty = qtyForCheckout;
            for (int i = 0; i < temQty; i++) {
                mDelegate.updatePriceForItemGroup(mItem, true);
            }
        }

        img_expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    rlt_body.setVisibility(View.GONE);
                    img_expand.setImageResource(Rconfig.getInstance().drawable("ic_down"));
                } else {
                    isShow = true;
                    rlt_body.setVisibility(View.VISIBLE);
                    img_expand.setImageResource(Rconfig.getInstance().drawable("ic_up"));
                }


            }
        });

        // price
        tv_price = (TextView) rlt_body.findViewById(Rconfig.getInstance().id("tv_price"));
        tv_salePrice = (TextView) rlt_body.findViewById(Rconfig.getInstance().id("tv_sale_price"));
        createPrice();

        // image add
        ImageView img_add = (ImageView) rlt_body.findViewById(Rconfig.getInstance().id("img_add"));
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });


        // image sub
        ImageView img_sub = (ImageView) rlt_body.findViewById(Rconfig.getInstance().id("img_sub"));
        img_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subItem();
            }
        });

        return ll_itemGroup;
    }

    private void addItem() {
        if (qtyForCheckout < 0) {
            qtyForCheckout = 0;
        }
        qtyForCheckout++;
        tv_qty.setText("Qty: " + qtyForCheckout);
        mDelegate.updatePriceForItemGroup(mItem, true);
    }

    private void subItem() {
        qtyForCheckout = qtyForCheckout -1;
        Log.e("ItemGroupView ", "SUB QTY " + qtyForCheckout);
        if (qtyForCheckout >= 0) {
            tv_qty.setText("Qty: " + qtyForCheckout);
            mDelegate.updatePriceForItemGroup(mItem, false);
        } else {
            qtyForCheckout = 0;
            tv_qty.setText("Qty: " + qtyForCheckout);
        }
    }

    private void createPrice() {

        float price1 = 0;
        float price2 = 0;

        float salePriceTax = mItem.getSalePriceTax();
        float priceTax = mItem.getPriceTax();
        float salePrice = mItem.getSalePrice();
        float price = mItem.getPrice();
        if (salePriceTax > 0 || priceTax > 0) {
            if(salePriceTax > 0) {
                price2 = salePriceTax;
            }
            if (priceTax > 0) {
                price1 = priceTax;
            }

        } else {
            if(salePrice > 0)
            {
                price2 = salePrice;
            }
            if(price > 0)
            {
                price1 = price;
            }
        }

        if(price2 <= 0)
        {
            tv_salePrice.setVisibility(View.GONE);
            if(price1 > 0)
            {
                String s_price1 = Config.getInstance().getPrice(price1);
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText(s_price1);
            }
        }
        else {
            String s_price2 = Config.getInstance().getPrice(price2);
            tv_salePrice.setVisibility(View.VISIBLE);
            tv_salePrice.setText(s_price2);
            if(price1 <= 0)
            {
                tv_price.setVisibility(View.GONE);
            }
            else
            {
                tv_price.setPaintFlags(tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                String s_price1 = Config.getInstance().getPrice(price1);
                tv_price.setVisibility(View.VISIBLE);
                tv_price.setText(s_price1);
            }
        }


    }


    public JSONObject getDataForCheckout() {
        if (qtyForCheckout <= 0) {
            qtyForCheckout = 1;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("item_id", mItem.getItemID());
            json.put("qty", qtyForCheckout);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public boolean isComple() {
        if (qtyForCheckout < mItem.getDefaultQty()) {
            return false;
        }
        return true;
    }

}
