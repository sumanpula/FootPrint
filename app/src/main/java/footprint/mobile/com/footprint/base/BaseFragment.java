package footprint.mobile.com.footprint.base;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import footprint.mobile.com.footprint.Constants;
import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.dto.Savings;
import footprint.mobile.com.footprint.fragments.FragmentMain;
import footprint.mobile.com.footprint.framework.BaseMethods;
import footprint.mobile.com.footprint.framework.ViewBackNavigationListener;

/**
 * Created by Suman Pula on 3/1/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public abstract class BaseFragment extends Fragment implements BaseMethods,ViewBackNavigationListener, Constants {
    private static final String TAG = BaseFragment.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setHasOptionsMenu(hasMenu());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try {
            if (getLayout() > 0) {
                view = inflater.inflate(getLayout(), container, false);
                if (hasToolBar() && toolbar() != null) {
                    prepareToolBar();
                } else {
                    getActivity().findViewById(R.id.toolbar).setVisibility(View.GONE);
                }
                onViewCreated(view);
            } else {
                Log.e(TAG, "fragment view layout not set");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public Fragment addOrReplaceFragment(final Fragment fragment, final int frameId) throws Exception {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(frameId, fragment).commit();
        return fragment;
    }

    public void removeFragment(final Fragment fragment) throws Exception {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }

    public Fragment addOrReplaceChildFragment(final Fragment fragment, final int frameId) throws Exception {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameId, fragment).commit();
        return fragment;
    }

    public void removeChildFragment(final Fragment fragment) throws Exception {
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.remove(fragment).commit();
    }

    public void prepareToolBar() {
        try {
            getActivity().setActionBar(toolbar());
            toolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //menu icon click
                }
            });

            toolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    //menu items click if any
                    return false;
                }
            });
            toolbar().setTitle(menuBarTitle());
            toolbar().setNavigationIcon(menuBarIcon());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getMainItems() {
        return getResources().getStringArray(R.array.main_items);
    }

    public int[] getMainIcons() {
        int[] icons = new int[5];
        icons[0] = R.drawable.ic_camera;
        icons[1] = R.drawable.ic_eye;
        icons[2] = R.drawable.ic_amend_details;
        icons[3] = R.drawable.ic_camera;
        return icons;
    }

    public void openCamera() {
        //camera stuff
        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, setFileUri());
        getActivity().startActivityForResult(imageIntent, IMAGE_CAPTURED_CODE);
    }

    public void showFileChooser() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            getActivity().startActivityForResult(intent,
                    FILE_UPLOADED_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Uri setFileUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //folder stuff
        File imagesFolder = new File(getCurrentFolderPath());

        File image = new File(imagesFolder, "fp_" + timeStamp + ".jpeg");
        Uri uriSavedImage = Uri.fromFile(image);
        return uriSavedImage;
    }

    public void checkPermissions() {
        if (isGreaterThanM()) {
            // Check if the Camera permission has been granted
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }
        }
    }

    public static boolean isGreaterThanL() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        return false;
    }

    public static boolean isGreaterThanM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }
        return false;
    }

    public void addNewFootPrint(final String name, final OnFootPrintAdd onFootPrintAdd) throws Exception {
        Set<String> names = new HashSet<>();
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences.getStringSet(PREFS_FOOT_PRINTS, names).size() > 0) {
            names = sharedPreferences.getStringSet(PREFS_FOOT_PRINTS, names);
            names.add(name);
            sharedPreferences.edit().putStringSet(PREFS_FOOT_PRINTS, names).commit();
            onFootPrintAdd.footAdded();
            return;
        }
        names.add(name);
        sharedPreferences.edit().putStringSet(PREFS_FOOT_PRINTS, names).commit();
        onFootPrintAdd.footAdded();
    }

    public interface OnFootPrintAdd {
        void footAdded() throws Exception;
    }

    public void removeFootPrint(final String name) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences.getStringSet(PREFS_FOOT_PRINTS, new HashSet<String>()).size() > 0) {
            sharedPreferences.getStringSet(PREFS_FOOT_PRINTS, new HashSet<String>()).remove(name);
        }
    }

    public List<String> getFootPrintNames() {
        SharedPreferences sharedPreferences = getSharedPreferences();
        Set<String> names = sharedPreferences.getStringSet(PREFS_FOOT_PRINTS, new HashSet<String>());
        return addToList(sharedPreferences.getStringSet(PREFS_FOOT_PRINTS, new HashSet<String>()));
    }

    private SharedPreferences getSharedPreferences() {
        return getActivity().getSharedPreferences(PREFS_FOOT_PRINTS, Context.MODE_PRIVATE);
    }

    private List<String> addToList(final Set<String> names) {
        List<String> nameList = new ArrayList<>();
        for (String item : names) {
            nameList.add(item);
        }
        return nameList;
    }

    public void setSelectFootPrintName(final String name) {
        Savings.getSavings().setSelectedFootPrint(name);
    }

    public String getSelectedFootPrint() {
        return Savings.getSavings().getSelectedFootPrint();
    }

    public String getCurrentFolderPath() {
        File file = new File(FILE_PATH+"/"+getSelectedFootPrint());
        file.mkdirs();
        return file.getAbsolutePath();
    }

    public void goHome() {
        try {
            addOrReplaceFragment(new FragmentMain(),R.id.frame_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
