package com.simicart.core.zopimchat;

import android.content.Intent;

import com.simicart.core.base.manager.SimiManager;

/**
 * Created by frank on 9/21/16.
 */

public class SimiChat {

    public void onChat(){
        Intent intent = new Intent(SimiManager.getIntance().getCurrentActivity(), SimiChatActivity.class);
        SimiManager.getIntance().getCurrentActivity().startActivity(intent);
    }

}
