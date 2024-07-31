package com.example.appelaunda.activites;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.appelaunda.R;
import com.example.appelaunda.models.NewProductsModel;
import com.example.appelaunda.models.PopularProductsModel;
import com.example.appelaunda.models.ShowAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;

    RatingBar rating;
    TextView  name,price,description,quantity;
    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice =0;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    NewProductsModel newProductsModel= null;
    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null;
    private FirebaseFirestore firestore;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detailed);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");

        if(obj instanceof NewProductsModel){
            newProductsModel = (NewProductsModel) obj;
        }else if(obj instanceof PopularProductsModel){
            popularProductsModel = (PopularProductsModel) obj;
        }else if(obj instanceof ShowAllModel){
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        rating = findViewById(R.id.my_rating);
        name = findViewById(R.id.detailed_name);
        price = findViewById(R.id.detailed_price);
        description = findViewById(R.id.detailed_desc);

        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        if(newProductsModel != null){
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            price.setText(newProductsModel.getPrice());
            description.setText(newProductsModel.getDescription());
            try {
                float ratingValue = Float.parseFloat(newProductsModel.getRating());
                rating.setRating(ratingValue);
            } catch (NumberFormatException e) {
                // Handle cases where the rating string is not a valid number
                rating.setRating(0f); // Set a default value, or handle it differently as needed
                Log.w("ProductRating", "Invalid rating format: " + newProductsModel.getRating());
            }
            totalPrice = newProductsModel.getPrice() * totalQuantity;

        }

        if(popularProductsModel != null){
            Glide.with(getApplicationContext()).load(popularProductsModel.getImg_url()).into(detailedImg);
            name.setText(popularProductsModel.getName());
            price.setText(popularProductsModel.getPrice());
            description.setText(popularProductsModel.getDescription());
            try {
                float ratingValue = Float.parseFloat(popularProductsModel.getRating());
                rating.setRating(ratingValue);
            } catch (NumberFormatException e) {
                // Handle cases where the rating string is not a valid number
                rating.setRating(0f); // Set a default value, or handle it differently as needed
                Log.w("ProductRating", "Invalid rating format: " + popularProductsModel.getRating());
            }
            totalPrice = popularProductsModel.getPrice() * totalQuantity;

        }

        if(showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            price.setText(showAllModel.getPrice());
            description.setText(showAllModel.getDescription());
            try {
                float ratingValue = Float.parseFloat(showAllModel.getRating());
                rating.setRating(ratingValue);
            } catch (NumberFormatException e) {
                // Handle cases where the rating string is not a valid number
                rating.setRating(0f); // Set a default value, or handle it differently as needed
                Log.w("ProductRating", "Invalid rating format: " + showAllModel.getRating());
            }
            totalPrice = showAllModel.getPrice() * totalQuantity;

        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailedActivity.this,AddressActivity.class));
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
            }
        });

        addItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity < 10){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));

                    if(newProductsModel != null){
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if(popularProductsModel != null){
                        totalPrice = popularProductsModel.getPrice() * totalQuantity;
                    }
                    if(showAllModel != null){
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }

                }

            }
        });

        removeItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalQuantity > 1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));
                }

            }
        });

    }

    private void addToCart() {
        String saveCurrentTime,saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM,dd,yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);

        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(DetailedActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}