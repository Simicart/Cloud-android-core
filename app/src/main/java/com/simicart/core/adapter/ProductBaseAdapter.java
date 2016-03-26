package com.simicart.core.adapter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.R;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

@SuppressLint("ViewHolder")
public class ProductBaseAdapter extends BaseAdapter {

    protected ArrayList<ProductEntity> mProductList;
    protected Context mContext;
    protected boolean isHome;
    private double mPrice;
    private double mSalePrice;
    ViewHolder holder;

    public ProductBaseAdapter(Context context, ArrayList<ProductEntity> ProductList) {
        this.mContext = context;
        this.mProductList = ProductList;
    }


    public void setProductList(ArrayList<ProductEntity> products) {
        mProductList = products;
    }


    public void setIsHome(boolean isHome) {
        this.isHome = isHome;
    }

    @Override
    public int getCount() {
        return this.mProductList.size();
    }


    @Override
    public ProductEntity getItem(int position) {
        return this.mProductList.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(
                    Rconfig.getInstance().layout("core_product_list_details"),
                    null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(Rconfig
                    .getInstance().id("product_list_name"));
            holder.ll_price = (LinearLayout) convertView.findViewById(Rconfig
                    .getInstance().id("layout_price"));
            holder.img_avartar = (ImageView) convertView.findViewById(Rconfig
                    .getInstance().id("product_list_image"));
            holder.tv_first = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_fist_price"));
            holder.tv_second = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_second_price"));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductEntity product = getItem(position);


        // name
        holder.tv_name.setText(product.getName());
        holder.tv_name.setTextColor(Config.getInstance().getContent_color());
        if (isHome) {
            if (DataLocal.isLanguageRTL) {
                holder.tv_name.setGravity(Gravity.RIGHT);
                holder.tv_first.setGravity(Gravity.RIGHT);
                holder.tv_second.setGravity(Gravity.RIGHT);
            } else {
                holder.tv_name.setGravity(Gravity.LEFT);
                holder.tv_first.setGravity(Gravity.LEFT);
                holder.tv_second.setGravity(Gravity.LEFT);
            }
        }
        // price
        if (isHome) {
            if (DataLocal.isLanguageRTL) {
                holder.ll_price.setGravity(Gravity.RIGHT);
            } else {
                holder.ll_price.setGravity(Gravity.LEFT);
            }
        }

        // price
        createPriceWithoutTax(holder, product);

        // image
        if (product.getID().equals("fake")) {
            // fake image
            holder.img_avartar.setImageResource(Rconfig.getInstance().drawable(
                    "fake_product"));
            holder.img_avartar.setScaleType(ScaleType.FIT_XY);
        } else {
            ArrayList<String> array = product.getImages();
            if (null != array && array.size() > 0) {
                String urlImage = array.get(0);
                Log.e("ProductBaseAdapter ",urlImage);
                if (urlImage != null) {
//                    DrawableManager.fetchDrawableOnThread(urlImage,
//                            holder.img_avartar);

                    final AtomicBoolean playAnimation = new AtomicBoolean(true);
                    Picasso.with(mContext).load(urlImage).into(holder.img_avartar, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (playAnimation.get()) {
                                Animation fadeOut = AnimationUtils.loadAnimation(mContext,
                                        R.anim.appear_from_center);
                                holder.img_avartar.startAnimation(fadeOut);
                            }
                        }

                        @Override
                        public void onError() {
                            holder.img_avartar.setImageResource(Rconfig.getInstance().drawable("default_logo"));
                        }
                    });
                    playAnimation.set(false);
                }else{
                    holder.img_avartar.setImageResource(Rconfig.getInstance().drawable("default_logo"));
                }
            }else{
                holder.img_avartar.setImageResource(Rconfig.getInstance().drawable("default_logo"));
            }
        }

        // dispatch event for Product Label Plugin
        RelativeLayout rl_product_list = (RelativeLayout) convertView
                .findViewById(Rconfig.getInstance().id("rel_product_list_spot"));

        Intent intent = new Intent("com.simicart.image.product.home");
        SimiEventBlockEntity blockEntity = new SimiEventBlockEntity();
        blockEntity.setView(rl_product_list);
        blockEntity.setEntity(product);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY,blockEntity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);

        return convertView;
    }

    static class ViewHolder {
        ImageView img_avartar;
        TextView tv_name;
        LinearLayout layoutStock;
        LinearLayout ll_price;
        TextView tv_first;
        TextView tv_second;
    }

    protected void createPriceWithoutTax(ViewHolder holder, ProductEntity product) {
        holder.tv_first.setVisibility(View.VISIBLE);
        holder.tv_second.setVisibility(View.VISIBLE);

        holder.tv_first.setTextColor(Color.parseColor(Config.getInstance().getPrice_color()));
        holder.tv_second.setTextColor(Color.parseColor(Config.getInstance().getPrice_color()));

        mPrice = product.getPrice();
        mSalePrice = product.getSalePrice();
        if (mPrice == mSalePrice) {
            holder.tv_second.setVisibility(View.GONE);
            String sPrice = getPrice(mPrice);
            if (Utils.validateString(sPrice)) {
                holder.tv_first.setText(sPrice);
            }
        } else {
            if (mSalePrice == -1) {
                holder.tv_second.setVisibility(View.GONE);
                holder.tv_first.setText(getPrice(mPrice));
            } else {
                holder.tv_first.setText(getPrice(mSalePrice));
                holder.tv_second.setVisibility(View.GONE);
            }
        }
    }

    protected String getPrice(double price) {
        return Config.getInstance().getPrice(price);
    }

}
