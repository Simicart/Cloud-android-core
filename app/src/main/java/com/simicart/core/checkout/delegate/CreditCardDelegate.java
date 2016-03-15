package com.simicart.core.checkout.delegate;

import android.widget.LinearLayout;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.checkout.entity.PaymentMethod;

import kankan.wheel.widget.WheelView;

public interface CreditCardDelegate extends SimiDelegate {

	public String getCardType();

	public String getCVV();

	public String getExpired();

	public String getCardNumber();

	public String getCardName();

	public PaymentMethod getPaymentMethod();

	public WheelView getWheelViewCardType();

	public LinearLayout getCardDateLayout();

}
