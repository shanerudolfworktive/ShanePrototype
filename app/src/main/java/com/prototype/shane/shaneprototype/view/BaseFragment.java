package com.prototype.shane.shaneprototype.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

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
}
