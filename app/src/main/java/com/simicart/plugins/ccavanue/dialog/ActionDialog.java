package com.simicart.plugins.ccavanue.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.simicart.MainActivity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Rconfig;
import com.simicart.plugins.ccavanue.common.Communicator;

public class ActionDialog extends DialogFragment {

    Communicator communicator;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        communicator = (Communicator) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Option");

        builder.setItems(Rconfig.getInstance().getId(MainActivity.context,"selectAction","array"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    communicator.actionSelected(Config.getInstance().getText("ResendOTP"));
                }
                else if(which == 1){
                    communicator.actionSelected(Config.getInstance().getText("EnterOTPManually"));
                }

            }
        });

        builder.setNegativeButton(Config.getInstance().getText("Cancel"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                communicator.actionSelected(Config.getInstance().getText("Cancel"));
            }
        });

        /*builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        Dialog dialog = builder.create();
        return dialog;
    }
}
