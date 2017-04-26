/*
Copyright 2014 Scott Logic Ltd

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package com.prototype.shane.shaneprototype.view.soundMeter;

import android.app.Fragment;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.view.BaseFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class SoundMeterFragment extends BaseFragment implements SoundLevelUpdateable {

    private static final int sampleRate = 8000;
    private AudioRecord audio;
    private int bufferSize;
    private double lastLevel = 0;
    private Thread thread;
    private static final int SAMPLE_DELAY = 75;

    private SamplingSoundMeter samplingSoundMeter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sound_meter, container, false);

        try {
            bufferSize = AudioRecord
                    .getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO,
                            AudioFormat.ENCODING_PCM_16BIT);
        } catch (Exception e) {
            android.util.Log.e("TrackingFlow", "Exception", e);
        }

//        samplingSoundMeter = new SamplingSoundMeter(this);
//
//        // Add some button handlers
//        rootView.findViewById(R.id.soundmeter_start_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                samplingSoundMeter.Start(1000);
//            }
//        });
//
//        rootView.findViewById(R.id.soundmeter_stop_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                samplingSoundMeter.Stop();
//            }
//        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        audio = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT, bufferSize);

        audio.startRecording();
        thread = new Thread(new Runnable() {
            public void run() {
                while(thread != null && !thread.isInterrupted()){
                    //Let's make the thread sleep for a the approximate sampling time
                    try{Thread.sleep(SAMPLE_DELAY);}catch(InterruptedException ie){ie.printStackTrace();}
                    readAudioBuffer();//After this call we can get the last value assigned to the lastLevel variable

                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Log.e("shaneTest", "last level = " + lastLevel);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    /**
     * Functionality that gets the sound level out of the sample
     */
    private void readAudioBuffer() {

        try {
            short[] buffer = new short[bufferSize];

            int bufferReadResult = 1;

            if (audio != null) {

                // Sense the voice...
                bufferReadResult = audio.read(buffer, 0, bufferSize);
                double sumLevel = 0;
                for (int i = 0; i < bufferReadResult; i++) {
                    sumLevel += buffer[i];
                }
                lastLevel = Math.abs((sumLevel / bufferReadResult));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        thread.interrupt();
        thread = null;
        try {
            if (audio != null) {
                audio.stop();
                audio.release();
                audio = null;
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    @Override
    public void setPeakValue(int peakValue) {
        Log.e("shaneTest", "peakValue = " + peakValue);
        ((TextView)getView().findViewById(R.id.textViewPeak)).setText("Peak: " + peakValue);
    }

    @Override
    public void setRmsValue(int rmsValue) {
        Log.e("shaneTest", "rmsValue = " + rmsValue);
        ((TextView)getView().findViewById(R.id.textViewRMS)).setText("RMS: " + rmsValue);
    }
}
