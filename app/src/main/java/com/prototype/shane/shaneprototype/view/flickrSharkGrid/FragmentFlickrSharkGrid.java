package com.prototype.shane.shaneprototype.view.flickrSharkGrid;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.NetworkError;
import com.android.volley.VolleyError;
import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.flickrShark.FlickrSearchPhotoModel;
import com.prototype.shane.shaneprototype.util.Const;
import com.prototype.shane.shaneprototype.view.BaseFragment;
import com.prototype.shane.shaneprototype.view.lightBox.LightBoxFragment;
import com.prototype.shane.shaneprototype.volley.GsonRequest;


/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentFlickrSharkGrid extends BaseFragment {

    public static final String TAG_MAIN_ACTIVITY_FRAGMENT = "TAG_MAIN_ACTIVITY_FRAGMENT";

    //View States
    Integer totalChildBeforOrientationChange = null;
    Integer listPositionBeforOrientationChange = null;

    //view
    RecyclerView recyclerView;
    MainRecycleViewAdapter adapter = new MainRecycleViewAdapter();;
    SwipeRefreshLayout swipeRefreshLayout;

    GsonRequest<FlickrSearchPhotoModel> photosModelRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_flickr_shark_grid, container, false);
        loadInitialView(rootView);//load initial views
        restoreViewState(rootView);//restore view state
        return rootView;
    }

    private void loadInitialView(View rootView){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMainShark);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new InifiniteScrollListener(layoutManager, adapter));
        adapter.setOnItemSelectedListener(new MainRecycleViewAdapter.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Drawable drawable, FlickrSearchPhotoModel.PhotoModel photo) {
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enter_light_box_scene_animation, R.anim.exit_light_box_scene_animation, R.anim.pop_enter_light_box_scene_animation, R.anim.pop_exit_light_box_scene_animation).replace(getId(), LightBoxFragment.create(drawable, photo.originalPhotoUrl, photo.id)).addToBackStack(null).commit();
            }
        });

        swipeRefreshLayout= (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                photosModelRequest.setShouldCacheResponse(true).sendRequest(getActivity());
            }
        });

        //send request if it is first time loading the view
        if (photosModelRequest == null) photosModelRequest = createFlickrSearchRequest().setShouldCacheResponse(true).setShouldGetCachedResponseFirst(true).setShouldOnlyNotifyFromCache(true).sendRequest(getActivity());
    }

    private void restoreViewState(View rootView) {
        if (totalChildBeforOrientationChange != null) adapter.setItemCount(totalChildBeforOrientationChange);
        if (listPositionBeforOrientationChange != null) recyclerView.offsetChildrenVertical(listPositionBeforOrientationChange);
    }

    private void saveViewState(){
        totalChildBeforOrientationChange = adapter.getItemCount();
        listPositionBeforOrientationChange = recyclerView.computeVerticalScrollOffset();
    }

    private GsonRequest<FlickrSearchPhotoModel> createFlickrSearchRequest(){
        GsonRequest<FlickrSearchPhotoModel> request = new GsonRequest<FlickrSearchPhotoModel>(Const.FLICKR_SEARCH_URL, FlickrSearchPhotoModel.class, null) {
            @Override
            protected void deliverResponse(FlickrSearchPhotoModel response, boolean isFromCache) {
                handleFlickrSearchPhotoSuccess(response, isFromCache);
            }

            @Override
            public void deliverError(VolleyError error, FlickrSearchPhotoModel cachedResponse) {
                handleFlickrSearchPhotoError(error, cachedResponse);
            }
        };
        requestToCancelOnDestroy.add(request);
        return request;
    }

    public void handleFlickrSearchPhotoSuccess(FlickrSearchPhotoModel response, boolean isFromCache){
        adapter.setData(response.photos.photo);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void handleFlickrSearchPhotoError(VolleyError error, FlickrSearchPhotoModel cachedResponse){
        if (error instanceof NetworkError) {
            displayErrorDialogWithMsg(getString(R.string.network_error_message));
        }else {
            displayErrorDialogWithMsg(getString(R.string.generalErrorMessage));
        }

        swipeRefreshLayout.setRefreshing(false);
    }

    private void displayErrorDialogWithMsg(String msg){
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.errorTitle)
                .setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveViewState();
    }
}
