package com.prototype.shane.shaneprototype.view.paintCode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.view.BaseFragment;

/**
 * Created by shane on 3/6/17.
 */

public class PaintCodeFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_paintcode, container, false);

        final RobotView robotView = (RobotView) rootView.findViewById(R.id.robotView);

        SeekBar seekBarAngle = (SeekBar)rootView.findViewById(R.id.seekBarAngle);
        seekBarAngle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                robotView.setAngle(230 - progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekBarColor = (SeekBar)rootView.findViewById(R.id.seekBarColor);
        seekBarColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                robotView.setRed(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return rootView;
    }
}
