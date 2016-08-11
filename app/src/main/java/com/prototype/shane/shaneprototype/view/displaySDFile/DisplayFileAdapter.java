package com.prototype.shane.shaneprototype.view.displaySDFile;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.displayFileModel.DisplayFileModel;

import java.util.ArrayList;

/**
 * Created by shane on 8/9/16.
 */
public class DisplayFileAdapter extends RecyclerView.Adapter<DisplayFileAdapter.ViewHolder>{

    ArrayList<DisplayFileModel> displayFileModels = new ArrayList<>();

    public void addRow(DisplayFileModel row){
        displayFileModels.add(row);
    }

    public void setDisplayFileModels(ArrayList<DisplayFileModel> displayFileModels){
        this.displayFileModels = displayFileModels;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_display_file, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DisplayFileModel fileModel = displayFileModels.get(position);
        holder.textViewFileName.setText(fileModel.fileName);
        holder.textViewDescription.setText(fileModel.description);

    }

    @Override
    public int getItemCount() {
        return displayFileModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFileName;
        TextView textViewDescription;

        public ViewHolder(View rootView) {
            super(rootView);
            textViewFileName = (TextView) rootView.findViewById(R.id.textViewFileName);
            textViewDescription = (TextView) rootView.findViewById(R.id.textViewDescription);
        }
    }
}
