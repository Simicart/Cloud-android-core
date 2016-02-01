package com.simicart.core.event.fragment;

import com.simicart.core.base.fragment.SimiFragment;

import java.io.Serializable;

/**
 * Created by MSI on 27/01/2016.
 */
public class SimiEventFragmentEntity implements Serializable{
    private SimiFragment mFragment;

    public void setFragmetn(SimiFragment fragment)
    {
        mFragment = fragment;
    }

    public SimiFragment getFragment()
    {
        return mFragment;
    }
}
