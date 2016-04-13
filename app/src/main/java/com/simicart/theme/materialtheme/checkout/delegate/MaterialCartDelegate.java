package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;

/**
 * Created by Sony on 4/11/2016.
 */
public interface MaterialCartDelegate extends SimiDelegate {
    public void onUpdateTotalPrice(QuoteEntity totalPrice);

    public void setMessage(String message);

    public void visibleAllView();
}
