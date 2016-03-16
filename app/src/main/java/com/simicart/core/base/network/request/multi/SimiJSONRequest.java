package com.simicart.core.base.network.request.multi;

import android.util.Log;

import com.simicart.core.base.delegate.NetWorkDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.network.response.CoreResponse;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;

public class SimiJSONRequest extends SimiRequest {

    String url;

    public SimiJSONRequest(String url, NetWorkDelegate delegate) {
        super(url, delegate);
        this.url = url;
    }


    @Override
    public CoreResponse parseNetworkResponse(SimiNetworkResponse response) {
        if (null != response) {
            byte[] data = response.getData();
            if (null != data && data.length > 0) {
                String content = new String(data);
                CoreResponse coreResponse = new CoreResponse();
                coreResponse.setData(content);
                return coreResponse;
            }
        }
        return null;
    }

    @Override
    public void deliveryCoreResponse(CoreResponse response) {
        if (null != response) {
            if (response.parse()) {
                mDelegate.callBack(response, true);
            } else {
                mRequestQueue.finish(this);
                mDelegate.callBack(response, false);
            }

        } else {
            mRequestQueue.finish(this);
            mDelegate.callBack(response, false);
        }
    }
}
