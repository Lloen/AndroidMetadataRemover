package com.example.metadataremover;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    List<FileData> chosenFilesList;

    public FileAdapter(List<FileData> chosenFilesList) {
        this.chosenFilesList = chosenFilesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chosenfiles_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileData fileData = chosenFilesList.get(position);
        holder.itemView.setBackground(fileData.thumbnail);
        holder.txtFileName.setText(fileData.name);
    }

    @Override
    public int getItemCount() {
        return chosenFilesList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFileName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFileName = itemView.findViewById(R.id.txtFileName);
        }
    }
}
