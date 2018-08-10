package com.interview.ammaryali.pheramor_android_developer_position_assessment.views.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.interview.ammaryali.pheramor_android_developer_position_assessment.R;
import com.interview.ammaryali.pheramor_android_developer_position_assessment.databinding.FragmentDateDialogBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class DatePickerDialogFrag extends DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "extra_date";

    private DatePicker datePicker;

    public static DatePickerDialogFrag newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerDialogFrag fragment = new DatePickerDialogFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceDate) {

        FragmentDateDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_date_dialog, null, false);

        View view = binding.getRoot();

        Date date = (Date) (getArguments() != null ? getArguments().getSerializable(ARG_DATE) : null);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker = binding.dialogDatePicker;
        datePicker.init(year, month, day, null);


        return new AlertDialog.Builder(Objects.requireNonNull(getActivity())).setView(view)
                .setTitle(R.string.dob_dialog)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int year = datePicker.getYear();
                        int month = datePicker.getMonth();
                        int day = datePicker.getDayOfMonth();

                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    public void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
