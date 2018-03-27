package footprint.mobile.com.footprint;

import android.content.Intent;
import android.os.Bundle;

import footprint.mobile.com.footprint.base.BaseFragActivity;
import footprint.mobile.com.footprint.base.BaseFragment;
import footprint.mobile.com.footprint.fragments.FragmentCreateFootPrint;
import footprint.mobile.com.footprint.fragments.FragmentEditFootPrints;
import footprint.mobile.com.footprint.fragments.FragmentMain;
import footprint.mobile.com.footprint.fragments.FragmentShowImage;
import footprint.mobile.com.footprint.fragments.FragmentSplashScreen;
import footprint.mobile.com.footprint.fragments.FragmentViewFootPrintList;
import footprint.mobile.com.footprint.fragments.FragmentViewFootPrints;
import footprint.mobile.com.footprint.framework.IOnAttachmentReceived;

public class MainActivity extends BaseFragActivity implements Constants {

    private IOnAttachmentReceived onAttachmentReceived;

    @Override
    public int getLayout() throws Exception {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityReady(Bundle bundle) throws Exception {
        addOrReplaceFragment(new FragmentSplashScreen(), R.id.frame_container);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case FILE_UPLOADED_CODE:
                    if (resultCode == RESULT_OK) {
                        //send the to caller that hs received here
                        onAttachmentReceived.onUploadedFileReceived(data.getData());
                    } else if (requestCode == RESULT_CANCELED) {
                        onAttachmentReceived.onUserCancelled();
                    }
                    break;
                case IMAGE_CAPTURED_CODE:
                    if (resultCode == RESULT_OK) {
                        onAttachmentReceived.onCapturedImage();
                    } else if (requestCode == RESULT_CANCELED) {
                        onAttachmentReceived.onUserCancelled();
                    }
                    break;
                default:
                    //cancelled
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void setOnAttachmentReceived(IOnAttachmentReceived onAttachmentReceived) {
        this.onAttachmentReceived = onAttachmentReceived;
    }

    @Override
    public void onBackPressed() {
        try {
            final android.support.v4.app.Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_container);
            if (fragment != null && fragment.isVisible()) {
                if (fragment instanceof FragmentMain) {
                    super.onBackPressed();
                } else  if (fragment instanceof FragmentCreateFootPrint) {
                    ((BaseFragment) fragment).onBackButtonClick();
                } else  if (fragment instanceof FragmentViewFootPrintList) {
                    ((BaseFragment) fragment).onBackButtonClick();
                } else  if (fragment instanceof FragmentViewFootPrints) {
                    ((BaseFragment) fragment).onBackButtonClick();
                } else  if (fragment instanceof FragmentShowImage) {
                    ((BaseFragment) fragment).onBackButtonClick();
                } else  if (fragment instanceof FragmentEditFootPrints) {
                    ((BaseFragment) fragment).onBackButtonClick();
                }
            }
           // addOrReplaceFragment(new FragmentMain(),R.id.frame_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMenuClick() throws Exception {

    }

    @Override
    public void onBackButtonClick() throws Exception {

    }
}
