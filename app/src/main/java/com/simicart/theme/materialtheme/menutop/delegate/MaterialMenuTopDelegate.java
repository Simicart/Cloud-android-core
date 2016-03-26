package com.simicart.theme.materialtheme.menutop.delegate;

import com.simicart.core.base.delegate.SimiDelegate;

/**
 * Created by Sony on 3/27/2016.
 */
public interface MaterialMenuTopDelegate extends SimiDelegate{
    public void updateBackground(int color);

    public void updateCartQty(String qty);

    public void showCartLayout(boolean show);

    public void hideMenuTop(boolean isHide);
}
