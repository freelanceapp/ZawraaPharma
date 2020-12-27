package com.zawraapharma.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.zawraapharma.R;
import com.zawraapharma.databinding.PrinterRowBinding;
import com.zawraapharma.databinding.ProductRowBinding;
import com.zawraapharma.models.CompanyProductModel;
import com.zawraapharma.models.PrinterModel;
import com.zawraapharma.ui.activity_printer_devices.PrinterDevicesActivity;
import com.zawraapharma.ui.activity_retrieve_bill.RetrieveBillActivity;

import java.util.List;

public class PrinterDevicesAdapter extends RecyclerView.Adapter<PrinterDevicesAdapter.MyHolder> {

    private List<PrinterModel> list;
    private Context context;
    private PrinterDevicesActivity activity;

    public PrinterDevicesAdapter(List<PrinterModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (PrinterDevicesActivity) context;



    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PrinterRowBinding bankRowBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.printer_row, parent, false);
        return new MyHolder(bankRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        PrinterModel model = list.get(position);
        holder.binding.setModel(model);
        holder.binding.btnConnect.setOnClickListener(view -> {
            PrinterModel model2 = list.get(holder.getAdapterPosition());
            activity.setItemData(model2);

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        private PrinterRowBinding binding;

        public MyHolder(PrinterRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;


        }


    }
}
