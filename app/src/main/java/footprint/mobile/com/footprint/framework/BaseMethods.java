package footprint.mobile.com.footprint.framework;


import android.view.View;
import android.widget.Toolbar;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public interface BaseMethods {


    int getLayout() throws Exception;

    void onViewCreated(final View view) throws Exception;

    Toolbar toolbar() throws Exception;

    int menuBarTitle() throws Exception;

    int menuBarIcon() throws Exception;

    boolean hasToolBar() throws Exception;

    boolean hasMenu() throws Exception;
}
