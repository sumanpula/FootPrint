package footprint.mobile.com.footprint.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import java.io.File;

import footprint.mobile.com.footprint.Constants;
import footprint.mobile.com.footprint.dto.Savings;
import footprint.mobile.com.footprint.framework.ActivityBaseMethods;
import footprint.mobile.com.footprint.framework.ViewBackNavigationListener;

/**
 * Created by Suman Pula on 3/1/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public abstract class BaseFragActivity extends FragmentActivity implements ActivityBaseMethods, ViewBackNavigationListener,
        Constants {

    private static final String TAG = BaseFragActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getLayout() > 0) {
               setContentView(getLayout());
               onActivityReady(savedInstanceState);
            } else {
                Log.e(TAG,"activity view not set");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Fragment addOrReplaceFragment(final Fragment fragment,final int frameId) throws Exception {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameId,fragment).commit();
        return fragment;
    }

    public void removeFragment(final Fragment fragment)  throws Exception {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }
    public String getCurrentFolderPath() {
        File file = new File(FILE_PATH+File.separator+ Savings.getSavings().getSelectedFootPrint());
        file.mkdirs();
        return file.getAbsolutePath();
    }
}
