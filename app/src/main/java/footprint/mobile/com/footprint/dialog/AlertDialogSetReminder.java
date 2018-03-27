package footprint.mobile.com.footprint.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.NumberPicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.base.BaseDialog;

/**
 * Created by Suman Pula on 3/8/2018.
 * © Business Intelli Solutions.
 * Email : admin@businessintelli.com
 */

public class AlertDialogSetReminder extends BaseDialog {
    private int hoursPos = 0, minutesPos = 0, datesPosition = 0;
    private OnTimeSetEventListener onTimeSetEventListener;
    public AlertDialogSetReminder(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getView() throws Exception {
        return R.layout.alert_dialog_set_reminder;
    }

    public void setReminder() {
        final View view = showDialog(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

        final NumberPicker numberDates = view.findViewById(R.id.numberDates);
        List<String> datesList = getDates();
        final String[] dates = new String[datesList.size()];
        numberDates.setMinValue(0);
        numberDates.setMaxValue(datesList.size()-1);
        numberDates.setDisplayedValues(datesList.toArray(dates));
        numberDates.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                datesPosition = i1;
            }
        });

        final NumberPicker hoursPicker = view.findViewById(R.id.numberPicHours);
        final String[] hours = getItems(23);
        hoursPicker.setMinValue(0);
        hoursPicker.setMaxValue(hours.length-1);
        hoursPicker.setDisplayedValues(hours);
        hoursPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                hoursPos = i1;
            }
        });

        final NumberPicker minutesPicker = view.findViewById(R.id.numberPicMinutes);
        final String[] minutes = getItems(59);
        minutesPicker.setMinValue(0);
        minutesPicker.setMaxValue(minutes.length-1);
        minutesPicker.setDisplayedValues(minutes);
        minutesPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                minutesPos = i1;
            }
        });

        final Button btnSet = view.findViewById(R.id.btnSet);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissDialog();
                try {
                    onTimeSetEventListener.setHours(hours[hoursPos]);
                    onTimeSetEventListener.setMinutes(minutes[minutesPos]);
                    onTimeSetEventListener.setTime(hours[hoursPos] + ":" + minutes[minutesPos]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    public interface OnTimeSetEventListener {
        void setHours(final String hours) throws Exception;

        void setMinutes(final String minutes) throws Exception;

        void setTime(final String time) throws Exception;
    }

    private List<String> getDates() {
        int max = 30;
        List<String> dates = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("EE dd MMM");
        Date date = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);

        Date afterOneYear = new Date();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(afterOneYear);
        cal2.add(Calendar.MONTH,1);
        afterOneYear = cal2.getTime();
        while(max > 0 ) {
           Date listDate = c1.getTime();
           dates.add(dateFormat.format(listDate));
           max--;
        }
        String fromDate = dateFormat.format(date);
        System.out.println("from date "+fromDate);
        String toDate = dateFormat.format(afterOneYear);
        System.out.println("to date "+toDate);
        return dates;
    }

    private String[] getItems(final int size) {
        int digits = 1;
        String[] items = new String[size];
        for (int i = 0; i < items.length; i++) {
            String item = String.valueOf(digits);
            if (isSingleDigit(digits)) {
                item = "0" + item;
            }
            items[i] = item;
            digits++;
        }
        return items;
    }

    private boolean isSingleDigit(final int digit) {
        if (digit < 10) {
            return true;
        }
        return false;
    }
}
