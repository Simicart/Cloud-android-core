package com.simicart.core.checkout.delegate;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.entity.TotalPrice;
import com.simicart.core.material.ButtonRectangle;

public interface CartDelegate extends SimiDelegate {

	public void onUpdateTotalPrice(QuoteEntity totalPrice);

	public void showPopupCheckout();

	public void dismissPopupCheckout();

	public void setMessage(String message);
	
	public void visibleAllView ();
}
