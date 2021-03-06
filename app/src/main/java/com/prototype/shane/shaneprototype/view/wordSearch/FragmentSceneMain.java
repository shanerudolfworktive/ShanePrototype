package com.prototype.shane.shaneprototype.view.wordSearch;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.wordSearch.GameDisplayResponseModel;
import com.prototype.shane.shaneprototype.util.Const;
import com.prototype.shane.shaneprototype.view.BaseFragment;
import com.prototype.shane.shaneprototype.volley.VolleyHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by shane on 6/24/16.
 */
public class FragmentSceneMain extends BaseFragment {

    private ArrayList<GameDisplayResponseModel> gameDataArrayList = new ArrayList<>();

    View.OnClickListener hintOnClickListener;
    TextView sourceWord;
    AlertDialog.Builder replayDialogBuilder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchGameDataAnStartGame();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void fetchGameDataAnStartGame(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.URL_GAME_DATA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String lines[] = response.split("\\r?\\n");//split by new line
                gameDataArrayList.clear();
                for (String l: lines) {
                    GameDisplayResponseModel model = new Gson().fromJson(l, GameDisplayResponseModel.class);
                    gameDataArrayList.add(model);
                }
                if (isResumed()){
                    displayGmaeData(0);
                }else {
                    setOnResumeListener(new OnResumeListener() {
                        @Override
                        public void onResume() {
                            displayGmaeData(0);
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyHelper.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scene_main, container, false);

        //restore states
        if (hintOnClickListener != null) rootView.findViewById(R.id.buttonShowHint).setOnClickListener(hintOnClickListener);
        if (sourceWord != null) {
            TextView textView = (TextView) rootView.findViewById(R.id.textViewSourceWord);
            textView.setText(sourceWord.getText().toString());
            sourceWord = textView;
        }
        if (replayDialogBuilder!=null)replayDialogBuilder.show();
        return rootView;
    }

    FragmentGamePlayMain fragmentGamePlayMain;
    private void displayGmaeData(final int progress){
        if (progress >= gameDataArrayList.size()) return;
        final GameDisplayResponseModel progressData = gameDataArrayList.get(progress);
        sourceWord = (TextView) getView().findViewById(R.id.textViewSourceWord);
        sourceWord.setText(progressData.word);

        fragmentGamePlayMain = FragmentGamePlayMain.create(progressData.characterGrid);
        hintOnClickListener = new View.OnClickListener() {
            int hintCount = 1;
            @Override
            public void onClick(View v) {
                if (!progressData.wordLocations.keySet().iterator().hasNext()) return;
                String[] location = progressData.wordLocations.keySet().iterator().next().split(",");
                if (hintCount == 1) {
                    fragmentGamePlayMain.highLightCell(Integer.parseInt(location[0]), Integer.parseInt(location[1]));
                    hintCount++;
                }else{
                    fragmentGamePlayMain.highLightCell(Integer.parseInt(location[location.length - 2]), Integer.parseInt(location[location.length - 1]));
                    hintCount = 1;
                }
            }
        };
        getView().findViewById(R.id.buttonShowHint).setOnClickListener(hintOnClickListener);

        fragmentGamePlayMain.setOnWordSelectedListen(new FragmentGamePlayMain.OnWordSelectedListener() {
            @Override
            public boolean onWordSelected(String word) {
                boolean foundCorrect = false;

                for (Iterator<Map.Entry<String, String>> iterator = progressData.wordLocations.entrySet().iterator(); iterator.hasNext(); ) {
                    Map.Entry<String, String> entry = iterator.next();
                    if (entry.getValue().equals(word)) {
                        foundCorrect = true;
                        iterator.remove();
                        break;
                    }
                }

                if (progressData.wordLocations.isEmpty()) {
                    displayGmaeData(progress + 1);
                }

                if (progress + 1 >= gameDataArrayList.size()) {
                    replayDialogBuilder = new AlertDialog.Builder(getActivity());
                    replayDialogBuilder.setTitle(R.string.congratulations);
                    replayDialogBuilder.setCancelable(false);
                    replayDialogBuilder.setPositiveButton(R.string.next_level, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            replayDialogBuilder = null;
                            fetchGameDataAnStartGame();
                        }
                    });
                    replayDialogBuilder.show();
                }
                return foundCorrect;
            }
        });
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentGamePlayMain).commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isRemoving()) if (fragmentGamePlayMain != null) getActivity().getSupportFragmentManager().beginTransaction().remove(fragmentGamePlayMain).commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }
}
