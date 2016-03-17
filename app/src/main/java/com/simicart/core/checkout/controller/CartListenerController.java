package com.simicart.core.checkout.controller;

import java.util.ArrayList;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.TextView;

import com.simicart.R;
import com.simicart.MainActivity;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.catalog.product.entity.productEnity.ProductEntity;
import com.simicart.core.catalog.product.fragment.ProductDetailParentFragment;
import com.simicart.core.checkout.delegate.CartAdapterDelegate;
import com.simicart.core.checkout.delegate.CartDelegate;
import com.simicart.core.checkout.entity.QuoteEntity;
import com.simicart.core.checkout.fragment.AddressBookCheckoutFragment;
import com.simicart.core.checkout.model.DeleteCartItemModel;
import com.simicart.core.checkout.model.EditCartItemModel;
import com.simicart.core.common.Utils;
import com.simicart.core.config.Config;
import com.simicart.core.config.DataLocal;
import com.simicart.core.config.Rconfig;

@SuppressLint("ClickableViewAccessibility")
public class CartListenerController implements CartAdapterDelegate {
    protected ArrayList<ProductEntity> mCarts;
    protected CartDelegate mBlockDelegate;
    protected String mMessage;

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }


    @Override
    public OnTouchListener getOnTouchListener(final int position) {
        OnTouchListener onTouch = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(Color.parseColor("#EBEBEB"));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        deleteItemCart(position);
                    }

                    case MotionEvent.ACTION_CANCEL: {
                        v.setBackgroundColor(0);
                        break;
                    }
                    default:
                        break;
                }
                return true;
            }
        };

        return onTouch;
    }

    private void deleteItemCart(final int position) {
        new AlertDialog.Builder(SimiManager.getIntance().getCurrentActivity())
                .setMessage(
                        Config.getInstance()
                                .getText(
                                        "Are you sure you want to delete this product?"))
                .setPositiveButton(Config.getInstance().getText("Yes"),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                requestDeleteItemCart(position);
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




    protected void requestDeleteItemCart(int position) {
        mBlockDelegate.showDialogLoading();
        ProductEntity productEntity = mCarts.get(position);
        String productID = productEntity.getID();
        DeleteCartItemModel mModelDelete = new DeleteCartItemModel();
        mModelDelete.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                mBlockDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mBlockDelegate.dismissDialogLoading();
                mBlockDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(cart.getQty()));
                    mBlockDelegate.onUpdateTotalPrice(cart);
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

//		mModelDelete.addDataExtendURL("56682d0ce2bc731b157b23d1");;
        mModelDelete.request();
    }

    protected void editItemCart(final int position, String qty) {
        mBlockDelegate.showDialogLoading();
        ProductEntity productEntity = mCarts.get(position);
        String productID = productEntity.getID();
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
                mBlockDelegate.dismissDialogLoading();
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "Ok");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mBlockDelegate.dismissDialogLoading();
                mBlockDelegate.updateView(collection);
                if (collection != null && collection.getCollection().size() > 0) {
                    QuoteEntity cart = (QuoteEntity) collection.getCollection().get(0);
                    SimiManager.getIntance().onUpdateCartQty(
                            String.valueOf(cart.getQty()));
                    mBlockDelegate.onUpdateTotalPrice(cart);
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
//		model.addDataExtendURL("56682d0ce2bc731b157b23d1");
        model.addDataBody("qty", qty);
        model.request();
    }

    public OnClickListener getClickItemCartListener(final int position,
                                                    final ArrayList<String> listID) {
        OnClickListener clicker = new OnClickListener() {

            @Override
            public void onClick(View v) {
                showProductDetail(position, listID);

            }
        };

        return clicker;
    }

    protected void showProductDetail(int position, ArrayList<String> listID) {
        ProductEntity cart = mCarts.get(position);
        if (null != cart) {
            String id = cart.getProductID();
            if (Utils.validateString(id)) {
                ProductDetailParentFragment fragment = ProductDetailParentFragment
                        .newInstance();
                fragment.setProductID(id);
                fragment.setListIDProduct(listID);
                SimiManager.getIntance().replaceFragment(fragment);
                SimiManager.getIntance().removeDialog();
            }
        }
    }

    protected void onEditQty(final int position, String qty) {
        mCarts.get(position).setQty(Integer.parseInt(qty));
    }

    public void setListCart(ArrayList<ProductEntity> carts) {
        mCarts = carts;
    }


    public void setCartDelegate(CartDelegate delegate) {
        mBlockDelegate = delegate;
    }

    private void showDialogNumberPicker(final int position, final int qty) {
        final Dialog dialoglayout = new Dialog(MainActivity.context);
        dialoglayout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoglayout.setContentView(Rconfig.getInstance().layout(
                "core_cart_dialog_layout"));
        if (!DataLocal.isTablet) {
            dialoglayout.getWindow().setLayout(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            dialoglayout.getWindow().setBackgroundDrawable(
                    new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialoglayout.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
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
        bt_apply.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String value = String.valueOf(minAdapter.getItemText(wheel
                        .getCurrentItem()));
                if (Integer.parseInt(value) != qty) {
                    editItemCart(position, String.valueOf(minAdapter
                            .getItemText(wheel.getCurrentItem())));
                }
                dialoglayout.dismiss();
            }
        });

        TextView bt_cancel = (TextView) dialoglayout.findViewById(Rconfig
                .getInstance().id("bt_cancel"));
        bt_cancel.setText(Config.getInstance().getText("Cancel"));
        bt_cancel.setTextColor(Color.parseColor("#0173F2"));
        bt_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialoglayout.dismiss();
            }
        });
    }

    @Override
    public OnClickListener getClickQtyItem(final int position, final int qty) {
        OnClickListener onclick = new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialogNumberPicker(position, qty);
            }
        };
        return onclick;
    }

}
