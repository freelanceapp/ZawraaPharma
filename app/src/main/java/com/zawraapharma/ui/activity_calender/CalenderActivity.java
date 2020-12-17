package com.zawraapharma.ui.activity_calender;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.applandeo.materialcalendarview.EventDay;
import com.zawraapharma.R;
import com.zawraapharma.adapters.PharmacyAdapter;
import com.zawraapharma.databinding.ActivityCalenderBinding;
import com.zawraapharma.language.Language;
import com.zawraapharma.models.AppointmentModel;
import com.zawraapharma.models.PharmacyModel;
import com.zawraapharma.mvp.activity_calender_mvp.ActivityCalenderPresenter;
import com.zawraapharma.mvp.activity_calender_mvp.CalenderActivityView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class CalenderActivity extends AppCompatActivity implements CalenderActivityView {
    private ActivityCalenderBinding binding;
    private ActivityCalenderPresenter presenter;
    private List<PharmacyModel> pharmacyModelList;
    private PharmacyAdapter adapter;
    private String lang;
    private String todayDate;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calender);
        initView();
    }



    private void initView() {
        Paper.init(this);
        lang= Paper.book().read("lang","ar");
        binding.setLang(lang);
        pharmacyModelList = new ArrayList<>();
        presenter = new ActivityCalenderPresenter(this, this);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PharmacyAdapter(pharmacyModelList,this);
        binding.recView.setAdapter(adapter);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.progBar2.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        presenter.getAppointment();

        Calendar firstSelectedDate = binding.calendarView.getFirstSelectedDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        todayDate = simpleDateFormat.format(new Date(firstSelectedDate.getTimeInMillis()));
        presenter.searchAppointment(todayDate);

        binding.calendarView.setOnDayClickListener(eventDay -> {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String selectedDate = simpleDateFormat2.format(new Date(eventDay.getCalendar().getTimeInMillis()));
            presenter.searchAppointment(selectedDate);

        });

        binding.llBack.setOnClickListener(view -> finish());
    }




    @Override
    public void onBackPressed() {
        presenter.backPress();
    }


    @Override
    public void onSuccess(List<AppointmentModel> data) {

        if (data.size()>0){
            List<EventDay> eventDayList = new ArrayList<>();
            for (AppointmentModel model:data){
                String pharmacyDate = model.getFired_at();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
                try {
                    Date parse = simpleDateFormat.parse(pharmacyDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.setTime(parse);
                    EventDay eventDay = new EventDay(calendar,R.drawable.event_bg);
                    eventDayList.add(eventDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            binding.calendarView.setEvents(eventDayList);
        }
    }

    @Override
    public void onPharmacySuccess(List<PharmacyModel> data) {
        if (data.size()>0){
            pharmacyModelList.addAll(data);
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressShow() {
        pharmacyModelList.clear();
        adapter.notifyDataSetChanged();
        binding.progBar.setVisibility(View.VISIBLE);
        binding.tvNoData.setVisibility(View.GONE);
    }

    @Override
    public void onProgressHide() {
        binding.progBar.setVisibility(View.GONE);

    }

    @Override
    public void onMainProgressShow() {
        binding.flLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMainProgressHide() {
        binding.flLoad.setVisibility(View.GONE);

    }

    @Override
    public void onFinished() {
        finish();
    }


}