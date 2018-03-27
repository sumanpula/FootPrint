package footprint.mobile.com.footprint.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import footprint.mobile.com.footprint.Constants;
import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.base.BaseFragment;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class FragmentViewFootPrints extends BaseFragment implements Constants {

    @Override
    public int getLayout() throws Exception {
        return R.layout.fragment_view_foot_prints;
    }

    @Override
    public void onViewCreated(View view) throws Exception {
        //Toast.makeText(getActivity(), "tab screen to view foot prints", Toast.LENGTH_SHORT).show();
        final File file = new File(getCurrentFolderPath());

        final List<Bitmap> bitmaps = new ArrayList<>();
        final List<String> fileNames = new ArrayList<>();
        final LinearLayout linearLayout = view.findViewById(R.id.llViewPrints);

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
//             BitmapFactory.Options options = new BitmapFactory.Options();
//             options.inJustDecodeBounds = true;
//             options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                final Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
                bitmaps.add(bitmap);
                fileNames.add(f.getName());
                //makeVideo(bitmap);
            }
        }

        final ImageView imageView = view.findViewById(R.id.myImage);
        final TextView tvFootName = view.findViewById(R.id.tvFootPrintName);
        tvFootName.setText(fileNames.get(0));
        imageView.setImageBitmap(bitmaps.get(0));
        final Handler handler = new Handler();
        new Runnable() {
            int counter = 0;

            @Override
            public void run() {
                if (counter == bitmaps.size()) {
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    return;
                }
                tvFootName.setText(fileNames.get(counter));
                imageView.setImageBitmap(bitmaps.get(counter));
                handler.postDelayed(this, SHOW_FOOT_PRINT_INTERVAL);
                counter++;
            }
        }.run();
        // imageView.setImageBitmap(bitmaps.get(i));

//        while (i > bitmaps.size()) {
//            imageView.setImageBitmap(bitmaps.get(i));
//            Thread.sleep(SHOW_FOOT_PRINT_INTERVAL);
//        }

//        try {
//        fragmentShowImage.setShowImageCallBack(new FragmentShowImage.ShowImageCallBack() {
//            @Override
//            public Bitmap setBitmap() throws Exception {
//                return bitmaps.get(0);
//            }
//
//            @Override
//            public String fileName() throws Exception {
//                return file.listFiles()[count-1].getName();
//            }
//        });
//        addOrReplaceChildFragment(fragmentShowImage,R.id.frame_child);
//    } catch (Exception e) {
//        e.printStackTrace();
//    }

//        while (count>bitmaps.size()){
//           final Handler handler = new Handler();
//           handler.postDelayed(new Runnable() {
//               @Override
//               public void run() {
//                   fragmentShowImage.setShowImageCallBack(new FragmentShowImage.ShowImageCallBack() {
//                       @Override
//                       public Bitmap setBitmap() throws Exception {
//                           return bitmaps.get(count-1);
//                       }
//                   });
//                   try {
//                       addOrReplaceChildFragment(fragmentShowImage,R.id.frame_child);
//                   } catch (Exception e) {
//                       e.printStackTrace();
//                   }
//               }
//           },SHOW_FOOT_PRINT_INTERVAL);
//            count++;
//        }
        final ImageView ivHome = view.findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addOrReplaceFragment(new FragmentMain(), R.id.frame_container);
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
        return R.string.menu_title_view_foot_print;
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

    @Override
    public void onMenuClick() throws Exception {
        backNavigation();
    }

    @Override
    public void onBackButtonClick() throws Exception {
        backNavigation();
    }

    private void backNavigation() {
        try {
            addOrReplaceFragment(new FragmentMain(), R.id.frame_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void makeVideo(final Bitmap bitmap) throws Exception {
        MediaCodec mMediaCodec = MediaCodec.createByCodecName("mp4");
        MediaFormat mMediaFormat = MediaFormat.createVideoFormat("video/mp4", 320, 240);
        mMediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
        mMediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
        mMediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
        mMediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);
        mMediaCodec.configure(mMediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        mMediaCodec.start();
        final ByteBuffer[] mInputBuffers = mMediaCodec.getInputBuffers();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream); // image is the bitmap
        byte[] input = byteArrayOutputStream.toByteArray();

        int inputBufferIndex = mMediaCodec.dequeueInputBuffer(-1);
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = mInputBuffers[inputBufferIndex];
            inputBuffer.clear();
            inputBuffer.put(input);
            mMediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, 0, 0);
        }

        final File videoFile = new File(getCurrentFolderPath(), "output.mp4");
        MediaMuxer mediaMuxer = new MediaMuxer(videoFile.getAbsolutePath(), MediaMuxer.OutputFormat.MUXER_OUTPUT_3GPP);
        mediaMuxer.start();
    }
}
