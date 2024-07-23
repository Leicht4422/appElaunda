package com.example.appelaunda.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appelaunda.R;
import com.example.appelaunda.models.NewProductsModel;

import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductsAdapter.ViewHolder>{

    private Context context;
    private List<NewProductsModel>list;
    import java.util.List;

    public NewProductAdapter(Context context, List<NewProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
    return new ViewHolder(LayoutInflater.from(parent.getContent()).inflate(R.layout.new_products,parent,false));
    }
    @Override
    public void onBindViewHolder(@NonNull ViewGroup holder, int position){
        Glide.with(content).load(list.get(position).geImg_url())into(holder.newImg);
        holder.newName.setText(list.get(position).getName());
        holder.newPrice.setText(String.valueOf(list.get(position).getPrice()));
    }

    @Override
    public class getItemCount(){
         return list.size();
    }
public class ViewHolder extends RecyclerView.ViewHolder{
    ImageView newImg;
    TextView newName,newPrice;
    public ViewHolder(@NonNull View itemView){
        super(itemView);
        newImg= itemView.findViewById(R.id.new_img);
        newName= itemView.findViewById(R.id.new_product_name);
        newPrice= itemView.findViewById(R.id.new_price);
    }
}
}