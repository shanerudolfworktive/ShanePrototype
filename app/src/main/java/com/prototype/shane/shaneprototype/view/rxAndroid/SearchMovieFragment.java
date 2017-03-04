package com.prototype.shane.shaneprototype.view.rxAndroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.rxAndroid.MovieSearchModel;
import com.prototype.shane.shaneprototype.util.Const;
import com.prototype.shane.shaneprototype.volley.GsonRequest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shane on 3/1/17.
 */

public class SearchMovieFragment extends Fragment {
    EditText editTextSearchMoview;

    private RecyclerView mRecyclerView;
    private MovieListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Disposable mDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_movie, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewMovieList);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        editTextSearchMoview = (EditText)rootView.findViewById(R.id.editTextSearchMoview);

        Button buttonSearchMoview = (Button) rootView.findViewById(R.id.buttonSearchMoview);
        buttonSearchMoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMovie(editTextSearchMoview.getText().toString());
            }
        });

        mAdapter.setOnMovieListClickListener(new MovieListAdapter.MovieListClickListener() {
            @Override
            public void onClick(MovieSearchModel.MovieModel movieModel) {

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Observable<String> textChangeStream = createTextChangeObservable();
        mDisposable = textChangeStream.observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) {

                    }
                })
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        searchMovie(s);
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.dispose();
    }

    GsonRequest<MovieSearchModel> gsonRequest;

    private Observable<String> createTextChangeObservable() {
        //2
        Observable<String> textChangeObservable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                //3
                final TextWatcher watcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void afterTextChanged(Editable s) {}

                    //4
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        emitter.onNext(s.toString());
                    }
                };

                //5
                editTextSearchMoview.addTextChangedListener(watcher);

                //6
                emitter.setCancellable(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        editTextSearchMoview.removeTextChangedListener(watcher);
                    }
                });
            }
        });

        return textChangeObservable
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String query) throws Exception {
                        return query.length() >= 2;
                    }
                }).debounce(1000, TimeUnit.MILLISECONDS);  // add this line
    }

    private void searchMovie(String title){
        String movieSearchUrl = Const.movieUrl + title;

        gsonRequest = new GsonRequest<MovieSearchModel>(movieSearchUrl, MovieSearchModel.class, new HashMap<String, String>()) {
            @Override
            protected void deliverResponse(MovieSearchModel response, boolean isFromCache) {
                Log.d("shaneTest", "response = " + new Gson().toJson(response));
                mAdapter.setMovieSearchModel(response);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void deliverError(VolleyError error, MovieSearchModel cachedResponse) {
                Log.e("shaneTest", "error = " + error.getMessage());
            }
        }.sendRequest(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (gsonRequest != null) gsonRequest.cancel();
    }
}
