package com.simicart.core.customer.block;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simicart.R;
import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.adapter.AddressBookAdapter;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;

public class AddressBookBlock extends SimiBlock {

    protected TextView tv_addAddress;
    protected ListView lv_Address;
    protected AddressBookAdapter mAdapter;
    protected boolean isCheckout;
    private RelativeLayout rlt_layout_addadress;
    private ImageView img_address_left;
    private ImageView img_address_right;

    public void setIsCheckout(boolean checkout) {
        isCheckout = checkout;
    }

    public AddressBookBlock(View view, Context context) {
        super(view, context);
    }

    public void setOnItemClicker(OnItemClickListener clicker) {
        lv_Address.setOnItemClickListener(clicker);
    }

    public void setonTouchListener(OnTouchListener listener) {
        tv_addAddress.setOnTouchListener(listener);
    }

    @Override
    public void initView() {

        rlt_layout_addadress = (RelativeLayout) mView.findViewById(Rconfig
                .getInstance().id("layout_addadress"));
        rlt_layout_addadress.setBackgroundColor(Config.getInstance()
                .getButton_background());

        // title
        TextView tv_chooseAddress = (TextView) mView.findViewById(Rconfig
                .getInstance().id("tv_chooseAddress"));
//        lv_listAddress.setBackgroundColor(Color.parseColor(Config
//                .getInstance().getSection_color()));
//        tv_chooseAddress.setTextColor(Config.getInstance()
//                .getSection_text_color());
        if (isCheckout) {
            tv_chooseAddress.setText(Config.getInstance().getText(
                    "OR CHOOSE AN ADDRESS"));
        } else {
            tv_chooseAddress.setText(Config.getInstance().getText(
                    "OR CHOOSE AN ADDRESS FOR EDITING"));
        }
        tv_addAddress = (TextView) mView.findViewById(Rconfig.getInstance().id(
                "addAddress"));
        tv_addAddress.setText(Config.getInstance().getText("Add an address"));
        tv_addAddress.setBackgroundColor(Config.getInstance()
                .getButton_background());
        tv_addAddress.setTextColor(Config.getInstance().getButton_text_color());

        img_address_left = (ImageView) mView.findViewById(Rconfig.getInstance()
                .id("iv_add"));
        Utils.changeColorImageview(mContext, img_address_left, "ic_action_add");

        img_address_right = (ImageView) mView.findViewById(Rconfig
                .getInstance().id("iv_extend"));
        Utils.changeColorImageview(mContext, img_address_right, "ic_extend");

        // list address
        lv_Address = (ListView) mView.findViewById(Rconfig.getInstance().id(
                "lv_listAddress"));
//        ColorDrawable sage = new ColorDrawable(Config.getInstance()
//                .getLine_color());
//        lv_Address.setDivider(sage);
//        lv_Address.setDividerHeight(1);
    }

    @Override
    public void drawView(SimiCollection collection) {
        if (collection != null && collection.getCollection().size() > 0) {
            ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
            if (null != entity) {
                ArrayList<MyAddress> address = new ArrayList<MyAddress>();
                if (entity.getAddress() != null) {
                    if (entity.getAddress().size() > 0) {
                        for (SimiEntity simiEntity : entity.getAddress()) {
                            MyAddress addr = (MyAddress) simiEntity;
                            address.add(addr);
                        }
                        if (address.size() > 0) {
                            if (null == mAdapter) {
                                mAdapter = new AddressBookAdapter(mContext, address);
                                lv_Address.setAdapter(mAdapter);
                            } else {
                                mAdapter.setAddress(address);
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        }
    }
}
