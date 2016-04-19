package com.simicart.theme.materialtheme.checkout.adapter;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.checkout.entity.Condition;
import com.simicart.core.checkout.fragment.ConditionFragment;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;

import java.util.ArrayList;

/**
 * Created by Sony on 4/19/2016.
 */
public class MaterialViewOrderTermConditionAdapter extends RecyclerView.Adapter<MaterialViewOrderTermConditionAdapter.ViewHolder>{
    private ArrayList<Condition> listCondition;

    public MaterialViewOrderTermConditionAdapter(ArrayList<Condition> listCondition){
        this.listCondition = listCondition;
    }

    public void setListCondition(ArrayList<Condition> listCondition) {
        this.listCondition = listCondition;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(Rconfig.getInstance().layout("material_item_term_and_condition"), parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Condition condition = listCondition.get(position);

        String title = condition.getTitle();
        final String content =  condition.getContent();
        String full_condition = title + content;
        holder.tv_term_condition.setText(Html.fromHtml(full_condition).toString());

        String cb_title = condition.getCheckText();
        holder.cb_term_condition.setText(cb_title);

        holder.tv_term_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConditionFragment fragment = ConditionFragment
                        .newInstance();
                fragment.setContent(content);
                SimiManager.getIntance().replacePopupFragment(fragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCondition.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cv_term_condition;
        private TextView tv_term_condition;
        private ImageView im_arrow_right;
        private CheckBox cb_term_condition;

        public ViewHolder(View v) {
            super(v);
            cv_term_condition = (CardView) v.findViewById(Rconfig.getInstance().id("cv_term_condition"));
            tv_term_condition = (TextView) v.findViewById(Rconfig.getInstance().id("tv_term_condition"));

            im_arrow_right = (ImageView) v.findViewById(Rconfig.getInstance().id("im_arrow_right"));
            Drawable icon = v.getContext().getResources().getDrawable(
                    Rconfig.getInstance().drawable("ic_action_expand"));
            icon.setColorFilter(Config.getInstance().getContent_color(),
                    PorterDuff.Mode.SRC_ATOP);
            im_arrow_right.setImageDrawable(icon);

            cb_term_condition = (CheckBox) v.findViewById(Rconfig.getInstance().id("cb_term_condition"));
            cb_term_condition.setTextColor(Config.getInstance().getContent_color());
        }
    }
}
