package com.example.thirdpartydemo.oppo.download;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpartydemo.R;
import com.example.thirdpartydemo.oppo.customview.DownloadProgressView;
import com.example.thirdpartydemo.oppo.entity.DownloadApp;

import java.util.ArrayList;
import java.util.List;

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private List<DownloadApp> appList;

    public DownloadAdapter(List<DownloadApp> appList) {
        this.appList = appList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.appPicture.setBackgroundResource(appList.get(position).getAppPicture());
        holder.appName.setText(appList.get(position).getAppName());
        holder.appSize.setText(appList.get(position).getAppSize());
        holder.downloadProgressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView appPicture;
        private TextView appName;
        private TextView appSize;
        private DownloadProgressView downloadProgressView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            appPicture = itemView.findViewById(R.id.iv_appPicture);
            appName = itemView.findViewById(R.id.tv_appName);
            appSize = itemView.findViewById(R.id.tv_appSize);
            downloadProgressView = itemView.findViewById(R.id.download_progress);
        }
    }
}

