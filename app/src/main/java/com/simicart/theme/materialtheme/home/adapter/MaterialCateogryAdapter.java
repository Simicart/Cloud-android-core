package com.simicart.theme.materialtheme.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;
import java.util.ArrayList;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCateogryAdapter extends RecyclerView.Adapter<MaterialCateogryAdapter.ViewHolder> {
    protected ArrayList<ProductEntity> listProduct;
    protected Context mContext;
    private double mPrice;
    private double mSalePrice;

    public MaterialCateogryAdapter(ArrayList<ProductEntity> listProduct, Context mContext) {
        this.listProduct = listProduct;
        this.mContext = mContext;
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(Rconfig.getInstance().layout("material_category_item_layout"), parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductEntity product = listProduct.get(position);

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

        Intent intent = new Intent("com.simicart.image.product.list");
        SimiEventBlockEntity blockEntity = new SimiEventBlockEntity();
        blockEntity.setView(holder.rl_product_list);
        blockEntity.setEntity(product);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, blockEntity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView txtName;
        public LinearLayout layoutStock;
        public TextView txt_outstock;
        public TextView tv_first;
        public TextView tv_second;
        public RelativeLayout rl_product_list;

        public ViewHolder(View v) {
            super(v);

            txtName = (TextView) v.findViewById(Rconfig
                    .getInstance().id("tv_productItemName"));
            txtName
                    .setTextColor(Config.getInstance().getContent_color());
            imageView = (ImageView) v.findViewById(Rconfig
                    .getInstance().id("iv_productItemImage"));
            layoutStock = (LinearLayout) v
                    .findViewById(Rconfig.getInstance().id("layout_stock"));
            layoutStock.setBackgroundColor(Config.getInstance()
                    .getOut_stock_background());
            ImageView ic_expand = (ImageView) v.findViewById(Rconfig
                    .getInstance().id("ic_expand"));
            Drawable icon = mContext.getResources().getDrawable(
                    Rconfig.getInstance().drawable("ic_extend"));
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            ic_expand.setImageDrawable(icon);
            txt_outstock = (TextView) v.findViewById(Rconfig
                    .getInstance().id("txt_out_stock"));
            txt_outstock.setTextColor(Config.getInstance()
                    .getOut_stock_text());
            tv_first = (TextView) v.findViewById(Rconfig.getInstance().id("tv_fist_price"));
            tv_second = (TextView) v.findViewById(Rconfig.getInstance().id("tv_second_price"));

            rl_product_list = (RelativeLayout) v
                    .findViewById(Rconfig.getInstance().id("rel_product_list"));
        }
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