package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.checkout.entity.PaymentMethod;

import java.util.ArrayList;

/**
 * Created by Sony on 4/16/2016.
 */
public interface MaterialPaymentInformationManagerDelegate {

    public void showPaymentInformation(ArrayList<PaymentMethod> listPaymentMethod);

    public void selectedPaymentMethod(PaymentMethod paymentMethod);

}
