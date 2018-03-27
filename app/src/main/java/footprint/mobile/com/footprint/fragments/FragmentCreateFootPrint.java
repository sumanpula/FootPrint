package footprint.mobile.com.footprint.fragments;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import footprint.mobile.com.footprint.R;
import footprint.mobile.com.footprint.base.BaseFragment;
import footprint.mobile.com.footprint.dialog.AlertDialogReminderFreq;
import footprint.mobile.com.footprint.dialog.AlertDialogSetReminder;
import footprint.mobile.com.footprint.dto.Savings;

/**
 * Created by Suman Pula on 3/4/2018.
 * Copy Right @ Prabhakar Reddy Gudipati.
 * EMAIL : suman07.india@gmail.com.
 */
public class FragmentCreateFootPrint extends BaseFragment {
    @Override
    public int getLayout() throws Exception {
        return R.layout.fragment_create_foot_print;
    }

    @Override
    public void onViewCreated(View view) throws Exception {
        final EditText etFootPrintName = view.findViewById(R.id.etFootPrintName);

        final Button btnNever = view.findViewById(R.id.btnNever);
        btnNever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialogReminderFreq alertDialogReminderFreq = new AlertDialogReminderFreq(getActivity());
                alertDialogReminderFreq.showPicker(R.string.title_set_frequency);
                alertDialogReminderFreq.setOnFrequencySetListener(new AlertDialogReminderFreq.OnFrequencySetListener() {
                    @Override
                    public void onFrequencySet(String frequency) {
                        btnNever.setText(frequency);
                    }
                });
            }
        });

        final Button btnSetReminder = view.findViewById(R.id.btnSetReminder);
        btnSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialogSetReminder alertDialogSetReminder = new AlertDialogSetReminder(getActivity());
                //alertDialogSetReminder.setReminder();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int year = calendar.get(Calendar.YEAR);

               int month = calendar.get(Calendar.MONTH);
                int  day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog  =
                        new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                btnSetReminder.setText(year+"-"+month+"-"+dayOfMonth);
                            }
                        }, year, month, day);
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime()-1000);
                datePickerDialog.show();

            }
        });

        final Button btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go back to main screen
                backNavigation();
            }
        });

        final Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etFootPrintName.getText().toString().length() > 0) {
                    try {
                        if (isDirExist(etFootPrintName.getText().toString().trim())) {
                            Toast.makeText(getActivity(), "Foot name exist, cannot add", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Savings.getSavings().setFootPrints(etFootPrintName.getText().toString().trim());
                        addOrReplaceFragment(new FragmentViewFootPrintList(), R.id.frame_container);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.toast_enter_name), Toast.LENGTH_SHORT).show();
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
        return R.string.menu_title_create_foot_print;
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

    private boolean isDirExist(final String name) {
        checkPermissions();
        final File file = new File(FILE_PATH);
        file.mkdirs();
        for (File f : file.listFiles()) {
            if (f.isDirectory() && f.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
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
            addOrReplaceFragment(new FragmentViewFootPrintList(), R.id.frame_container);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
