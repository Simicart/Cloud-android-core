package com.simicart.theme.materialtheme.checkout.block;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.simicart.core.base.block.SimiBlock;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialBillingInformationDelegate;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialBillingInformationBlock extends SimiBlock implements MaterialBillingInformationDelegate{
    protected TextView tv_billing_information;
    protected TextView tv_choose_ship_to_address;
    protected TextView tv_ship_to_different_address;

    public MaterialBillingInformationBlock(View view, Context context){
        super(view, context);
    }

    @Override
    public void initView() {
        tv_billing_information = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_billing_information"));
        tv_billing_information.setText("2" + " " + Config.getInstance().getText("Billing Information"));
        tv_billing_information.setTextColor(Config.getInstance().getSection_text_color());
        tv_billing_information.setBackgroundColor((Color.parseColor(Config.getInstance().getSection_color())));

        tv_choose_ship_to_address = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_choose_ship_to_address"));
        tv_choose_ship_to_address.setText(Config.getInstance().getText("Ship to this address"));

        tv_ship_to_different_address = (TextView) mView.findViewById(Rconfig.getInstance().id("tv_ship_to_different_address"));
        tv_ship_to_different_address.setText(Config.getInstance().getText("Ship to different address"));
    }
}
