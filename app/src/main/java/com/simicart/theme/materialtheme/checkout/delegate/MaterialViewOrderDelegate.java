package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;

/**
 * Created by Sony on 4/19/2016.
 */
public interface MaterialViewOrderDelegate extends SimiDelegate {
    public void showCouponCode(QuoteEntity quote);

    public void removeTextCouponCode();

    public void showHidenRemoveCoupon(int type);
}
