package com.simicart.theme.materialtheme.categorydetail.adapter;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.common.DrawableManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by MSI on 26/03/2016.
 */
public class MaterialCateDetailAdapter extends RecyclerView.Adapter<MaterialCateDetailAdapter.ViewHolder> {

    private ArrayList<ProductEntity> mListProduct;
    private LinearLayout.LayoutParams mParam;
    private ArrayList<String> mListID;

    public void setListProduct(ArrayList<ProductEntity> products) {
        mListProduct = products;
        mListID = getListIDProduct();
    }

    public MaterialCateDetailAdapter(ArrayList<ProductEntity> listProduct) {
        mListProduct = listProduct;
        calHeight();
        mListID = getListIDProduct();
    }

    private void calHeight() {
        Display display = SimiManager.getIntance().getCurrentActivity()
                .getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float density = SimiManager.getIntance().getCurrentActivity()
                .getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;

        int mHeight = (int) (dpHeight * 2) / 3;
        mHeight = Utils.getValueDp(mHeight);

        mParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mHeight);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(Rconfig.getInstance().layout("material_item_categorydetail_list"), parent, false);
        itemView.setLayoutParams(mParam);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductEntity product = mListProduct.get(position);

        // image
        ArrayList<String> images = product.getImages();
        if (null != images && images.size() > 0) {
            String url = images.get(0);
            DrawableManager.fetchDrawableOnThread(url, holder.img_item);
        }

        // name
        String name = product.getName();
        if (Utils.validateString(name)) {
            holder.tv_name.setText(name);
        }

        // price
        createPriceWithoutTax(holder, product);

        // add to cart
        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddToCart(product);
            }
        });


        // select item
        holder.cv_categorydetail_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProductDetail(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListProduct.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_categorydetail_list;
        private ImageView img_item;
        private TextView tv_name;
        private TextView tv_price1;
        private TextView tv_price2;
        private Button btn_add_to_cart;

        public ViewHolder(View itemView) {
            super(itemView);
            cv_categorydetail_list = (CardView) itemView.findViewById(Rconfig.getInstance().id("cv_categorydetail_list"));
            img_item = (ImageView) itemView.findViewById(Rconfig.getInstance().id("img_item"));
            tv_name = (TextView) itemView.findViewById(Rconfig.getInstance().id("tv_name"));
            tv_price1 = (TextView) itemView.findViewById(Rconfig.getInstance().id("tv_price1"));
            tv_price2 = (TextView) itemView.findViewById(Rconfig.getInstance().id("tv_price2"));
            btn_add_to_cart = (Button) itemView.findViewById(Rconfig.getInstance().id("btn_addtocart"));
        }
    }

    protected void createPriceWithoutTax(ViewHolder holder, ProductEntity product) {
        holder.tv_price1.setPaintFlags(holder.tv_price1.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        holder.tv_price1.setVisibility(View.VISIBLE);
        holder.tv_price1.setTextColor(Color.parseColor(Config.getInstance().getPrice_color()));
        holder.tv_price2.setVisibility(View.VISIBLE);
        holder.tv_price2.setTextColor(Color.parseColor(Config.getInstance().getSpecial_price_color()));

        double mPrice = product.getPrice();
        double mSalePrice = product.getSalePrice();
        if (mPrice == mSalePrice) {
            holder.tv_price2.setVisibility(View.GONE);
            String sPrice = Config.getInstance().getPrice(mPrice);
            if (Utils.validateString(sPrice)) {
                holder.tv_price1.setText(sPrice);
            }
        } else {
            if (mSalePrice == 0) {
                holder.tv_price2.setVisibility(View.GONE);
                holder.tv_price1.setText(Config.getInstance().getPrice(mPrice));
            } else {
                holder.tv_price2.setText(Config.getInstance().getPrice(mSalePrice));
                holder.tv_price1.setPaintFlags(holder.tv_price1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_price1.setText(Config.getInstance().getPrice(mPrice));
            }
        }
    }

    protected void onAddToCart(ProductEntity product) {

    }


    protected void openProductDetail(ProductEntity product) {
        String productId = product.getID();
        if (productId != null) {
            ProductDetailParentFragment fragment = ProductDetailParentFragment
                    .newInstance();
            fragment.setProductID(productId);
            fragment.setListIDProduct(mListID);
            SimiManager.getIntance().replaceFragment(fragment);
        }

    }

    protected ArrayList<String> getListIDProduct() {
        ArrayList<String> listID = new ArrayList<>();

        for (int i = 0; i < mListProduct.size(); i++) {
            ProductEntity product = mListProduct.get(i);
            String id = product.getID();
            listID.add(id);
        }

        return listID;
    }


}
