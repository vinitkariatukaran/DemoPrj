package com.demoprj.Fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Vinty on 12/27/2015.
 */
public class BaseFragment extends Fragment {

    public boolean isPaused = true ;
    boolean isStopped = true ;

    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
    }
}
