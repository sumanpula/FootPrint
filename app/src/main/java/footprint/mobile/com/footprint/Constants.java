package footprint.mobile.com.footprint;

import android.os.Environment;

/**
 * Created by Suman Pula on 3/1/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public interface Constants {

   int SPLASH_TIME = 3*1000;// 3 seconds
   int IMAGE_CAPTURED_CODE = 1;//on image captured from camera
   int FILE_UPLOADED_CODE = 2;//on file uploaded using attach file
    String FILE_PATH = Environment.getExternalStorageDirectory()+ "/"+"Android/data/footprint.mobile.com.footprint";
    String PREFS_FOOT_PRINTS = "foot_prints";
    String DEFAULT_FOOT_PRINT = "default_foot_name";
    int SHOW_FOOT_PRINT_INTERVAL = 1*1000;
    int RIPPLE_EFFECT_INTERVAL = 400;
}
