package com.simicart.core.checkout.delegate;

import android.widget.LinearLayout;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.checkout.entity.CreditcardEntity;
import com.simicart.core.checkout.entity.PaymentMethod;

import kankan.wheel.widget.WheelView;

public interface CreditCardDelegate extends SimiDelegate {

    public CreditcardEntity getCreditCard();

	public PaymentMethod getPaymentMethod();

	public WheelView getWheelViewCardType();

	public LinearLayout getCardDateLayout();

}
