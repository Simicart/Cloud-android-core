package com.simicart.core.checkout.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.delegate.CartDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.model.CartModel;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;

public class CartController extends SimiController {

    protected CartDelegate mDelegate;
    protected QuoteEntity mTotal;

    public CartController() {

    }

    @Override
    public void onStart() {
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("") || !DataLocal.getQuoteCustomerNotSigin().equals("")) {
            request();
        } else {
            mDelegate.visibleAllView();
        }
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

    @Override
    public void onResume() {
        request();
//        mDelegate.updateView(mModel.getCollection());
//        mDelegate.onUpdateTotalPrice(mTotal);
    }

    public void setDelegate(CartDelegate delegate) {
        mDelegate = delegate;
    }

}
