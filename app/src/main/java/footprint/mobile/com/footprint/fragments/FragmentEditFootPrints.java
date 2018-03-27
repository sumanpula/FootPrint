package footprint.mobile.com.footprint.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.adapter.AdapterShowImagesToEdt;
import footprint.mobile.com.footprint.base.BaseFragment;

/**
 * Created by Suman Pula on 3/14/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class FragmentEditFootPrints extends BaseFragment {
    private boolean isLongPressed = false;
    private boolean isSelected = false;
    private List<File> selectedFiles = new ArrayList<>();

    @Override
    public void onMenuClick() throws Exception {
        goHome();
    }

    @Override
    public void onBackButtonClick() throws Exception {
        goHome();
    }

    @Override
    public int getLayout() throws Exception {
        return R.layout.fragment_edit_foot_prints;
    }

    @Override
    public void onViewCreated(View view) throws Exception {
        final ImageView ivHome = view.findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goHome();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final RecyclerView rvImages = view.findViewById(R.id.rvImages);
        rvImages.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        final AdapterShowImagesToEdt adapterShowImagesToEdt = new AdapterShowImagesToEdt(getFiles());
        rvImages.setAdapter(adapterShowImagesToEdt);
        adapterShowImagesToEdt.setOnEditImageClickListener(new AdapterShowImagesToEdt.OnEditImageClickListener() {
            @Override
            public void onImageClick(final File file, final CardView cardView) throws Exception {
                if (isLongPressed) {
                    if (isSelected) {
                        cardView.setBackgroundResource(0);
                        selectedFiles.remove(file);
                        isSelected = false;
                    } else {
                        cardView.setBackground(getResources().getDrawable(R.drawable.image_selected_bg));
                        selectedFiles.add(file);
                        isSelected = true;
                    }
                } else {
                    //move to show image with delete
                    final FragmentShowImage fragmentShowImage = new FragmentShowImage();
                    addOrReplaceFragment(fragmentShowImage, R.id.frame_container);
                    fragmentShowImage.setShowImageCallBack(new FragmentShowImage.ShowImageCallBack() {
                        @Override
                        public Bitmap setBitmap() throws Exception {
                            return BitmapFactory.decodeFile(file.getAbsolutePath());
                        }

                        @Override
                        public File file() throws Exception {
                            return file;
                        }
                    });
                }
            }

            @Override
            public void onImageLongPress(List<File> fileListe) throws Exception {
                isLongPressed = true;
            }
        });

        final TextView tvCancel = view.findViewById(R.id.tvCancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    goHome();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        final TextView tvDelete = view.findViewById(R.id.tvDelete);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (selectedFiles.size() > 0) {
                        for (File file : selectedFiles) {
                            file.delete();
                            adapterShowImagesToEdt.updateFiles(file);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Toolbar toolbar() throws Exception {
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

    private List<File> getFiles() {
        List<File> files = new ArrayList<>();
        final File file = new File(getCurrentFolderPath());
        for (File f : file.listFiles()) {
            files.add(f);
        }
        return files;
    }
}
