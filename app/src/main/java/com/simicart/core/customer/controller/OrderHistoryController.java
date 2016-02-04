package com.simicart.core.customer.controller;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.simicart.core.base.controller.SimiController;
import com.simicart.core.base.delegate.ModelDelegate;
import com.simicart.core.base.manager.SimiManager;
import com.simicart.core.base.model.collection.SimiCollection;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.OrderHistoryDelegate;
import com.simicart.core.customer.entity.OrderHistory;
import com.simicart.core.customer.fragment.OrderHistoryDetailFragment;
import com.simicart.core.customer.model.OrderHistoryModel;

public class OrderHistoryController extends SimiController {
    protected OrderHistoryDelegate mDelegate;
    protected OnItemClickListener mItemClicker;
    protected OnScrollListener mScrollListener;
    protected int mOffset = 0;
    protected int mLimit = 5;
    protected boolean mCheckOnScroll = true;

    public OnItemClickListener getItemClicker() {
        return mItemClicker;
    }

    public OnScrollListener getScrollListener() {
        return mScrollListener;
    }

    public void setDelegate(OrderHistoryDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onStart() {
        mItemClicker = new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                onSelectedItem(position);
            }
        };

        mScrollListener = new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                int threshold = 1;
                int count = view.getCount();
                if (scrollState == SCROLL_STATE_IDLE) {
                    if ((view.getLastVisiblePosition() >= count - threshold)) {
                        if (mCheckOnScroll) {
                            mOffset += 5;
                            onAddData();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

            }
        };

        onRequestData(mLimit, mOffset);

    }

    protected void onAddData() {
        mDelegate.addFooterView();
        mCheckOnScroll = false;
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {

            }
        });

        String email = DataLocal.getEmail();
        String pass = DataLocal.getPassword();

        mModel.addDataBody(Constants.USER_EMAIL, email);
        mModel.addDataBody(Constants.USER_PASSWORD, pass);
        mModel.addDataBody(Constants.LIMIT, String.valueOf(mLimit));
        mModel.addDataBody(Constants.OFFSET, String.valueOf(mOffset));

        mModel.request();
    }

    protected void onRequestData(int limit, int offset) {
        mDelegate.showLoading();
        mCheckOnScroll = false;
        mModel = new OrderHistoryModel();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if (error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.dismissLoading();
                mDelegate.updateView(collection);
            }
        });

        // Add Filter with fixed data
        mModel.addFilterDataParameter("customer|customer_id", DataLocal.getCustomerID());
        mModel.sortDirDESC();
//        String email = DataLocal.getEmail();
//        String pass = DataLocal.getPassword();
//
//        mModel.addDataBody(Constants.USER_EMAIL, email);
//        mModel.addDataBody(Constants.USER_PASSWORD, pass);
//        mModel.addDataBody(Constants.LIMIT, String.valueOf(limit));
//        mModel.addDataBody(Constants.OFFSET, String.valueOf(offset));

        mModel.request();
    }

    protected void onSelectedItem(int position) {
        OrderHistory orderHis = (OrderHistory) mModel.getCollection()
                .getCollection().get(0);

        String id = orderHis.getmID().get(position);
        OrderHistoryDetailFragment fragment = OrderHistoryDetailFragment
                .newInstance();
        fragment.setOrderHisDetail(orderHis.getmOrders().get(position));
        if (DataLocal.isTablet) {
            SimiManager.getIntance().addFragmentSub(fragment);
        } else {
            SimiManager.getIntance().replaceFragment(fragment);
        }

    }

    @Override
    public void onResume() {
        mDelegate.updateView(mModel.getCollection());
    }

}
