package com.simicart.core.customer.model;

import android.util.Log;

import com.simicart.core.base.model.SimiModel;
import com.simicart.core.base.network.request.multi.SimiRequest;
import com.simicart.core.checkout.entity.QuoteEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sony on 12/9/2015.
 */
public class GetAllQuoteModel extends SimiModel{
    @Override
    protected void setUrlAction() {
        addDataExtendURL("quotes");
    }

    @Override
    protected void setTypeMethod() {
        mTypeMethod = SimiRequest.Method.GET;
    }

    @Override
    protected void paserData() {
        super.paserData();
        if(mJSONResult != null){
            Log.e("GetAllQuoteModel", mJSONResult.toString());
            if(mJSONResult.has("quotes")){
                try {
                    JSONArray quoteArr = mJSONResult.getJSONArray("quotes");
                    if(quoteArr != null && quoteArr.length() > 0){
                        for (int i = 0; i < quoteArr.length(); i++){
                            QuoteEntity quote = new QuoteEntity();
                            quote.setJSONObject(quoteArr.getJSONObject(i));
                            quote.parse();
                            collection.addEntity(quote);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
