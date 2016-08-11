package com.prototype.shane.shaneprototype.controller.sdFileScan;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.controller.displaySDFile.ControllerDisplaySDFile;
import com.prototype.shane.shaneprototype.model.displayFileModel.DisplayFileModel;
import com.prototype.shane.shaneprototype.model.fileObj.FileFrequencyModel;
import com.prototype.shane.shaneprototype.model.fileObj.FileSizeModel;
import com.prototype.shane.shaneprototype.util.Const;
import com.prototype.shane.shaneprototype.util.Util;
import com.prototype.shane.shaneprototype.view.sdFileScan.FragmentSDFileScan;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by shane on 8/10/16.
 */
public class ControllerSDFileScan extends FragmentSDFileScan{
    PriorityQueue<FileSizeModel> fileSizeHeap;
    HashMap<String, FileFrequencyModel> fileFrequencyModelHashMap;

    int totalFile = 0;
    long totalFileSize = 0;

    FileScannerTask fileScannerTask;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.setOnBackPressListener(new MainActivity.OnBackPressListener() {
//            @Override
//            public boolean onBackPress() {
//                Log.e("shaneTest", "called here");
//                if(fileScannerTask != null) {
//                    fileScannerTask.cancel(true);
//                    return true;
//                }
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        MainActivity mainActivity = (MainActivity) getActivity();
//        mainActivity.setOnBackPressListener(null);
//    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setOnStartScaningListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Const.SHANE_LOG, "can write = " + Util.isExternalStorageWritable());
                Log.e(Const.SHANE_LOG, "can read = " + Util.isExternalStorageReadable());
                Log.e(Const.SHANE_LOG, "total file = " + Environment.getExternalStorageDirectory().listFiles().length);
                fileScannerTask = new FileScannerTask();
                fileScannerTask.execute();
            }
        });

        setOnGetLargestFileListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<DisplayFileModel> displayFileModels = new ArrayList<>();
                int i = 0;
                while (!fileSizeHeap.isEmpty() && i++ <= 10) {
                    FileSizeModel fileSizeModel = fileSizeHeap.poll();
                    displayFileModels.add(new DisplayFileModel(getString(R.string.fileName) + fileSizeModel.fileName, getString(R.string.fileSize) + Util.getDisplayedFileSize(fileSizeModel.size)));
                }

                ControllerDisplaySDFile displaySDFileController = ControllerDisplaySDFile.create(displayFileModels);
                getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content, displaySDFileController).addToBackStack(null).commit();
            }
        });

        setOnGetTopFrequencyListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<FileFrequencyModel> fileFrequencyModels = new ArrayList<>(fileFrequencyModelHashMap.values());
                Collections.sort(fileFrequencyModels, new Comparator<FileFrequencyModel>() {
                    @Override
                    public int compare(FileFrequencyModel lhs, FileFrequencyModel rhs) {
                        return lhs.appearCount < rhs.appearCount ? 1 : lhs.appearCount > rhs.appearCount ? -1 : 0;
                    }
                });

                ArrayList<DisplayFileModel> displayFileModels = new ArrayList<>();
                for (int j = 0; j < fileFrequencyModels.size() && j <= 5; j++) {
                    FileFrequencyModel fileFrequencyModel = fileFrequencyModels.get(j);
                    displayFileModels.add(new DisplayFileModel(getString(R.string.fileExtension) + fileFrequencyModel.extension, getString(R.string.fileFrequency) + fileFrequencyModel.appearCount + "/" + totalFile));
                }

                ControllerDisplaySDFile displaySDFileController = ControllerDisplaySDFile.create(displayFileModels);
                getActivity().getSupportFragmentManager().beginTransaction().replace(android.R.id.content, displaySDFileController).addToBackStack(null).commit();
            }
        });
    }

    private class FileScannerTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... params) {
            walkdir(Environment.getExternalStorageDirectory(), 100, 0);
            return "Task Completed.";
        }
        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();
            if (totalFile > 0){
                String text = getString(R.string.everageFileSize) + Util.getDisplayedFileSize(totalFileSize / totalFile);
                setAverageSize(text);
            }
            if (!fileSizeHeap.isEmpty()) getView().findViewById(R.id.buttonLargestFiles).setVisibility(View.VISIBLE);
            if (!fileFrequencyModelHashMap.isEmpty()) getView().findViewById(R.id.buttonMostFrequentFiles).setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPreExecute() {
            showProgressDialog();
            setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                }
            });
            fileSizeHeap = new PriorityQueue<>(10, new Comparator<FileSizeModel>() {
                @Override
                public int compare(FileSizeModel lhs, FileSizeModel rhs) {
                    int result = 0;
                    result = lhs.size > rhs.size ? -1 : 1;
                    return result;
                }
            });

            fileFrequencyModelHashMap = new HashMap<>();
            getView().findViewById(R.id.buttonMostFrequentFiles).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.buttonLargestFiles).setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            updateProgress(values[0]);
        }

        public void walkdir(File dir, int previousPercentageInterval, int previousPercentage) {
            if (isCancelled()) {
                Log.e(Const.SHANE_LOG, "canceled task");
                return;
            }
            File[] listFile = dir.listFiles();

            if (listFile != null && listFile.length > 0) {
                int currentPercentageInterval = previousPercentageInterval / listFile.length;

                for (int i = 0; i < listFile.length; i++) {
                    if (isCancelled()) {
                        Log.e(Const.SHANE_LOG, "canceled task");
                        return;
                    }
                    int currentPercentage = previousPercentage + i * currentPercentageInterval;
                    totalFile++;
                    if (listFile[i].isDirectory()) {
                        walkdir(listFile[i], currentPercentageInterval, currentPercentage);
                    } else {
                        totalFileSize+=listFile[i].length();
                        onProgressUpdate(currentPercentage);
                        fileSizeHeap.offer(new FileSizeModel(listFile[i].getName(), listFile[i].length()));

                        String extension = Util.getFileExtension(listFile[i].getName());
                        if (extension.isEmpty()) continue;

                        FileFrequencyModel fileFrequencyModel = new FileFrequencyModel(extension);
                        if (!fileFrequencyModelHashMap.containsKey(extension)) fileFrequencyModelHashMap.put(extension, fileFrequencyModel);
                        fileFrequencyModelHashMap.get(extension).appearCount++;
                    }
                }
            }
        }
    }
}
