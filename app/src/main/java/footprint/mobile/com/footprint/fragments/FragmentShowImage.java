package footprint.mobile.com.footprint.fragments;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.base.BaseFragment;

/**
 * Created by Suman Pula on 3/8/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class FragmentShowImage extends BaseFragment {
    private ShowImageCallBack showImageCallBack;
    @Override
    public void onMenuClick() throws Exception {
        backNavigation();
    }

    @Override
    public void onBackButtonClick() throws Exception {
        backNavigation();
    }

    @Override
    public int getLayout() throws Exception {
        return R.layout.fragment_show_image;
    }

    @Override
    public void onViewCreated(View view) throws Exception {

        final TextView textView = view.findViewById(R.id.tvImageName);
        textView.setText(showImageCallBack.file().getName());

        final ImageView  imageView = view.findViewById(R.id.iv);
        imageView.setImageBitmap(showImageCallBack.setBitmap());

        final TextView tvDelete = view.findViewById(R.id.tvDelete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (showImageCallBack.file().exists()) {
                        showImageCallBack.file().delete();
                        backNavigation();
                        Toast.makeText(getActivity(),"file deleted "+showImageCallBack.file().getName(),Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Toolbar toolbar() throws Exception {
        return getActivity().findViewById(R.id.toolbar);
    }

    @Override
    public int menuBarTitle() throws Exception {
        return R.string.menu_delete_foot_print;
    }

    @Override
    public int menuBarIcon() throws Exception {
        return R.drawable.ic_arrow_back;
    }

    @Override
    public boolean hasToolBar() throws Exception {
        return true;
    }

    @Override
    public boolean hasMenu() throws Exception {
        return false;
    }

    public interface ShowImageCallBack {
        Bitmap setBitmap() throws Exception;
        File file() throws Exception;
    }

    public void setShowImageCallBack(ShowImageCallBack showImageCallBack) {
        this.showImageCallBack = showImageCallBack;
    }

    private void backNavigation() {
        try {
            addOrReplaceFragment(new FragmentEditFootPrints(),R.id.frame_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
