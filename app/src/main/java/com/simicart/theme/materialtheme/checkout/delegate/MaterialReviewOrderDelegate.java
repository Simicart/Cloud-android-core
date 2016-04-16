package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.customer.entity.MyAddress;

/**
 * Created by Sony on 4/12/2016.
 */
public interface MaterialReviewOrderDelegate extends SimiDelegate{
    public void showShippingInformation(int type);

    public void showShippingMethod();

    public void showPaymentInformation();

}
