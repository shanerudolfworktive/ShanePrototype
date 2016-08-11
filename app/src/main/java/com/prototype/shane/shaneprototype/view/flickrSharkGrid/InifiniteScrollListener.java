package com.prototype.shane.shaneprototype.view.flickrSharkGrid;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by shane on 7/9/16.
 */
public class InifiniteScrollListener extends RecyclerView.OnScrollListener{
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    int firstVisibleItem, visibleItemCount, totalItemCount;
    GridLayoutManager gridLayoutManager;
    MainRecycleViewAdapter recycleViewAdapter;
    public InifiniteScrollListener(GridLayoutManager gridLayoutManager, MainRecycleViewAdapter recycleViewAdapter){
        this.gridLayoutManager = gridLayoutManager;
        this.recycleViewAdapter = recycleViewAdapter;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = gridLayoutManager.getChildCount();
        totalItemCount = gridLayoutManager.getItemCount();
        firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            recycleViewAdapter.setItemCount(gridLayoutManager.getItemCount()+20);
            if(!recyclerView.isComputingLayout())recycleViewAdapter.notifyDataSetChanged();
            loading = true;
        }
    }
}
