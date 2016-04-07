package com.simicart.core.menutop.delegate;

import com.simicart.core.base.delegate.SimiDelegate;

public interface MenuTopDelegate extends SimiDelegate {

	public void updateBackground(int color);

	public void updateCartQty(String qty);

	public void showCartLayout(boolean show);

	public void hideMenuTop(boolean isHide);

	public void showSearchView();

	public void hideSearchView();

	public void showSearchScreen(String query);

	public void clearSearchField();

}
