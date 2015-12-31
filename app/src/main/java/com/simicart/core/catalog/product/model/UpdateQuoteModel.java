package com.simicart.core.catalog.product.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.QuoteEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by MSI on 16/12/2015.
 */
public class UpdateQuoteModel extends SimiModel {
    @Override
    protected void setUrlAction() {
        addDataExtendURL("quotes");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.PUT;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            if(mJSONResult.has("quote")){
                Log.e("CreateQuoteModel", mJSONResult.toString());
                if(mJSONResult.has("quote")){
                    try {
                        JSONObject quoteArr = mJSONResult.getJSONObject("quote");
                        QuoteEntity quote = new QuoteEntity();
                        quote.setJSONObject(quoteArr);
                        quote.parse();
                        collection.addEntity(quote);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
