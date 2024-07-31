package com.example.appelaunda.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appelaunda.R;
import com.example.appelaunda.models.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    SelectedAddress selectedAddress;

    List<AddressModel> addressModelList;

    private RadioButton selectedRadioBtn;

    public AddressAdapter(Context context, SelectedAddress selectedAddress, List<AddressModel> addressModelList) {
        this.context = context;
        this.selectedAddress = selectedAddress;
        this.addressModelList = addressModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(AddressModel address:addressModelList){
                    address.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);

                if(selectedRadioBtn!=null){
                    selectedRadioBtn.setPressed(false);
                }
                selectedRadioBtn = (RadioButton) view;
                selectedRadioBtn.setPressed(true);
                selectedAddress.setAddress(addressModelList.get(position).getUserAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }

    public interface SelectedAddress{
        void setAddress(String address);
    }
}
