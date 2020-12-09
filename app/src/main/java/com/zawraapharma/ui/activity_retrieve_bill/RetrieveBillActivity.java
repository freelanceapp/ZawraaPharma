package com.zawraapharma.ui.activity_retrieve_bill;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;

import com.zawraapharma.R;
import com.zawraapharma.databinding.ActivityPayBillBinding;
import com.zawraapharma.databinding.ActivityRetrieveBillBinding;
import com.zawraapharma.language.Language;

import io.paperdb.Paper;

public class RetrieveBillActivity extends AppCompatActivity {
    private ActivityRetrieveBillBinding binding;
    private String lang;

    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase,Paper.book().read("lang","ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_retrieve_bill);
        initView();
    }



    private void initView() {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        binding.setLang(lang);
        binding.imageBack.setOnClickListener(view -> finish());
    }
}