package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.customer.entity.MyAddress;

/**
 * Created by Sony on 4/13/2016.
 */
public interface MaterialShippingManagerDelegate {
    public void onSelectedShipping(MyAddress address);
}
