package com.simicart.core.base.delegate;

import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;

public interface ModelDelegate {
    public abstract void onFail(SimiError error);

    public abstract void onSuccess(SimiCollection collection);
}
