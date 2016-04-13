package com.simicart.theme.materialtheme.checkout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.entity.MyAddress;

import java.util.ArrayList;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialAddressAdapter extends BaseAdapter{
    protected ArrayList<MyAddress> listAddress;
    protected Context context;
    protected LayoutInflater mInflater;

    public MaterialAddressAdapter(Context context, ArrayList<MyAddress> listAddress){
        this.context = context;
        this.listAddress = listAddress;
        mInflater = LayoutInflater.from(context);
    }

    public void setListAddress(ArrayList<MyAddress> listAddress) {
        this.listAddress = listAddress;
    }

    @Override
    public int getCount() {
        return listAddress.size();
    }

    @Override
    public Object getItem(int position) {
        return listAddress.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater
                .inflate(
                        Rconfig.getInstance().layout(
                                "material_item_address_layout"), null);
        MyAddress myAddress = listAddress.get(position);

        TextView tv_address_name = (TextView) convertView.findViewById(Rconfig.getInstance().id("tv_address_name"));

        // name
        String first_name = myAddress.getFirstName();
        String last_name = myAddress.getLastName();
        String name = first_name + " " + last_name;

        tv_address_name.setText(name);

        return convertView;
    }
}
