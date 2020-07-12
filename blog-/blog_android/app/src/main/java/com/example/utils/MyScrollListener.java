package com.example.utils;

import android.widget.AbsListView;

/**
 * 暂时用不上
 */
public class MyScrollListener implements AbsListView.OnScrollListener{
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
            if(view.getLastVisiblePosition() == view.getCount()-1){
//                loadData();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                lastItem = firstVisibleItem + visibleItemCount - 1;
    }
}
