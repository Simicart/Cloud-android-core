package com.simicart.core.checkout.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.delegate.CartDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.checkout.model.CartModel;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.customer.fragment.SignInFragment;

public class CartController extends SimiController {

    protected CartDelegate mDelegate;
    protected QuoteEntity mTotal;
    protected View.OnTouchListener onCancel;
    protected View.OnTouchListener onExcustomer;
    protected View.OnTouchListener onNewcustomer;
    protected View.OnTouchListener onAsguest;
    protected View.OnClickListener mCartListener;

    public View.OnClickListener getCartListener()
    {
        return mCartListener;
    }

    public CartController() {

    }

    @Override
    public void onStart() {
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("") || !DataLocal.getQuoteCustomerNotSigin().equals("")) {
            request();
        } else {
            mDelegate.visibleAllView();
        }

        mCartListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickCart();
            }
        };

        cancelAction();
        exCustomerAction();
        newCustomerAction();
        guestAction();
    }


    private void request() {
        mModel = new CartModel();
        mDelegate.showLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (collection != null && collection.getCollection().size() > 0) {
                    mDelegate.updateView(collection);
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    int newQtyCart = cart.getQty();
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(newQtyCart));
                    mTotal = cart;
                    mDelegate.onUpdateTotalPrice(cart);
                }
            }
        };
        mModel.setDelegate(delegate);
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        mModel.request();
    }

    private void processClickCart() {
        if (DataLocal.isSignInComplete()) {
            AddressBookCheckoutFragment fragment = AddressBookCheckoutFragment
                    .newInstance();
            fragment.setAddressFor(AddressBookCheckoutFragment.ALL_ADDRESS);
            if (DataLocal.isTablet) {
                SimiManager.getIntance().replacePopupFragment(fragment);
            } else {
                SimiManager.getIntance().replaceFragment(fragment);
            }
        } else {
            ProgressDialog popup = createPopupCheckout();
            mDelegate.showPopupCheckout(popup);
        }
    }

    private ProgressDialog createPopupCheckout() {

        Context context = SimiManager.getIntance().getCurrentContext();
        ProgressDialog pp_checkout = ProgressDialog.show(context, null, null, true,
                false);

        pp_checkout.setContentView(Rconfig.getInstance().layout(
                "core_popup_checkout_layout"));
        pp_checkout.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pp_checkout.setCanceledOnTouchOutside(false);
        pp_checkout.show();

        TextView tv_cancel = (TextView) pp_checkout.findViewById(Rconfig.getInstance()
                .id("method_cancel"));
        tv_cancel.setText(Config.getInstance().getText("Cancel"));
        tv_cancel.setOnTouchListener(onCancel);

        TextView tv_excustomer = (TextView) pp_checkout.findViewById(Rconfig
                .getInstance().id("method_excustomer"));
        tv_excustomer.setText(Config.getInstance().getText(
                "Checkout as existing customer"));
        tv_excustomer
                .setOnTouchListener(onExcustomer);

        TextView tv_newcustomer = (TextView) pp_checkout.findViewById(Rconfig
                .getInstance().id("method_newcustomer"));
        tv_newcustomer.setText(Config.getInstance().getText(
                "Checkout as new customer"));
        tv_newcustomer.setOnTouchListener(onNewcustomer);

        TextView tv_guest = (TextView) pp_checkout.findViewById(Rconfig.getInstance()
                .id("method_guest"));
        if (Config.getInstance().getGuest_checkout() == 1) {
            tv_guest.setText(Config.getInstance().getText("Checkout as guest"));
            tv_guest.setOnTouchListener(onAsguest);
        } else {
            tv_guest.setVisibility(View.GONE);
        }

        return pp_checkout;
    }


    public void cancelAction() {
        onCancel = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(Color.parseColor("#EBEBEB"));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mDelegate.dismissPopupCheckout();
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        v.setBackgroundColor(0);
                        break;
                    }
                }
                return true;
            }
        };
    }

    public void exCustomerAction() {
        onExcustomer = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(Color.parseColor("#EBEBEB"));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mDelegate.dismissPopupCheckout();
                        SignInFragment fragment = SignInFragment.newInstance();
                        fragment.setCheckout(true);
                        if (DataLocal.isTablet) {
                            SimiManager.getIntance().replacePopupFragment(fragment);
                        } else {
                            SimiManager.getIntance().replaceFragment(fragment);
                        }
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        v.setBackgroundColor(0);
                        break;
                    }
                }
                return true;
            }
        };
    }

    public void newCustomerAction() {
        onNewcustomer = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(Color.parseColor("#EBEBEB"));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mDelegate.dismissPopupCheckout();

                        NewAddressBookFragment fragment = NewAddressBookFragment
                                .newInstance();
                        fragment.setAfterControler(NewAddressBookFragment.NEW_CUSTOMER);
                        if (DataLocal.isTablet) {
                            SimiManager.getIntance().replacePopupFragment(fragment);
                        } else {
                            SimiManager.getIntance().replaceFragment(fragment);
                        }
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        v.setBackgroundColor(0);
                        break;
                    }
                }
                return true;
            }
        };
    }

    public void guestAction() {
        onAsguest = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(Color.parseColor("#EBEBEB"));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        mDelegate.dismissPopupCheckout();

                        NewAddressBookFragment fragment = NewAddressBookFragment
                                .newInstance();
                        fragment.setAfterControler(NewAddressBookFragment.NEW_AS_GUEST);
                        if (DataLocal.isTablet) {
                            SimiManager.getIntance().replacePopupFragment(fragment);
                        } else {
                            SimiManager.getIntance().replaceFragment(fragment);
                        }
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        v.setBackgroundColor(0);
                        break;
                    }
                }
                return true;
            }
        };
    }


    @Override
    public void onResume() {
        request();
    }

    public void setDelegate(CartDelegate delegate) {
        mDelegate = delegate;
    }

}
