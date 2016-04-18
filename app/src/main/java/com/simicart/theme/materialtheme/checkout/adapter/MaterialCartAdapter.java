package com.simicart.theme.materialtheme.checkout.adapter;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by Sony on 4/11/2016.
 */
public class MaterialCartAdapter extends RecyclerView.Adapter<MaterialCartAdapter.ViewHolder> {
    private ArrayList<ProductEntity> mListProduct;
    private onItemClickListener onClickListener;
    private onClickDeleteItemCart onClickDeleteListener;
    private onChangeQtyItemCart onChangeQtyListener;

    public MaterialCartAdapter(ArrayList<ProductEntity> listProduct) {
        mListProduct = listProduct;
    }

    public void setListProduct(ArrayList<ProductEntity> products) {
        mListProduct = products;
    }

    public void setOnClickListener(onItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnClickDeleteListener(onClickDeleteItemCart onClickDeleteListener) {
        this.onClickDeleteListener = onClickDeleteListener;
    }

    public void setOnChangeQtyListener(onChangeQtyItemCart onChangeQtyListener) {
        this.onChangeQtyListener = onChangeQtyListener;
    }

    public interface onClickDeleteItemCart{
        void onItemDelete(ProductEntity item, int position);
    }

    public interface onItemClickListener {
        void onItemClick(ArrayList<ProductEntity> mListProduct, ProductEntity item, int position);
    }

    public interface onChangeQtyItemCart{
        void onChangeQty(int Qty, ProductEntity item, int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(Rconfig.getInstance().layout("material_item_card_layout"), parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ProductEntity product = mListProduct.get(position);

        ArrayList<String> images = product.getImages();
        if (null != images && images.size() > 0) {
            String url = images.get(0);
            DrawableManager.fetchDrawableOnThread(url, holder.item_cart_image);
        }

        String name = product.getName();
        holder.item_cart_name.setText(name);

        String qty = Config.getInstance().getText("Qty") + "." + " (" + product.getQty() + ")" ;
        holder.item_cart_qty.setText(qty);

        String price = Config.getInstance().getPrice(product.getPrice());
        holder.item_cart_price.setText(price);

        holder.rl_item_cart_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickDeleteListener != null){
                    onClickDeleteListener.onItemDelete(product, position);
                }
            }
        });

        holder.item_cart_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickListener != null){
                    onClickListener.onItemClick(mListProduct, product, position);
                }
            }
        });

        holder.item_cart_qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onChangeQtyListener != null){
                    int Qty = product.getQty();
                    onChangeQtyListener.onChangeQty(Qty, product, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_cart_list;
        private ImageView item_cart_image;
        private TextView item_cart_name;
        private TextView item_cart_option;
        private TextView item_cart_qty;
        private TextView item_cart_price;
        private RelativeLayout rl_item_cart_del;
        private ImageView item_cart_del;

        public ViewHolder(View v) {
            super(v);
            cv_cart_list = (CardView) v.findViewById(Rconfig.getInstance().id("cv_cart_list"));
            item_cart_image = (ImageView) v.findViewById(Rconfig.getInstance().id("item_cart_image"));
            item_cart_name = (TextView) v.findViewById(Rconfig.getInstance().id("item_cart_name"));
            item_cart_name.setTextColor(Config.getInstance().getContent_color());
            item_cart_option = (TextView) v.findViewById(Rconfig.getInstance().id("item_cart_option"));
            item_cart_option.setTextColor(Config.getInstance().getContent_color());
            item_cart_qty = (TextView) v.findViewById(Rconfig.getInstance().id("item_cart_qty"));
            item_cart_qty.setTextColor(Config.getInstance().getContent_color());
            item_cart_price = (TextView) v.findViewById(Rconfig.getInstance().id("item_cart_price"));
            item_cart_price.setTextColor(Color.parseColor(Config.getInstance()
                    .getPrice_color()));
            rl_item_cart_del = (RelativeLayout) v.findViewById(Rconfig.getInstance().id("rl_item_cart_del"));
            item_cart_del = (ImageView) v.findViewById(Rconfig.getInstance().id("item_cart_del"));
            Drawable icon = v.getContext().getResources().getDrawable(
                    Rconfig.getInstance().drawable("ic_delete"));
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            item_cart_del.setImageDrawable(icon);
        }
    }
}
