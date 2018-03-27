package footprint.mobile.com.footprint.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.TextView;

import footprint.mobile.com.footprint.Constants;
import footprint.mobile.com.footprint.R;

/**
 * Created by Suman Pula on 3/10/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class ChooseFilesBottomSheet extends BottomSheetDialogFragment implements Constants {
    private GalleryOptionClickEventListener optionClickEventListener;

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final MyViewClickListener myViewClickListener = new MyViewClickListener();
        View view = View.inflate(getContext(), R.layout.choose_files_bottom_sheets, null);
        final TextView tvFromCamera = (TextView) view.findViewById(R.id.tvFromCamera);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvFromCamera.setBackground(getActivity().getDrawable(R.drawable.ripple_effect));
        }
        tvFromCamera.setOnClickListener(myViewClickListener);

        final TextView tvFromGallery = (TextView) view.findViewById(R.id.tvFromGallery);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tvFromGallery.setBackground(getActivity().getDrawable(R.drawable.ripple_effect));
        }
        tvFromGallery.setOnClickListener(myViewClickListener);

        dialog.setContentView(view);
    }

    public interface GalleryOptionClickEventListener {

        void onFromCameraEvent(final View view) throws Exception;

        void onFromGalleryEvent(final View view) throws Exception;
    }

    public void setGalleryOptionClickEventListener(GalleryOptionClickEventListener optionClickEventListener) {
        this.optionClickEventListener = optionClickEventListener;
    }


    private class MyViewClickListener implements View.OnClickListener {

        @Override
        public void onClick(final View view) {
            Handler handler = new Handler();
            switch (view.getId()) {
                case R.id.tvFromGallery:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                optionClickEventListener.onFromGalleryEvent(view);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },RIPPLE_EFFECT_INTERVAL);
                    break;
                case R.id.tvFromCamera:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                optionClickEventListener.onFromCameraEvent(view);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },RIPPLE_EFFECT_INTERVAL);
                    break;
            }
        }
    }
}