package footprint.mobile.com.footprint.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

/**
 * Created by Suman Pula on 3/5/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public abstract class BaseDialog extends Dialog {
    public Dialog dialog;
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public View showDialog() {
        View view = null;
        try {
            if (getView() > 0) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(getView(), null);
                if (view.getParent() == null) {
                    dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(view);
                }
                // retrieve display dimensions
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public View showDialog(final int width, final int height) {
        View view = null;
        try {
            if (getView() > 0) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(getView(), null);
                if (view.getParent() == null) {
                    dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(view);
                }
                dialog.getWindow().setLayout(width, height);
                // retrieve display dimensions
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    public abstract int getView() throws Exception;
}
