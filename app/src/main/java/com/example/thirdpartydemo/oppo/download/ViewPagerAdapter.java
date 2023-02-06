package com.example.thirdpartydemo.oppo.download;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.thirdpartydemo.oppo.fragment.DownloadFragment;
import com.example.thirdpartydemo.oppo.fragment.UploadFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<String> list;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, List<String> list) {
        super(fragmentManager, lifecycle);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return new DownloadFragment();
        } else if(position == 1){
            return new UploadFragment();
        }
        return new DownloadFragment();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
