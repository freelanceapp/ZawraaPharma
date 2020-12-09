package com.zawraapharma.mvp.activity_date_mvp;

import android.app.FragmentManager;
import android.content.Context;

import androidx.core.content.ContextCompat;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.zawraapharma.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityDatePresenter implements DatePickerDialog.OnDateSetListener {
    private ActivityDateView view;
    private Context context;
    private DatePickerDialog datePickerDialog;

    public ActivityDatePresenter(ActivityDateView view, Context context) {
        this.view = view;
        this.context = context;
        createDateDialog();
    }

    private void createDateDialog() {
        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setOkText(context.getString(R.string.select));
        datePickerDialog.setCancelText(context.getString(R.string.cancel));
        datePickerDialog.setAccentColor(ContextCompat.getColor(context, R.color.colorPrimary));
        datePickerDialog.setOkColor(ContextCompat.getColor(context, R.color.colorPrimary));
        datePickerDialog.setCancelColor(ContextCompat.getColor(context, R.color.gray4));
        datePickerDialog.setLocale(Locale.ENGLISH);
        datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_1);

    }

    public void showDateDialog(FragmentManager fragmentManager){
        try {
            datePickerDialog.show(fragmentManager,"");
        }catch (Exception e){}
    }
    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, monthOfYear);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = dateFormat.format(new Date(calendar.getTimeInMillis()));
        ActivityDatePresenter.this.view.onDateSelected(date);
    }
}
