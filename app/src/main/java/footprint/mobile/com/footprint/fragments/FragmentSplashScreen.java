package footprint.mobile.com.footprint.fragments;

import android.os.Handler;
import android.view.View;

import java.io.File;

import footprint.mobile.com.footprint.Constants;
import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.base.BaseFragment;
import footprint.mobile.com.footprint.dto.Savings;

/**
 * Created by Suman Pula on 3/1/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class FragmentSplashScreen extends BaseFragment implements Constants {
    @Override
    public int getLayout() throws Exception {
        return R.layout.spash_screen;
    }

    @Override
    public void onViewCreated(View view) throws Exception {

        if (Savings.getSavings().footPrintsList().size() <= 0) {
            Savings.getSavings().setSelectedFootPrint(getString(R.string.tv_create_foot_print));
            Savings.getSavings().setFootPrints(getString(R.string.tv_create_foot_print));
        }

        File path = new File(FILE_PATH);
        path.mkdirs();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    addOrReplaceFragment(new FragmentMain(), R.id.frame_container);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, SPLASH_TIME);
    }

    @Override
    public android.widget.Toolbar toolbar() throws Exception {
        return null;
    }

    @Override
    public int menuBarTitle() throws Exception {
        return 0;
    }

    @Override
    public int menuBarIcon() throws Exception {
        return 0;
    }

    @Override
    public boolean hasToolBar() throws Exception {
        return false;
    }

    @Override
    public boolean hasMenu() throws Exception {
        return false;
    }

    @Override
    public void onMenuClick() throws Exception {

    }

    @Override
    public void onBackButtonClick() throws Exception {

    }
}
