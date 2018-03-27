package footprint.mobile.com.footprint.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import footprint.mobile.com.footprint.MainActivity;
import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.adapter.MainViewsAdapter;
import footprint.mobile.com.footprint.base.BaseFragment;
import footprint.mobile.com.footprint.dialog.ChooseFilesBottomSheet;
import footprint.mobile.com.footprint.dto.MainItems;
import footprint.mobile.com.footprint.dto.Savings;
import footprint.mobile.com.footprint.framework.IOnAttachmentReceived;
import footprint.mobile.com.footprint.framework.OnMainItemsClickListener;

/**
 * Created by Suman Pula on 3/1/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class FragmentMain extends BaseFragment implements OnMainItemsClickListener, IOnAttachmentReceived {
    @Override
    public int getLayout() throws Exception {
        return R.layout.fragment_min;
    }

    @Override
    public void onViewCreated(View view) throws Exception {
        checkPermissions();


        ((MainActivity) getActivity()).setOnAttachmentReceived(this);

        final MainItems mainItems = new MainItems();
        mainItems.setMainItems(getMainItems());
        mainItems.setMainIcons(getMainIcons());
        final MainViewsAdapter mainViewsAdapter = new MainViewsAdapter(mainItems);
        mainViewsAdapter.setOnMainItemsClickListener(this);

        final RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setAdapter(mainViewsAdapter);

        final TextView tvCreateFootPrint = view.findViewById(R.id.tvCreateFootPrint);

        if (getSelectedFootPrint().isEmpty()) {
            tvCreateFootPrint.setText(getString(R.string.tv_create_foot_print));
        } else {
            tvCreateFootPrint.setText(getSelectedFootPrint());
        }

        tvCreateFootPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addOrReplaceFragment(new FragmentViewFootPrintList(), R.id.frame_container);
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

    @Override
    public void onTakeFootPrintClick() throws Exception {
        if (checkDefault()) {
            return;
        }

        chooseFootPrint();
    }

    @Override
    public void onViewFootPrintClick() throws Exception {
        if (checkDefault()) {
            return;
        }

        if (checkFiles()) {
            return;
        }

        addOrReplaceFragment(new FragmentViewFootPrints(), R.id.frame_container);
    }

    @Override
    public void onAmendDetailsClick() throws Exception {
        if (checkDefault()) {
            return;
        }
        // Toast.makeText(getActivity(),"This feature comes next build",Toast.LENGTH_SHORT).show();
        addOrReplaceFragment(new FragmentCreateFootPrint(), R.id.frame_container);
    }

    @Override
    public void onEditFootPrintClick() throws Exception {
        if (checkDefault()) {
            return;
        }

        if (checkFiles()) {
            return;
        }

        //Toast.makeText(getActivity(),"This feature comes next build",Toast.LENGTH_SHORT).show();
        addOrReplaceFragment(new FragmentEditFootPrints(), R.id.frame_container);
    }

    @Override
    public void onUploadedFileReceived(Uri uri) throws Exception {
        // System.out.println("chosen file saved");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        //folder stuff
        File imagesFolder = new File(getCurrentFolderPath());

        File image = new File(imagesFolder, "fp_" + timeStamp + ".jpeg");
        InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
        OutputStream outputStream = new FileOutputStream(image);
        byte buffer[] = new byte[1024];
        int length = 0;

        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        outputStream.close();
        inputStream.close();
        //saveBitmap(BitmapFactory.decodeFile(uri.getPath()));
        final List<Bitmap>  bitmaps = new ArrayList<>();
        bitmaps.add(BitmapFactory.decodeFile(image.getAbsolutePath()));
        createVideo(bitmaps);
    }

    public void saveBitmap(Bitmap bmp) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File imagesFolder = new File(getCurrentFolderPath());
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs();
        }

        try {
            if (!imagesFolder.exists())
                imagesFolder.mkdirs();
            File file = new File(imagesFolder, timeStamp + ".jpeg");
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();

        } catch (Exception e) {
            Log.e("error in saving image", e.getMessage());
        }
    }

    @Override
    public void onCapturedImage() throws Exception {
        //System.out.println("captured image saved");
    }

    @Override
    public void onUserCancelled() throws Exception {

    }

    @Override
    public String fileName() throws Exception {
        return getFileName();
    }

    private String getFileName() {
        final Date date = new Date();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");
        final String fileName = dateFormat.format(date) + ".jpeg";
        return fileName;
    }

    private boolean checkDefault() {
        if (Savings.getSavings().getSelectedFootPrint().equalsIgnoreCase(getString(R.string.tv_create_foot_print))) {
            Toast.makeText(getActivity(), getString(R.string.toast_select_foot_print), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean checkFiles() {
        final File file = new File(getCurrentFolderPath());
        if (file.listFiles().length <= 0) {
            Toast.makeText(getActivity(), getString(R.string.toast_no_foot_print), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onMenuClick() throws Exception {

    }

    @Override
    public void onBackButtonClick() throws Exception {

    }

    private void chooseFootPrint() throws Exception {
        checkPermissions();

        final ChooseFilesBottomSheet chooseFilesBottomSheet = new ChooseFilesBottomSheet();
        chooseFilesBottomSheet.setGalleryOptionClickEventListener(new ChooseFilesBottomSheet.GalleryOptionClickEventListener() {
            @Override
            public void onFromCameraEvent(View view) throws Exception {

                openCamera();
                chooseFilesBottomSheet.dismiss();
            }

            @Override
            public void onFromGalleryEvent(View view) throws Exception {
                showFileChooser();
                chooseFilesBottomSheet.dismiss();
            }
        });
        //set call back to receive intent received on dashboard_activity activity using method "onActivityResult"
        ((MainActivity) getActivity()).setOnAttachmentReceived(this);
        //show bottom sheet
        chooseFilesBottomSheet.show(getActivity().getSupportFragmentManager(), chooseFilesBottomSheet.getTag());
    }

    private void createVideo(final List<Bitmap> bitmaps) {
        MediaCodec mMediaCodec = null;
        try {
            mMediaCodec = MediaCodec.createEncoderByType("video/mp4");
            MediaFormat mMediaFormat = MediaFormat.createVideoFormat("video/mp4", 320, 240);
            mMediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
            mMediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
            mMediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
            mMediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
            mMediaCodec.configure(mMediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mMediaCodec.start();
            ByteBuffer[] mInputBuffers = mMediaCodec.getInputBuffers();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (Bitmap image : bitmaps) {
                image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // image is the bitmap
            }
            byte[] input = byteArrayOutputStream.toByteArray();

            int inputBufferIndex = mMediaCodec.dequeueInputBuffer(-1);
            if (inputBufferIndex >= 0) {
                ByteBuffer inputBuffer = mInputBuffers[inputBufferIndex];
                inputBuffer.clear();
                inputBuffer.put(input);
                mMediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, 0, 0);
            }

            String outputPath = new File(getCurrentFolderPath(),
                    "test.mp4").toString();
            MediaMuxer mediaMuxer = new MediaMuxer(outputPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            mediaMuxer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
