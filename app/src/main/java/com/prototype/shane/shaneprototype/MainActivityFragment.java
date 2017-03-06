package com.prototype.shane.shaneprototype;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;
import com.prototype.shane.shaneprototype.controller.musicSearch.ControllerMusicSearch;
import com.prototype.shane.shaneprototype.controller.sdFileScan.ControllerSDFileScan;
import com.prototype.shane.shaneprototype.view.chatApp.FragmentChat;
import com.prototype.shane.shaneprototype.view.flickrSharkGrid.FragmentFlickrSharkGrid;
import com.prototype.shane.shaneprototype.view.paintCode.PaintCodeFragment;
import com.prototype.shane.shaneprototype.view.rxAndroid.SearchMovieFragment;
import com.prototype.shane.shaneprototype.view.wordSearch.FragmentSceneMain;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import io.reactivex.android.plugins.RxAndroidPlugins;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        rootView.findViewById(R.id.buttonMusicSearchPrototype).setOnClickListener(createMusicSearchClickListener());
        rootView.findViewById(R.id.buttonSDFileScanPrototype).setOnClickListener(createSDFileScanClickListener());
        rootView.findViewById(R.id.buttonFlickrSharkPrototype).setOnClickListener(createFlickrSharkClickListener());
        rootView.findViewById(R.id.buttonWordSearchGamePrototype).setOnClickListener(createWordSearchGameClickListener());
        rootView.findViewById(R.id.buttonSocketChatPrototype).setOnClickListener(createSocketChatClickListener());
        rootView.findViewById(R.id.buttonInAppGmail).setOnClickListener(createInappOnclickListener());
        rootView.findViewById(R.id.buttonRxAndroid).setOnClickListener(createRXAndroidClickListener());
        rootView.findViewById(R.id.buttonPaintCode).setOnClickListener(createPaintCodeRobotClickListener());
        return rootView;
    }

    private View.OnClickListener createMusicSearchClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new ControllerMusicSearch()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createSDFileScanClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new ControllerSDFileScan()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createFlickrSharkClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new FragmentFlickrSharkGrid()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createWordSearchGameClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new FragmentSceneMain()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createRXAndroidClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new SearchMovieFragment()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createPaintCodeRobotClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new PaintCodeFragment()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createSocketChatClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(getId(), new FragmentChat()).addToBackStack(null).commit();
            }
        };
    }

    private View.OnClickListener createInappOnclickListener(){
        appendBLELog("a");
        appendBLELog("b");

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath = getContext().getFilesDir() + "/mytextfile.txt";
                Log.e("shaneTest", "filePath=" + filePath);
                BackgroundMail.newBuilder(getContext())
                        .withUsername("")
                        .withPassword("")
                        .withMailto("")
                        .withSubject("this is the subject")
                        .withBody("this is the body")
                        .withAttachments(filePath)
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                            @Override
                            public void onSuccess() {
                                Log.e("shaneTest", "gmail sucess");
                            }
                        })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {
                            @Override
                            public void onFail() {
                                Log.e("shaneTest", "gmail fail");
                            }
                        })
                        .send();
            }
        };
    }

    public void appendBLELog(String log) {
        try {
            FileOutputStream fileout = getContext().openFileOutput("mytextfile.txt", Context.MODE_PRIVATE | Context.MODE_APPEND);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(log);
            outputWriter.write("\n");
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readBLELog() {
        //reading text from file
        try {
            FileInputStream fileIn=getContext().openFileInput("mytextfile.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn);
            char[] inputBuffer= new char[100];
            StringBuilder sb= new StringBuilder();
            int charRead;

            while ((charRead=InputRead.read(inputBuffer))>0) {
                // char to string conversion
                String readstring=String.copyValueOf(inputBuffer,0,charRead);
                sb.append(readstring);
            }
            InputRead.close();
            Log.e("shaneTest", "sb.length = " + sb.length());
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
