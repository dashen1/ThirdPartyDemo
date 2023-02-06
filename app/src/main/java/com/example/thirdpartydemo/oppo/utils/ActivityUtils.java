package com.example.thirdpartydemo.oppo.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int fragmentId){

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentId, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}
