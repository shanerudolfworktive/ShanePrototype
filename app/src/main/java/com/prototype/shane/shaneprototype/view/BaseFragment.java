package com.prototype.shane.shaneprototype.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;

import com.android.volley.Request;

import java.util.ArrayList;
import java.util.List;

public class BaseFragment extends Fragment {
	protected List<OnViewCreatedListener> onViewCreatedListeners;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true	);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//getView() might be null if this fragment is a fragment holder (viewless)
		if (onViewCreatedListeners != null && onViewCreatedListeners.size() > 0) {
			for (OnViewCreatedListener onViewCreatedListener : onViewCreatedListeners) {
				onViewCreatedListener.onViewCreated(view, savedInstanceState, this);
			}
		}
	}

	public interface OnViewCreatedListener{
		void onViewCreated(View view, Bundle savedInstanceState, Fragment fragment);
	}

	protected ArrayList<Request> requestToCancelOnDestroy= new ArrayList<>();
	public OnResumeListener onResumeListener;
	private boolean mNeedToAvoidAnimation;

	public interface OnResumeListener{
		void onResume();
	}

	public void setOnResumeListener(OnResumeListener onResumeListener){
		this.onResumeListener = onResumeListener;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mNeedToAvoidAnimation = true;
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		// This avoids the transaction animation when the orienatation changes
		boolean needToAvoidAnimation = mNeedToAvoidAnimation;
		mNeedToAvoidAnimation = false;
		return needToAvoidAnimation ? new Animation() {
		} : super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (onResumeListener != null) onResumeListener.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		for (Request request: requestToCancelOnDestroy) request.cancel();
	}
}
