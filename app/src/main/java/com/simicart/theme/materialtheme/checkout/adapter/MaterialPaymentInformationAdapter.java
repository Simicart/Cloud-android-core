package com.simicart.theme.materialtheme.checkout.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.simicart.core.checkout.entity.PaymentMethod;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public class MaterialPaymentInformationAdapter extends RecyclerView.Adapter<MaterialPaymentInformationAdapter.ViewHolder> {
    private Context mContext;
    protected int mIDIconNormal;
    protected int mIDIconChecked;
    protected ArrayList<PaymentMethod> listPaymentMethod;
    private OnItemClickListener listener;

    public MaterialPaymentInformationAdapter(ArrayList<PaymentMethod> listPaymentMethod, Context mContext) {
        this.mContext = mContext;
        this.listPaymentMethod = listPaymentMethod;
        mIDIconNormal = Rconfig.getInstance().drawable("core_radiobox");
        mIDIconChecked = Rconfig.getInstance().drawable("core_radiobox2");
    }

    public void setListPaymentMethod(ArrayList<PaymentMethod> listPaymentMethod) {
        this.listPaymentMethod = listPaymentMethod;
    }

    public interface OnItemClickListener {
        void onItemClick(PaymentMethod item, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(Rconfig.getInstance().layout("material_item_payment_information"), parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PaymentMethod paymentMethod = listPaymentMethod.get(position);

        Drawable icon = null;
        if (paymentMethod.isCheck()) {
            icon = mContext.getResources().getDrawable(
                    mIDIconChecked);
        } else {
            icon = mContext.getResources().getDrawable(
                    mIDIconNormal);
        }

        icon.setColorFilter(Config.getInstance().getContent_color(),
                PorterDuff.Mode.SRC_ATOP);
        holder.payment_checkbox.setImageDrawable(icon);

        String titleMethod = paymentMethod.getTitle();
        holder.payment_title.setText(titleMethod,
                TextView.BufferType.SPANNABLE);

        String contentMethod = paymentMethod.getContent();
        if (Utils.validateString(contentMethod)) {
            holder.payment_content.setText(contentMethod,
                    TextView.BufferType.SPANNABLE);
        } else {
            holder.payment_content.setVisibility(View.GONE);
        }

        holder.cv_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    updateCheck(paymentMethod);
                    listener.onItemClick(paymentMethod, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPaymentMethod.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_payment_method;
        private ImageView payment_checkbox;
        private TextView payment_title;
        private TextView payment_content;

        public ViewHolder(View v) {
            super(v);

            cv_payment_method = (CardView) v.findViewById(Rconfig.getInstance().id("cv_payment_method"));
            payment_checkbox = (ImageView) v.findViewById(Rconfig.getInstance().id("payment_checkbox"));
            payment_title = (TextView) v.findViewById(Rconfig.getInstance().id("payment_title"));
            payment_content = (TextView) v.findViewById(Rconfig.getInstance().id("payment_content"));
        }
    }

    public void updateCheck(PaymentMethod cPaymentMethod) {
        cPaymentMethod.setIsCheck(true);
        for (int i = 0; i < listPaymentMethod.size(); i++) {
            PaymentMethod paymentItem = listPaymentMethod.get(i);
            if (!cPaymentMethod.equals(paymentItem)) {
                paymentItem.setIsCheck(false);
            }
        }
        notifyDataSetChanged();
    }
}
