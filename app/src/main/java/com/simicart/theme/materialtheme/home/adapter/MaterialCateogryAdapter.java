package com.simicart.theme.materialtheme.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.config.Rconfig;

import java.util.List;

/**
 * Created by Sony on 3/26/2016.
 */
public class MaterialCateogryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> contents;

    static final int TYPE_CELL = 1;

    public MaterialCateogryAdapter(List<Object> contents) {
        this.contents = contents;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        Log.e("MaterialCateogryAdapter", "Type"+viewType  );
        switch (viewType) {

            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(Rconfig.getInstance().layout("material_category_item_layout"), parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_CELL:
                break;
        }
    }
}