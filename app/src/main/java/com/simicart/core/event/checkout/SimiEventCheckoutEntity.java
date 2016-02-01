package com.simicart.core.event.checkout;

import com.simicart.core.checkout.entity.OrderEntity;
import com.simicart.core.checkout.entity.PaymentMethod;

import java.io.Serializable;

/**
 * Created by MSI on 28/01/2016.
 */
public class SimiEventCheckoutEntity implements Serializable {

    private String mInvoiceNumber;
    private PaymentMethod mPaymentMethod;
    private OrderEntity mOder;


    public String getInvoiceNumber() {
        return mInvoiceNumber;
    }

    public void setInvoiceNumber(String mInvoiceNumber) {
        this.mInvoiceNumber = mInvoiceNumber;
    }

    public OrderEntity getOder() {
        return mOder;
    }

    public void setOder(OrderEntity mOder) {
        this.mOder = mOder;
    }

    public PaymentMethod getPaymentMethod() {
        return mPaymentMethod;
    }

    public void setPaymentMethod(PaymentMethod mPaymentMethod) {
        this.mPaymentMethod = mPaymentMethod;
    }
}
