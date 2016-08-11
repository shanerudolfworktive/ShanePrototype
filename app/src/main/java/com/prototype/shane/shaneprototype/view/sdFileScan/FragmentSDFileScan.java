package com.prototype.shane.shaneprototype.view.sdFileScan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.view.BaseFragment;

/**
 * Created by shane on 8/10/16.
 */
public class FragmentSDFileScan extends BaseFragment{
    private int topLargeVisibility;
    private int topFrequencyVisibility;
    private String averageFileSize;

    ProgressDialog progressDialog;
    private int progress;
    private boolean isDialogShown;
    protected DialogInterface.OnCancelListener onCancelListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file_scan, container, false);

        progressDialog = createProgressDialog();

        if (savedInstanceState != null) reloadViewState(rootView);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("shaneTest", "visibility =" + getView().findViewById(R.id.buttonLargestFiles).getVisibility());
        topLargeVisibility = getView().findViewById(R.id.buttonLargestFiles).getVisibility();
        topFrequencyVisibility = getView().findViewById(R.id.buttonMostFrequentFiles).getVisibility();
        TextView textViewEverage = (TextView) getView().findViewById(R.id.textViewEverageFileSize);
        averageFileSize = textViewEverage.getText().toString();

        progress = progressDialog.getProgress();
        isDialogShown = progressDialog.isShowing();
    }

    protected ProgressDialog createProgressDialog(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Scanning SD Card");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgress(0);
        return progressDialog;
    }

    protected void dismissProgressDialog(){
        progressDialog.dismiss();
    }

    protected void showProgressDialog(){
        progressDialog.show();
    }

    protected void updateProgress(int progress){
        progressDialog.setProgress(progress);
    }

    protected void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener){
        this.onCancelListener = onCancelListener;
        progressDialog.setOnCancelListener(onCancelListener);
    }

    protected void reloadViewState(View rootView){
        rootView.findViewById(R.id.buttonLargestFiles).setVisibility(topLargeVisibility);
        rootView.findViewById(R.id.buttonMostFrequentFiles).setVisibility(topFrequencyVisibility);
        TextView textViewEverage = (TextView) rootView.findViewById(R.id.textViewEverageFileSize);
        textViewEverage.setText(averageFileSize);

        if (isDialogShown) progressDialog.show();
        progressDialog.setProgress(progress);
        progressDialog.setOnCancelListener(onCancelListener);
    }

    protected void setAverageSize(String everageSize){
        TextView textViewEverage = (TextView) getView().findViewById(R.id.textViewEverageFileSize);
        textViewEverage.setText(everageSize);
    }

    protected void setOnStartScaningListener(View.OnClickListener onStartScaningListener){
        getView().findViewById(R.id.buttonStartScan).setOnClickListener(onStartScaningListener);
    }

    protected void setOnGetLargestFileListener(View.OnClickListener onGetLargestFileListener){
        getView().findViewById(R.id.buttonLargestFiles).setOnClickListener(onGetLargestFileListener);
    }

    protected void setOnGetTopFrequencyListener(View.OnClickListener onGetTopFrequencyListener){
        getView().findViewById(R.id.buttonMostFrequentFiles).setOnClickListener(onGetTopFrequencyListener);
    }
}
