package footprint.mobile.com.footprint.framework;

import android.os.Bundle;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public interface ActivityBaseMethods {

    public abstract int getLayout() throws Exception;

    public abstract void onActivityReady(final Bundle bundle) throws Exception;

}
