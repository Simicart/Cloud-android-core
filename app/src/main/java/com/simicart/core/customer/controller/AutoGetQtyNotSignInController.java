package com.simicart.core.customer.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.model.CartModel;
import com.simicart.core.config.DataLocal;

/**
 * Created by Sony on 12/18/2015.
 */
public class AutoGetQtyNotSignInController extends SimiController {
    @Override
    public void onStart() {
        CartModel mModel = new CartModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(cart.getQty()));
                }
            }
        });

        mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        mModel.request();
    }

    @Override
    public void onResume() {

    }
}
