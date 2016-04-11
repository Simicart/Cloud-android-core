package com.simicart.theme.materialtheme.checkout.controller;

import android.view.View;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.model.CartModel;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialCartDelegate;

/**
 * Created by Sony on 4/11/2016.
 */
public class MaterialCartController extends SimiController{
    protected MaterialCartDelegate mDelegate;
    protected View.OnClickListener mCartListener;

    public void setDelegate(MaterialCartDelegate mDelegate) {
        this.mDelegate = mDelegate;
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

            }
        };
    }

    @Override
    public void onResume() {
        request();
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
}

