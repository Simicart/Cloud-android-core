package com.simicart.core.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;
import com.simicart.core.productlabel.ProductLabel;

public class ProductListAdapter extends BaseAdapter {

    protected ArrayList<ProductEntity> mProducts;
    protected Context mContext;
    private double mPrice;
    private double mSalePrice;

    public ProductListAdapter(Context context, ArrayList<ProductEntity> ProductList) {
        mContext = context;
        mProducts = ProductList;
    }


    public void setProductList(ArrayList<ProductEntity> products) {
        mProducts = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductEntity product = (ProductEntity) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(
                            Rconfig.getInstance().layout(
                                    "core_item_list_search"), null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(Rconfig
                    .getInstance().id("tv_productItemName"));
            holder.txtName
                    .setTextColor(Config.getInstance().getContent_color());
            holder.imageView = (ImageView) convertView.findViewById(Rconfig
                    .getInstance().id("iv_productItemImage"));
            holder.layoutStock = (LinearLayout) convertView
                    .findViewById(Rconfig.getInstance().id("layout_stock"));
            holder.layoutStock.setBackgroundColor(Config.getInstance()
                    .getOut_stock_background());
            ImageView ic_expand = (ImageView) convertView.findViewById(Rconfig
                    .getInstance().id("ic_expand"));
            Drawable icon = mContext.getResources().getDrawable(
                    Rconfig.getInstance().drawable("ic_extend"));
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            ic_expand.setImageDrawable(icon);
            holder.txt_outstock = (TextView) convertView.findViewById(Rconfig
                    .getInstance().id("txt_out_stock"));
            holder.txt_outstock.setTextColor(Config.getInstance()
                    .getOut_stock_text());
            holder.tv_first = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_fist_price"));
            holder.tv_second = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_second_price"));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (DataLocal.isLanguageRTL) {
            holder.txtName.setGravity(Gravity.RIGHT);
            holder.tv_first.setGravity(Gravity.RIGHT);
            holder.tv_second.setGravity(Gravity.RIGHT);
        }

        holder.txtName.setText(product.getName());

        ArrayList<String> images = product.getImages();
        if (holder.imageView != null) {
            if (null != images && images.size() > 0) {
                String link_image = images.get(0);
                DrawableManager.fetchDrawableOnThread(link_image, holder.imageView);
            } else {
                holder.imageView.setImageResource(Rconfig.getInstance().drawable("default_logo"));
            }
        } else {
            holder.imageView.setImageResource(Rconfig.getInstance().drawable("default_logo"));
        }

        createPriceWithoutTax(holder, product);

        holder.layoutStock.setVisibility(View.GONE);

        RelativeLayout rl_product_list = (RelativeLayout) convertView
                .findViewById(Rconfig.getInstance().id("rel_product_list"));

        new ProductLabel("addViewProductInList",rl_product_list);
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView txtName;
        LinearLayout layoutStock;
        TextView txt_outstock;
        TextView tv_first;
        TextView tv_second;
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    protected void createPriceWithoutTax(ViewHolder holder, ProductEntity product) {
        holder.tv_first.setPaintFlags(holder.tv_first.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        holder.tv_first.setVisibility(View.VISIBLE);
        holder.tv_first.setTextColor(Color.parseColor(Config.getInstance().getPrice_color()));
        holder.tv_second.setVisibility(View.VISIBLE);
        holder.tv_second.setTextColor(Color.parseColor(Config.getInstance().getSpecial_price_color()));

        mPrice = product.getPrice();
        mSalePrice = product.getSalePrice();
        if (mPrice == mSalePrice) {
            holder.tv_second.setVisibility(View.GONE);
            String sPrice = Config.getInstance().getPrice(mPrice);
            if (Utils.validateString(sPrice)) {
                holder.tv_first.setText(sPrice);
            }
        } else {
            if (mSalePrice == -1) {
                holder.tv_second.setVisibility(View.GONE);
                holder.tv_first.setText(Config.getInstance().getPrice(mPrice));
            } else {
                holder.tv_second.setText(Config.getInstance().getPrice(mSalePrice));
                holder.tv_first.setPaintFlags(holder.tv_first.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_first.setText(Config.getInstance().getPrice(mPrice));
            }
        }
    }

}
