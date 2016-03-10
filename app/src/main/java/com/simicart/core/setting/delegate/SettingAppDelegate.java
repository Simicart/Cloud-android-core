package com.simicart.core.setting.delegate;

import com.simicart.core.base.delegate.SimiDelegate;

/**
 * Created by Sony on 3/10/2016.
 */
public interface SettingAppDelegate extends SimiDelegate{
    public void updateLanguage(String language);

    public void hidenLanguage();

    public void updateCurrency(String currency);

    public void selectNotification();
}
