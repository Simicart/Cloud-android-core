package com.simicart.core.menutop.search.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Rconfig;
import com.simicart.core.menutop.search.block.SearchBlock;
import com.simicart.core.menutop.search.controller.SearchController;

/**
 * Created by Martial on 4/6/2016.
 */
public class FragmentSearch extends SimiFragment {

    SearchBlock mBlock;
    SearchController mController;

    public static FragmentSearch newInstance() {
        return new FragmentSearch();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(Rconfig.getInstance().layout("core_search_layout"), container, false);

        mBlock = new SearchBlock(v, getActivity());
        mBlock.initView();

        if(mController == null) {
            mController = new SearchController();
            mController.onStart();
        } else {
            mController.onResume();
        }

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        SimiManager.getIntance().backPreviousFragment();
                        SimiManager.getIntance().onUpdateSearchView(false);
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }
}
