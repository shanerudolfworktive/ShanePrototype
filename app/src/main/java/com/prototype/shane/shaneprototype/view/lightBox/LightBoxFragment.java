package com.prototype.shane.shaneprototype.view.lightBox;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.view.BaseFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by shane on 7/9/16.
 */
public class LightBoxFragment extends BaseFragment {

    public static String TAG_LIGHT_BOX_FRAGMENT = "TAG_LIGHT_BOX_FRAGMENT";

    Drawable thumpNailDrawable;
    String originalImageUrl;

    boolean isOverLayShown = false;
    long photoID;
    LightBoxOverLayFragment lightBoxOverLayFragment;

    public static LightBoxFragment create(Drawable thumpNailDrawable, String originalImageUrl, long photoID){
        LightBoxFragment lightBoxFragment = new LightBoxFragment();
        lightBoxFragment.thumpNailDrawable = thumpNailDrawable;
        lightBoxFragment.originalImageUrl = originalImageUrl;
        lightBoxFragment.photoID = photoID;
        return lightBoxFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lightBoxOverLayFragment = LightBoxOverLayFragment.create(photoID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_light_box, container, false);
        loadInitialView(rootView);
        return rootView;
    }

    private void loadInitialView(View rootView ){
        final PhotoView photoView = (PhotoView) rootView.findViewById(R.id.imageViewLightBox);
        photoView.setOnPhotoTapListener(createToggleOverLayListener());
        Picasso.with(getContext()).load(originalImageUrl).fit().centerInside().placeholder(thumpNailDrawable).into(photoView, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap originalBitmap = ((BitmapDrawable)photoView.getDrawable()).getBitmap();
                lightBoxOverLayFragment.setBitmapToSaveToSDCard(originalBitmap);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isRemoving()) getActivity().getSupportFragmentManager().beginTransaction().remove(lightBoxOverLayFragment).commit();
    }

    private PhotoViewAttacher.OnPhotoTapListener createToggleOverLayListener(){
        return new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (getActivity() == null || isRemoving() || !isResumed()) return;
                if (isOverLayShown){
                    getActivity().getSupportFragmentManager().beginTransaction().remove(lightBoxOverLayFragment).commit();
                }else {
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.relativeLayoutLightBox, lightBoxOverLayFragment).commit();
                }
                isOverLayShown = !isOverLayShown;
            }
        };
    }
}
