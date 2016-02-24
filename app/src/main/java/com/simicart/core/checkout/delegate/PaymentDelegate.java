package com.simicart.core.checkout.delegate;

import com.simicart.core.checkout.entity.PaymentMethod;

/**
 * Created by MSI on 04/02/2016.
 */
public interface PaymentDelegate {

    public void updatePaymentChecked(PaymentMethod payment);

}
