package com.simicart.core.customer.delegate;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.customer.entity.ProfileEntity;

public interface ProfileDelegate extends SimiDelegate{
	
	public ProfileEntity getProfileEntity();

}
