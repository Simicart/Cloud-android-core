package com.simicart.plugins.checkout.com.delegate;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.plugins.checkout.com.entity.CheckoutComEntity;

/**
 * Created by James Crabby on 1/15/2016.
 */
public interface CheckoutComDelegate extends SimiDelegate {
    public CheckoutComEntity getCheckoutComData();
}
