package com.simicart.core.customer.controller;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.delegate.SimiDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.controller.ConfigCheckout;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.MyAddress;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.customer.fragment.AddressBookDetailFragment;
import com.simicart.core.customer.fragment.NewAddressBookFragment;
import com.simicart.core.customer.model.AddressBookModel;

import java.util.ArrayList;

@SuppressLint("ClickableViewAccessibility")
public class AddressBookController extends SimiController {

	protected SimiDelegate mDelegate;
	protected OnTouchListener mListener;
	protected OnItemClickListener mClicker;

	public OnTouchListener getListener() {
		return mListener;
	}

	public OnItemClickListener getItemClicker() {
		return mClicker;
	}

	public void setDelegate(SimiDelegate delegate) {
		mDelegate = delegate;
	}

	@Override
	public void onStart() {
		mDelegate.showLoading();
		mModel = new AddressBookModel();
		mModel.setDelegate(new ModelDelegate() {
			@Override
			public void onFail(SimiError error) {
				if(error != null) {
					SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
				}
			}

			@Override
			public void onSuccess(SimiCollection collection) {
				mDelegate.dismissLoading();
				mDelegate.updateView(collection);
			}
		});
		mModel.addDataExtendURL(DataLocal.getCustomerID());
		mModel.request();

		mListener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				NewAddressBookFragment fragment = NewAddressBookFragment
						.newInstance();
				if (DataLocal.isTablet) {
					SimiManager.getIntance().replacePopupFragment(fragment);
				} else {
					SimiManager.getIntance().replaceFragment(fragment);
				}
				return false;
			}
		};

		mClicker = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				selectItem(position);
			}

		};

	}


	protected void selectItem(int position) {
		ProfileEntity entity = (ProfileEntity) mModel.getCollection().getCollection().get(0);
		if (null != entity) {
			ArrayList<MyAddress> address = new ArrayList<MyAddress>();
			if(entity.getAddress() != null) {
				if(entity.getAddress().size() > 0) {
					for (SimiEntity simiEntity : entity.getAddress()) {
						MyAddress addr = (MyAddress) simiEntity;
						String email = entity.getEmail();
						if(Utils.validateString(email)){
							addr.setEmail(email);
						}
						address.add(addr);
					}

					AddressBookDetailFragment fragment = AddressBookDetailFragment
							.newInstance();
					fragment.setAddressbook(address.get(position));
					if (DataLocal.isTablet) {
						SimiManager.getIntance().replacePopupFragment(fragment);
					} else {
						SimiManager.getIntance().replaceFragment(fragment);
					}
				}
			}
		}
	}

	@Override
	public void onResume() {
		mDelegate.updateView(mModel.getCollection());
	}

}
