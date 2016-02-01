package com.simicart.core.customer.controller;

import android.util.Log;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.config.Config;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.entity.ProfileEntity;
import com.simicart.core.catalog.product.model.CreateQuoteModel;
import com.simicart.core.customer.model.GetAllQuoteModel;
import com.simicart.core.customer.model.LogInModel;
import com.simicart.core.customer.model.MergeQuoteModel;
import com.simicart.core.customer.model.UpdateCustomerToQuoteModel;
import com.simicart.core.event.controller.EventController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AutoSignInController extends SimiController {

    @Override
    public void onStart() {
        String typeSignIn = DataLocal.getTypeSignIn();
        DataLocal.saveSignInState(false);
        if (typeSignIn.equals(Constants.NORMAL_SIGN_IN)) {
            final String email = DataLocal.getEmail();
            final String password = DataLocal.getPassword();
            final LogInModel model = new LogInModel();
            model.setDelegate(new ModelDelegate() {
                @Override
                public void onFail(SimiError error) {
                    DataLocal.saveSignInState(false);
                    if (error != null) {
                        SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                    }
                }

                @Override
                public void onSuccess(SimiCollection collection) {
                    if (collection != null && collection.getCollection().size() > 0) {
                        DataLocal.isNewSignIn = true;
                        DataLocal.saveSignInState(true);
                        ProfileEntity entity = (ProfileEntity) collection.getCollection().get(0);
                        DataLocal.mCustomer = entity;
                        String email = "";
                        String firstname = "";
                        String lastname = "";
                        String name = "";
                        String customerID = "";

                        if (entity.getEmail() != null)
                            email = entity.getEmail();
                        if (entity.getFirstName() != null && entity.getLastName() != null)
                            name = entity.getFirstName() + " " + entity.getLastName();
                        if (entity.getID() != null)
                            customerID = entity.getID();
                        if (entity.getFirstName() != null)
                            firstname = entity.getFirstName();
                        if (entity.getLastName() != null)
                            lastname = entity.getLastName();

                        if (null != name) {
                            Log.e("SignIn Info", name + " " + email + " " + password);
                            DataLocal.saveData(name, email, password);
                            DataLocal.saveCustomerID(customerID);
                            DataLocal.saveCustomer(firstname, lastname, email, name, customerID);
                            //DataLocal.saveEmailPassRemember(email, password);
                        }
                        SimiManager.getIntance().onUpdateItemSignIn();
                        requestGetAllQuote();
                    }
                }
            });
            model.addDataBody(Constants.USER_EMAIL, email);
            model.addDataBody(Constants.USER_PASSWORD, password);

            model.addDataExtendURL("login");
            model.request();
        } else {
            // event for face book
            EventController event = new EventController();
//            event.dispatchEvent("com.simicart.autoSignIn", typeSignIn);
        }
    }

    private void requestGetAllQuote() {
        GetAllQuoteModel quoteModel = new GetAllQuoteModel();
        quoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null) {
                    ArrayList<SimiEntity> entity = collection.getCollection();
                    if (entity != null && entity.size() > 0) {
                        ArrayList<QuoteEntity> listQuote = new ArrayList<QuoteEntity>();
                        for (SimiEntity simiEntity : entity) {
                            QuoteEntity quoteEntity = (QuoteEntity) simiEntity;
                            listQuote.add(quoteEntity);
                        }

                        if (listQuote.size() > 0) {
                            Config.getInstance().setQuoteCustomerSignIn(listQuote.get(0).getID());
                            SimiManager.getIntance().onUpdateCartQty(String.valueOf(listQuote.get(0).getQty()));

                        }

                        if (Config.getInstance().getQuoteCustomerSignIn().equals("")) {
                            if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
//								if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
//									requestMergeQuote();
//								} else {
                                requestUpdateCustomerToQuote();
//								}
                            }
                        } else {
                            if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
                                requestMergeQuote();
                            }
                        }
                    }
                }
            }
        });

        quoteModel.addDataParameter("filter[customer|customer_id]", DataLocal.getCustomerID());
        quoteModel.addDataParameter("filter[orig_order_id]", "0");
        quoteModel.request();
    }

    private void requestMergeQuote() {
        MergeQuoteModel mergeQuoteModel = new MergeQuoteModel();
        mergeQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null) {
                    if (collection.getCollection().size() > 0) {
                        QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                        SimiManager.getIntance().onUpdateCartQty(String.valueOf(quoteEntity.getQty()));
                        Config.getInstance().setQuoteCustomerSignIn(quoteEntity.getID());
                        DataLocal.saveQuoteCustomerNotSignIn("");
                    }
                }
            }
        });
        mergeQuoteModel.addDataExtendURL("merge");
        mergeQuoteModel.addDataBody("source_quoteId", DataLocal.getQuoteCustomerNotSigin());
        mergeQuoteModel.addDataBody("des_quoteId", Config.getInstance().getQuoteCustomerSignIn());
        mergeQuoteModel.request();
    }

    private void requestUpdateCustomerToQuote() {
        UpdateCustomerToQuoteModel updateCustomerToQuoteModel = new UpdateCustomerToQuoteModel();
        updateCustomerToQuoteModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                if (collection != null) {
                    if (collection.getCollection().size() > 0) {
                        QuoteEntity quoteEntity = (QuoteEntity) collection.getCollection().get(0);
                        Config.getInstance().setQuoteCustomerSignIn(quoteEntity.getID());
                        DataLocal.saveQuoteCustomerNotSignIn("");
                    }
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            updateCustomerToQuoteModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            updateCustomerToQuoteModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        JSONObject cutomer = null;
        try {
            cutomer = new JSONObject();
            cutomer.put("customer_first_name", DataLocal.getCustomer().getFirstName());
            cutomer.put("customer_last_name", DataLocal.getCustomer().getLastName());
            cutomer.put("customer_email", DataLocal.getCustomer().getEmail());
            cutomer.put("customer_name", DataLocal.getCustomer().getName());
            cutomer.put("customer_id", DataLocal.getCustomer().getID());
        } catch (JSONException e) {
            cutomer = null;
        }

        if (cutomer != null) {
            updateCustomerToQuoteModel.addDataBody("customer", cutomer);
        }
        updateCustomerToQuoteModel.request();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub

    }

}
