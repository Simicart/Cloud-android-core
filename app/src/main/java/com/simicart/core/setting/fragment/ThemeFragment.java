package com.simicart.core.setting.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.simicart.core.base.fragment.SimiFragment;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.common.Utils;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

/**
 * Created by frank on 9/20/16.
 */

public class ThemeFragment extends SimiFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(Rconfig.getInstance().layout("demo_fragment_theme"), container, false);

        RadioGroup rdgTheme = (RadioGroup) rootView.findViewById(Rconfig.getInstance().id("rdg_theme"));
        rdgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == Rconfig.getInstance().id("rdb_default")) {
                    if (!DataLocal.THEME.equals("default")) {
                        DataLocal.THEME = "default";
                        SimiManager.getIntance().changeStoreView();
                    }

                } else if (checkedId == Rconfig.getInstance().id("rdb_matrix")) {
                    if (!DataLocal.THEME.equals("matrix")) {
                        DataLocal.THEME = "matrix";
                        SimiManager.getIntance().changeStoreView();
                    }
                } else if (checkedId == Rconfig.getInstance().id("rdb_zara")) {
                    if (!DataLocal.THEME.equals("zara")) {
                        DataLocal.THEME = "zara";
                        SimiManager.getIntance().changeStoreView();
                    }
                }


            }
        });

        if (Utils.validateString(DataLocal.THEME)) {
            if (DataLocal.THEME.equals("matrix")) {
                RadioButton rdbMatrix = (RadioButton) rootView.findViewById(Rconfig.getInstance().id("rdb_matrix"));
                rdbMatrix.setChecked(true);
            } else if (DataLocal.THEME.equals("zara")) {
                RadioButton rdbZara = (RadioButton) rootView.findViewById(Rconfig.getInstance().id("rdb_zara"));
                rdbZara.setChecked(true);
            } else {
                RadioButton rdbDefault = (RadioButton) rootView.findViewById(Rconfig.getInstance().id("rdb_default"));
                rdbDefault.setChecked(true);
            }
        }


        return rootView;
    }
}
