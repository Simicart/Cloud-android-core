package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.checkout.entity.ShippingMethod;

import java.util.ArrayList;

/**
 * Created by Sony on 4/13/2016.
 */
public interface MaterialShippingMethodManagerDelegate {
    public void showShippingMethod(ArrayList<ShippingMethod> listShippingMethod);

    public void selectedShippingMethod(ShippingMethod shippingMethod);
}
