package com.simicart.theme.materialtheme.checkout.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.simicart.MainActivity;
import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.model.CartModel;
import com.simicart.core.checkout.model.DeleteCartItemModel;
import com.simicart.core.checkout.model.EditCartItemModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;
import com.simicart.theme.materialtheme.checkout.adapter.MaterialCartAdapter;
import com.simicart.theme.materialtheme.checkout.delegate.MaterialCartDelegate;
import com.simicart.theme.materialtheme.checkout.fragment.MaterialReviewOrderFragment;

import java.util.ArrayList;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

/**
 * Created by Sony on 4/11/2016.
 */
public class MaterialCartController extends SimiController {
    protected MaterialCartDelegate mDelegate;
    protected View.OnClickListener mCartListener;
    protected MaterialCartAdapter.onItemClickListener onClickItemListener;
    protected MaterialCartAdapter.onClickDeleteItemCart onClickDeleteListener;
    protected MaterialCartAdapter.onChangeQtyItemCart onChangeQtyListener;

    public void setDelegate(MaterialCartDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    public View.OnClickListener getCartListener() {
        return mCartListener;
    }

    public MaterialCartAdapter.onItemClickListener getOnClickItemListener() {
        return onClickItemListener;
    }

    public MaterialCartAdapter.onClickDeleteItemCart getOnClickDeleteListener() {
        return onClickDeleteListener;
    }

    public MaterialCartAdapter.onChangeQtyItemCart getOnChangeQtyListener() {
        return onChangeQtyListener;
    }

    @Override
    public void onStart() {
        // request get Cart
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("") || !DataLocal.getQuoteCustomerNotSigin().equals("")) {
            request();
        } else {
            mDelegate.visibleAllView();
        }

        // Action Cart
        actionCart();
    }

    @Override
    public void onResume() {
        request();
    }

    private void request() {
        mModel = new CartModel();
        mDelegate.showLoading();
        ModelDelegate delegate = new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                if (collection != null && collection.getCollection().size() > 0) {
                    mDelegate.updateView(collection);
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    int newQtyCart = cart.getQty();
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(newQtyCart));
                    mDelegate.onUpdateTotalPrice(cart);
                }
            }
        };
        mModel.setDelegate(delegate);
        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModel.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn());
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModel.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin());
        }

        mModel.request();
    }

    private void actionCart() {
        onClickItemListener = new MaterialCartAdapter.onItemClickListener() {
            @Override
            public void onItemClick(ArrayList<ProductEntity> mListProduct, ProductEntity item, int position) {
                String id = item.getProductID();
                ArrayList<String> listID = getListIdCart(mListProduct);
                if (Utils.validateString(id)) {
                    ProductDetailParentFragment fragment = ProductDetailParentFragment
                            .newInstance();
                    fragment.setProductID(id);
                    fragment.setListIDProduct(listID);
                    SimiManager.getIntance().replaceFragment(fragment);
                    SimiManager.getIntance().removeDialog();
                }
            }
        };

        onClickDeleteListener = new MaterialCartAdapter.onClickDeleteItemCart() {
            @Override
            public void onItemDelete(ProductEntity item, int position) {
                String productID = item.getProductID();
                showDialogDeleteItemCart(productID);
            }
        };

        onChangeQtyListener = new MaterialCartAdapter.onChangeQtyItemCart() {
            @Override
            public void onChangeQty(int Qty, ProductEntity item, int position) {
                String productID = item.getProductID();
                showDialogNumberPicker(productID, Qty);
            }
        };

        mCartListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialReviewOrderFragment fragment = MaterialReviewOrderFragment.newInstance();
                SimiManager.getIntance().replaceFragment(fragment);
            }
        };
    }

    private ArrayList<String> getListIdCart(ArrayList<ProductEntity> mListProduct) {
        ArrayList<String> listID = new ArrayList<String>();
        for (int i = 0; i < mListProduct.size(); i++) {
            listID.add(mListProduct.get(i).getProductID());
        }

        return listID;
    }

    private void showDialogDeleteItemCart(final String productID) {
        new AlertDialog.Builder(SimiManager.getIntance().getCurrentActivity())
                .setMessage(
                        Config.getInstance()
                                .getText(
                                        "Are you sure you want to delete this product?"))
                .setPositiveButton(Config.getInstance().getText("Yes"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                requestDeleteItemCart(productID);
                            }
                        })
                .setNegativeButton(Config.getInstance().getText("No"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // do nothing
                            }
                        }).show();
    }

    protected void requestDeleteItemCart(String productID) {
        mDelegate.showDialogLoading();
        DeleteCartItemModel mModelDelete = new DeleteCartItemModel();
        mModelDelete.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(cart.getQty()));
                    mDelegate.onUpdateTotalPrice(cart);
                }
            }
        });

        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            mModelDelete.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn(), "items");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            mModelDelete.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin(), "items");
        }
        mModelDelete.addDataExtendURL(productID);

        mModelDelete.request();
    }

    private void showDialogNumberPicker(final String productID, final int qty) {
        final Dialog dialoglayout = new Dialog(MainActivity.context);
        dialoglayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoglayout.setContentView(Rconfig.getInstance().layout(
                "core_cart_dialog_layout"));
        if (!DataLocal.isTablet) {
            dialoglayout.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            dialoglayout.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialoglayout.getWindow().getAttributes().windowAnimations = Rconfig.getInstance().getId("DialogAnimation", "style");;
        } else {
            dialoglayout.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        dialoglayout.show();

        final WheelView wheel = (WheelView) dialoglayout.findViewById(Rconfig
                .getInstance().id("select_quantity"));
        final NumericWheelAdapter minAdapter = new NumericWheelAdapter(
                MainActivity.context, 1, 999);
        wheel.setViewAdapter(minAdapter);
        if (qty > 0) {
            wheel.setCurrentItem((qty - 1));
        }

        TextView bt_apply = (TextView) dialoglayout.findViewById(Rconfig
                .getInstance().id("bt_apply"));
        bt_apply.setText(Config.getInstance().getText("Done"));
        bt_apply.setTextColor(Color.parseColor("#0173F2"));
        bt_apply.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String value = String.valueOf(minAdapter.getItemText(wheel
                        .getCurrentItem()));
                if (Integer.parseInt(value) != qty) {
                    requestEditItemCart(productID, String.valueOf(minAdapter
                            .getItemText(wheel.getCurrentItem())));
                }
                dialoglayout.dismiss();
            }
        });

        TextView bt_cancel = (TextView) dialoglayout.findViewById(Rconfig
                .getInstance().id("bt_cancel"));
        bt_cancel.setText(Config.getInstance().getText("Cancel"));
        bt_cancel.setTextColor(Color.parseColor("#0173F2"));
        bt_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialoglayout.dismiss();
            }
        });
    }

    protected void requestEditItemCart(final String productID, String qty) {
        mDelegate.showDialogLoading();
        int iqty = 0;
        try {
            iqty = Integer.parseInt(qty);
        } catch (Exception e) {

        }

        int previous = SimiManager.getIntance().getPreQty();
        if (previous != iqty) {
            SimiManager.getIntance().setRefreshCart(true);
        }


        final EditCartItemModel model = new EditCartItemModel();
        model.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissDialogLoading();
                mDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(cart.getQty()));
                    mDelegate.onUpdateTotalPrice(cart);
                }
            }
        });


        if (!Config.getInstance().getQuoteCustomerSignIn().equals("")) {
            model.addDataExtendURL(Config.getInstance().getQuoteCustomerSignIn(), "items");
        }

        if (!DataLocal.getQuoteCustomerNotSigin().equals("")) {
            model.addDataExtendURL(DataLocal.getQuoteCustomerNotSigin(), "items");
        }
        model.addDataExtendURL(productID);
        model.addDataBody("qty", qty);
        model.request();
    }
}

