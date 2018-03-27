package footprint.mobile.com.footprint.utils;

import android.os.Build;

/**
 * Created by Suman Pula on 3/8/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class CommonUtils {
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
}
