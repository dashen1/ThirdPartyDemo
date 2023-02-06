package com.example.thirdpartydemo.oppo.download;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.Navigation;

import com.example.thirdpartydemo.R;
import com.google.android.material.tabs.TabLayout;

public class OppoDownloadActivity extends AppCompatActivity {


     private FragmentContainerView navigationController;
     private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_oppo_download);

        navigationController = findViewById(R.id.nav_host_fragment);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e("TAG","position = "+tab.getPosition());
                if (tab.getPosition() == 0){
                    Navigation.findNavController(navigationController).navigate(R.id.downloadFragment2);
                } else {
                    Navigation.findNavController(navigationController).navigate(R.id.uploadFragment2);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
