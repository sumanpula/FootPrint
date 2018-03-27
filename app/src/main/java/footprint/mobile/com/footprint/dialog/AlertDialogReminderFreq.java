package footprint.mobile.com.footprint.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.base.BaseDialog;

/**
 * Created by Suman Pula on 3/8/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class AlertDialogReminderFreq extends BaseDialog {
    private OnFrequencySetListener onFrequencySetListener;
    private String frequency;

    public AlertDialogReminderFreq(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getView() throws Exception {
        return R.layout.alert_dialog_reminder_freq;
    }

    public void showPicker(final int titleId) {
        final View view = showDialog(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        final TextView title = view.findViewById(R.id.title);
        title.setText(getContext().getString(titleId));

        final NumberPicker numberPicker = view.findViewById(R.id.pickerFrequency);
        final String[] frequencies =  getFrequencies();
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(frequencies.length-1);
        numberPicker.setDisplayedValues(frequencies);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                frequency = frequencies[i1];
            }
        });

        final Button btnSet = view.findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
                onFrequencySetListener.onFrequencySet(frequency);
            }
        });

        final Button btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
            }
        });
    }

    private String[] getFrequencies() {
        return new String[]{"Never","Daily","Weekly","Monthly"};
    }

    public interface OnFrequencySetListener {
        void onFrequencySet(final String frequency);
    }

    public void setOnFrequencySetListener(OnFrequencySetListener onFrequencySetListener) {
        this.onFrequencySetListener = onFrequencySetListener;
    }
}
