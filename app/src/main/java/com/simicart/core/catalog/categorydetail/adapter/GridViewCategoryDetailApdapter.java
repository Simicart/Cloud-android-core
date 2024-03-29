package com.simicart.core.catalog.categorydetail.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.event.block.SimiEventBlockEntity;

public class GridViewCategoryDetailApdapter extends BaseAdapter {

    class ViewHolder {
        private TextView tv_name;
        private ImageView img_avartar;
        private LinearLayout layout_stock;
        private RelativeLayout layout_header;
        private LinearLayout ll_price;
        private TextView txt_outstock;
        private TextView txt_price_tablet;
        private TextView tv_first;
        private TextView tv_second;
    }

    private Context mContext;
    private ArrayList<ProductEntity> mListProduct;
    private LayoutInflater mInflater;
    private int numColum = 0;
    private RelativeLayout layout_image;
    private float withScreen;
    private RelativeLayout layout_header;
    private double mPrice;
    private double mSalePrice;
    private int dp2;
    private int dp4;
    private int dp6;
    LinearLayout.LayoutParams paramsLayout2;
    LinearLayout.LayoutParams paramsLayout4Phone;
    LinearLayout.LayoutParams paramsLayout4Tablet;
    LinearLayout.LayoutParams paramsLayout6;


    public GridViewCategoryDetailApdapter(Context context,
                                          ArrayList<ProductEntity> listProduct,
                                          int numcolumn) {
        mContext = context;
        mListProduct = listProduct;
        this.numColum = numcolumn;
        getDimension();
    }

    void getDimension() {
        Display display = SimiManager.getIntance().getCurrentActivity()
                .getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = SimiManager.getIntance().getCurrentActivity()
                .getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density;
        this.withScreen = dpWidth;

        dp2 = Utils.getValueDp((int) ((withScreen - 22) / 2));
        dp4 = Utils.getValueDp((int) ((withScreen - 20) / 4));
        dp6 = Utils.getValueDp((int) ((withScreen - 20) / 6));

        paramsLayout2 = new LinearLayout.LayoutParams(dp2, dp2);
        paramsLayout4Phone = new LinearLayout.LayoutParams(dp4, dp4);
        paramsLayout4Tablet = new LinearLayout.LayoutParams(dp4, dp4 + Utils.getValueDp(100));
        paramsLayout6 = new LinearLayout.LayoutParams(dp6, dp6);
    }

    public ArrayList<ProductEntity> getListProduct() {
        return mListProduct;
    }

    public void setListProduct(ArrayList<ProductEntity> products) {
        mListProduct = products;
    }

    @Override
    public int getCount() {
        return mListProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return mListProduct.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        if (convertView == null) {
            if (numColum == 4) {
                if (DataLocal.isTablet) {
                    convertView = mInflater.inflate(Rconfig.getInstance()
                                    .layout("core_item_gridview_productcategory"),
                            null, false);
                } else {
                    convertView = mInflater
                            .inflate(
                                    Rconfig.getInstance()
                                            .layout("core_item_gridview_productcategory_change"),
                                    null, false);
                }
            } else {
                if (DataLocal.isTablet) {
                    convertView = mInflater
                            .inflate(
                                    Rconfig.getInstance()
                                            .layout("core_item_gridview_productcategory_change"),
                                    null, false);
                } else {
                    convertView = mInflater.inflate(Rconfig.getInstance()
                                    .layout("core_item_gridview_productcategory"),
                            null, false);
                }
            }
            layout_image = (RelativeLayout) convertView.findViewById(Rconfig
                    .getInstance().id("rel_product_list"));


            if (numColum == 2) {
                layout_image.setLayoutParams(paramsLayout2);
            } else if (numColum == 4) {
                if (DataLocal.isTablet) {
                    layout_image.setLayoutParams(paramsLayout4Phone);
                } else {
                    layout_image.setLayoutParams(paramsLayout4Tablet);
                }
            } else {
                layout_image.setLayoutParams(paramsLayout6);
            }

            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(Rconfig
                    .getInstance().id("tv_name"));
            holder.tv_name.setTextColor(Config.getInstance()
                    .getContent_color());
            holder.ll_price = (LinearLayout) convertView.findViewById(Rconfig
                    .getInstance().id("ll_price"));
            holder.tv_first = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_fist_price"));
            holder.tv_second = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_second_price"));
            holder.img_avartar = (ImageView) convertView.findViewById(Rconfig
                    .getInstance().id("img_avartar"));
            holder.layout_stock = (LinearLayout) convertView
                    .findViewById(Rconfig.getInstance().id("layout_stock"));
            holder.layout_stock.setBackgroundColor(Config.getInstance()
                    .getOut_stock_background());

            holder.layout_header = (RelativeLayout) convertView
                    .findViewById(Rconfig.getInstance()
                            .id("layout_header_grid"));
            holder.txt_outstock = (TextView) convertView.findViewById(Rconfig
                    .getInstance().id("txt_out_stock"));
            holder.txt_outstock.setTextColor(Config.getInstance()
                    .getOut_stock_text());
            if (DataLocal.isTablet) {
                holder.txt_price_tablet = (TextView) convertView
                        .findViewById(Rconfig.getInstance().id("tv_excl"));
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (pos < numColum) {
            holder.layout_header.setVisibility(View.VISIBLE);
        } else {
            holder.layout_header.setVisibility(View.GONE);
        }

        ProductEntity product = mListProduct.get(pos);
        if (DataLocal.isTablet) {
            holder.txt_price_tablet.setText(Config.getInstance().getPrice(
                    product.getPrice()));
        }

        holder.layout_stock.setVisibility(View.GONE);

        if (holder.ll_price != null) {
            createPriceWithoutTax(holder, product);
        }

        String name = product.getName();
        if (null != name) {
            name.trim();
            holder.tv_name.setText(name);
        } else {
            holder.tv_name.setText("SimiCart");
        }

        if (product.getImages() != null) {
            if (product.getImages().size() > 0) {
                DrawableManager.fetchDrawableOnThread(product.getImages().get(0),
                        holder.img_avartar);
            }
        }

        RelativeLayout rl_product_list = (RelativeLayout) convertView
                .findViewById(Rconfig.getInstance().id("rel_product_list"));
        Intent intent = new Intent("com.simicart.image.product.grid");
        SimiEventBlockEntity blockEntity = new SimiEventBlockEntity();
        blockEntity.setView(rl_product_list);
        blockEntity.setEntity(product);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.ENTITY, blockEntity);
        intent.putExtra(Constants.DATA, bundle);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


        return convertView;
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
            String sPrice = getPrice(mPrice);
            if (Utils.validateString(sPrice)) {
                holder.tv_first.setText(sPrice);
            }
        } else {
            if (mSalePrice == 0) {
                holder.tv_second.setVisibility(View.GONE);
                holder.tv_first.setText(getPrice(mPrice));
            } else {
                holder.tv_second.setText(getPrice(mSalePrice));
                holder.tv_first.setPaintFlags(holder.tv_first.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_first.setText(getPrice(mPrice));
            }
        }
    }

    protected String getPrice(double price) {
        return Config.getInstance().getPrice(price);
    }
}
