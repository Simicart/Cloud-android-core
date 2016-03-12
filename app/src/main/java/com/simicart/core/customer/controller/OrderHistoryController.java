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
import com.simicart.core.base.model.entity.SimiEntity;
import com.simicart.core.base.network.request.error.SimiError;
import com.simicart.core.config.Constants;
import com.simicart.core.config.DataLocal;
import com.simicart.core.customer.delegate.OrderHistoryDelegate;
import com.simicart.core.customer.entity.OrderHistory;
import com.simicart.core.customer.fragment.OrderHistoryDetailFragment;
import com.simicart.core.customer.model.OrderHistoryModel;

import java.util.ArrayList;

public class OrderHistoryController extends SimiController {
    protected OrderHistoryDelegate mDelegate;
    protected OnItemClickListener mItemClicker;
    protected OnScrollListener mScrollListener;
    protected int mOffset = 0;
    protected int mLimit = 5;
    protected  int mTotal;
    protected  int mCurrentNumberOrder;

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
                        if(mCurrentNumberOrder < mTotal) {
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

        onRequestData();

    }

    protected void onRequestData() {
        mDelegate.showLoading();
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

                ArrayList<SimiEntity> entities = collection.getCollection();
                if(entities.size() > 0)
                {
                    OrderHistory orderHistory = (OrderHistory) entities.get(0);
                    mTotal = orderHistory.getmTotal();
                    mCurrentNumberOrder = orderHistory.getNumberOrder();
                }

                mDelegate.updateView(collection);


            }
        });

        // Add Filter with fixed data
        mModel.addFilterDataParameter("customer|customer_id", DataLocal.getCustomerID());
        mModel.sortDirDESC();
        mModel.addOffsetDataParameter(String.valueOf(mOffset));
        mModel.addLimitDataParameter(String.valueOf(mLimit));

        mModel.request();
    }

    protected void onAddData() {
        mDelegate.addFooterView();
        mModel.setDelegate(new ModelDelegate() {
            @Override
            public void onFail(SimiError error) {
                if(error != null) {
                    SimiManager.getIntance().showNotify(null, error.getMessage(), "OK");
                }
            }

            @Override
            public void onSuccess(SimiCollection collection) {
                mDelegate.removeFooterView();
                mDelegate.updateView(collection);
                ArrayList<SimiEntity> entities = collection.getCollection();
                if(entities.size() > 0)
                {
                    OrderHistory orderHistory = (OrderHistory) entities.get(0);
                    mTotal = orderHistory.getmTotal();
                    mCurrentNumberOrder = orderHistory.getNumberOrder();
                }
            }
        });

        mModel.addFilterDataParameter("customer|customer_id", DataLocal.getCustomerID());
        mModel.sortDirDESC();
        mModel.addOffsetDataParameter(String.valueOf(mOffset));
        mModel.addLimitDataParameter(String.valueOf(mLimit));

        mModel.request();
    }



    protected void onSelectedItem(int position) {
        OrderHistory orderHis = (OrderHistory) mModel.getCollection()
                .getCollection().get(0);

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
