package com.simicart.core.customer.delegate;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.material.ButtonRectangle;

/**
 * Created by Sony on 1/19/2016.
 */
public interface ChangePasswordDelegate extends SimiDelegate{
    public ProfileEntity getProfileEntity();

    public void onTouchDown(int type);

    public void onTouchCancel(int type);

    public String getCurrentPass();

    public String getNewPass();

    public String getConfirmPass();
}
