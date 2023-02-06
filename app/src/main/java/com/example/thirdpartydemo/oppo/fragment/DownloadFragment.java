package com.example.thirdpartydemo.oppo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thirdpartydemo.R;
import com.example.thirdpartydemo.oppo.download.DownloadAdapter;
import com.example.thirdpartydemo.oppo.entity.DownloadApp;

import java.util.ArrayList;
import java.util.List;

public class DownloadFragment extends Fragment {

    private RecyclerView mDownloadRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_download, container, false);

        mDownloadRecyclerView = view.findViewById(R.id.rv_download);
        initData();
        return view;
    }

    private void initData() {
        List<DownloadApp> appList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            DownloadApp downloadApp = new DownloadApp("内涵段子"+i,"57M",R.drawable.innertalk,"http://a.dxiazaicc.com/apk/neihanduanzi_downcc.apk");
            appList.add(downloadApp);
        }

        DownloadAdapter downloadAdapter = new DownloadAdapter(appList);
        mDownloadRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDownloadRecyclerView.setAdapter(downloadAdapter);
    }
}
