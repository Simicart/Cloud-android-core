package com.simicart.theme.materialtheme.customer.signin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.config.Rconfig;
import com.simicart.core.customer.fragment.SignInFragment;

/**
 * Created by Martial on 4/11/2016.
 */
public class MaterialSignInFragment extends SignInFragment {

    public static MaterialSignInFragment newInstance() {
        return new MaterialSignInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(Rconfig.getInstance().layout("material_sign_in_layout"), container, false);

        SimiManager.getIntance().getManager()
                .beginTransaction()
                .replace(Rconfig.getInstance().id("sign_in_container"), new MaterialSignInChildFragment())
                .addToBackStack(null)
                .commit();

        return v;
    }
}
