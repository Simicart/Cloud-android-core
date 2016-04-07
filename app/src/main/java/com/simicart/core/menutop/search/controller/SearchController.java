package com.simicart.core.menutop.search.controller;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.menutop.search.block.SearchBlock;

/**
 * Created by Martial on 4/6/2016.
 */
public class SearchController extends SimiController {

    protected SearchBlock mDelegate;

    public void setDelegate(SearchBlock delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        SimiManager.getIntance().onUpdateSearchView(true);
        SimiManager.getIntance().clearSearchText();
    }
}
