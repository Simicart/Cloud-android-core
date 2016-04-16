package com.simicart.theme.materialtheme.checkout.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialViewOrderAdapter extends RecyclerView.Adapter<MaterialViewOrderAdapter.ViewHolder> {
    protected ArrayList<ProductEntity> listProduct;

    public MaterialViewOrderAdapter(ArrayList<ProductEntity> listProduct) {
        this.listProduct = listProduct;
    }

    public void setListProduct(ArrayList<ProductEntity> listProduct) {
        this.listProduct = listProduct;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(Rconfig.getInstance().layout("material_item_view_order"), parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductEntity product = listProduct.get(position);

        if(product.getImages() != null && product.getImages().size() > 0) {
            String img = product.getImages().get(0);
            DrawableManager.fetchDrawableOnThread(img, holder.item_view_order_image);
        }

        String name = product.getName();
        holder.item_view_order_name.setText(name);

        String qty = Config.getInstance().getText("Qty") + "." + " (" + product.getQty() + ")" ;
        holder.item_view_order_qty.setText(qty);

        String price = Config.getInstance().getPrice(
                product.getPrice());
        holder.item_view_order_price.setText(price);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_view_order;
        private ImageView item_view_order_image;
        private TextView item_view_order_name;
        private TextView item_view_order_qty;
        private TextView item_view_order_price;

        public ViewHolder(View v) {
            super(v);
            v.setBackgroundColor(Config.getInstance().getApp_backrground());
            cv_view_order = (CardView) v.findViewById(Rconfig.getInstance().id("cv_view_order"));
            item_view_order_image = (ImageView) v.findViewById(Rconfig.getInstance().id("item_view_order_image"));
            item_view_order_name = (TextView) v.findViewById(Rconfig.getInstance().id("item_view_order_name"));
            item_view_order_name.setTextColor(Config.getInstance().getContent_color());
            item_view_order_qty = (TextView) v.findViewById(Rconfig.getInstance().id("item_view_order_qty"));
            item_view_order_qty.setTextColor(Config.getInstance().getContent_color());
            item_view_order_price = (TextView) v.findViewById(Rconfig.getInstance().id("item_view_order_price"));
            item_view_order_price.setTextColor(Color.parseColor(Config.getInstance()
                    .getPrice_color()));
        }
    }
}
