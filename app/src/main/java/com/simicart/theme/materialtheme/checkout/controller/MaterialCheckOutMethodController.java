package com.simicart.theme.materialtheme.checkout.controller;

import android.view.View;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.customer.fragment.SignInFragment;

/**
 * Created by Sony on 4/12/2016.
 */
public class MaterialCheckOutMethodController extends SimiController {
    protected View.OnClickListener onClickCheckoutExistingCustomer;
    protected View.OnClickListener onClickCheckOutNewCustomer;
    protected View.OnClickListener onClickCheckOutAsGuest;

    public View.OnClickListener getOnClickCheckoutExistingCustomer() {
        return onClickCheckoutExistingCustomer;
    }

    public View.OnClickListener getOnClickCheckOutNewCustomer() {
        return onClickCheckOutNewCustomer;
    }

    public View.OnClickListener getOnClickCheckOutAsGuest() {
        return onClickCheckOutAsGuest;
    }

    @Override
    public void onStart() {
        onClickCheckoutExistingCustomer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInFragment fragment = SignInFragment.newInstance();
                SimiManager.getIntance().replaceFragment(fragment);
            }
        };

        onClickCheckOutNewCustomer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        onClickCheckOutAsGuest = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    @Override
    public void onResume() {

    }
}
