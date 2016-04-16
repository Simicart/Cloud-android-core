package com.simicart.theme.materialtheme.checkout.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.simicart.core.checkout.entity.ShippingMethod;
import com.simicart.core.common.Utils;
import com.simicart.core.common.ViewIdGenerator;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by Sony on 4/15/2016.
 */
public class MaterialShippingMethodAdapter extends RecyclerView.Adapter<MaterialShippingMethodAdapter.ViewHolder> {
    private ArrayList<ShippingMethod> listShippingMethod;
    private Context mContext;
    protected int mIDIconNormal;
    protected int mIDIconChecked;
    private OnItemClickListener listener;

    public MaterialShippingMethodAdapter(ArrayList<ShippingMethod> listShippingMethod, Context mContext) {
        this.mContext = mContext;
        this.listShippingMethod = listShippingMethod;
        mIDIconNormal = Rconfig.getInstance().drawable("core_radiobox");
        mIDIconChecked = Rconfig.getInstance().drawable("core_radiobox2");
    }

    public void setListShippingMethod(ArrayList<ShippingMethod> listShippingMethod) {
        this.listShippingMethod = listShippingMethod;
    }

    public interface OnItemClickListener {
        void onItemClick(ShippingMethod item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(Rconfig.getInstance().layout("material_item_shipping_method"), parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ShippingMethod shippingMethod = listShippingMethod.get(position);

        Drawable icon = null;
        if (shippingMethod.getIsSelected()) {
            icon = mContext.getResources().getDrawable(
                    mIDIconChecked);
        } else {
            icon = mContext.getResources().getDrawable(
                    mIDIconNormal);
        }

        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        holder.shipping_checkbox.setImageDrawable(icon);
        holder.shipping_checkbox.setId(ViewIdGenerator.generateViewId());

        holder.shipping_title.setText(shippingMethod.getServiceName());
        if (!DataLocal.isLanguageRTL) {
            holder.shipping_title.setGravity(Gravity.LEFT);
        } else {
            holder.shipping_title.setGravity(Gravity.RIGHT);
        }

        String name = shippingMethod.getDescription();
        if (name == null || name.equals("null")) {
            holder.shipping_name.setVisibility(View.GONE);
        } else {
            holder.shipping_name.setText(name);
        }

        String s_incl_tax = shippingMethod.getS_method_fee_incl_tax();
        Float price = shippingMethod.getPrice();
        Float incl_tax = null;
        try {
            incl_tax = Float.parseFloat(s_incl_tax);
        } catch (Exception e) {
            incl_tax = null;
        }


        if (incl_tax == null) {
            holder.shipping_price.setText(Config.getInstance().getPrice(price));
            holder.shipping_price.setTextColor(Color.parseColor(Config.getInstance()
                    .getPrice_color()));
        } else {
            String price_method = "<font  color='"
                    + Config.getInstance().getPrice_color() + "'>"
                    + Config.getInstance().getPrice(price)
                    + "</font> (<font color='grey'>+"
                    + Config.getInstance().getText("Incl. Tax")
                    + "</font> <font  color='"
                    + Config.getInstance().getPrice_color() + "'> "
                    + Config.getInstance().getPrice(incl_tax) + "</font>)";
            holder.shipping_price.setText(Html.fromHtml(price_method));
        }

        holder.cv_shipping_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    updateCheck(shippingMethod);
                    listener.onItemClick(shippingMethod, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listShippingMethod.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_shipping_method;
        private ImageView shipping_checkbox;
        private TextView shipping_title;
        private TextView shipping_name;
        private TextView shipping_price;

        public ViewHolder(View v) {
            super(v);

            cv_shipping_method = (CardView) v.findViewById(Rconfig.getInstance().id("cv_shipping_method"));
            shipping_checkbox = (ImageView) v.findViewById(Rconfig.getInstance().id("shipping_checkbox"));
            shipping_title = (TextView) v.findViewById(Rconfig.getInstance().id("shipping_title"));
            shipping_name = (TextView) v.findViewById(Rconfig.getInstance().id("shipping_name"));
            shipping_price = (TextView) v.findViewById(Rconfig.getInstance().id("shipping_price"));
        }
    }

    public void updateCheck(ShippingMethod cShippingMethod){
        cShippingMethod.setIsSelected(true);
        for (int i = 0; i < listShippingMethod.size(); i++){
            ShippingMethod shippingItem = listShippingMethod.get(i);
            if(!cShippingMethod.equals(shippingItem)){
                shippingItem.setIsSelected(false);
            }
        }
        notifyDataSetChanged();
    }
}
