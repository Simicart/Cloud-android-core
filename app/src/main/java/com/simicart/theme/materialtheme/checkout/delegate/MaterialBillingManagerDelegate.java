package com.simicart.theme.materialtheme.checkout.delegate;

import com.simicart.core.customer.entity.MyAddress;

/**
 * Created by Sony on 4/12/2016.
 */
public interface MaterialBillingManagerDelegate {
    public void onSelectedBilling(MyAddress address);

    public void chooseShipToAddress(int type);
}
