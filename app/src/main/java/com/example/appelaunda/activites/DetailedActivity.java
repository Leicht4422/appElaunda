package com.example.appelaunda.activites;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;

    RatingBar rating;
    TextView  name,price,description,quantity;
    private androidx.appcompat.widget.Toolbar mToolBar;
    int totalQuantity = 1;
    int totalPrice =0;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    NewProductsModel newProductsModel= null;
    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null;
    private FirebaseFirestore firestore;
    FirebaseAuth auth;
    private int productId;



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

        mToolBar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get the product ID from the intent
        productId = getIntent().getIntExtra("productId", -1);
        if (productId == -1) {
            Log.e("DetailedActivity", "Invalid product ID received");
            finish(); // Close the activity if no valid ID is found
            return;
        }

        // Fetch product details based on the ID
        fetchProductDetails(productId);


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
                Intent intent = new Intent(DetailedActivity.this,AddressActivity.class);
                if (newProductsModel != null){
                    intent.putExtra("item",newProductsModel);
                }
                if (showAllModel != null){
                    intent.putExtra("item", (Parcelable) showAllModel);
                }
                if (popularProductsModel != null){
                    intent.putExtra("item",popularProductsModel);
                }


                startActivity(intent);

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
    private void fetchProductDetails(int productId) {
        // Assuming you have a "Products" collection in Firestore
        firestore.collection("Products").document(String.valueOf(productId))
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Extract product details from the document
                                String name = document.getString("name");
                                String description = document.getString("description");
                                int price = document.getLong("price").intValue(); // Assuming price is stored as a number
                                String imageUrl = document.getString("img_url");
                                String rating = document.getString("rating");

                                // Update your UI elements with the fetched data
                                Glide.with(getApplicationContext()).load(imageUrl).into(detailedImg);
                                DetailedActivity.this.name.setText(name);
                                DetailedActivity.this.price.setText(String.format("$%.2f", price));
                                DetailedActivity.this.description.setText(description);
                                try {
                                    float ratingValue = Float.parseFloat(rating);
                                    DetailedActivity.this.rating.setRating(ratingValue);
                                } catch (NumberFormatException e) {
                                    // Handle cases where the rating string is not a valid number
                                    DetailedActivity.this.rating.setRating(0f);
                                    Log.w("ProductRating", "Invalid rating format: " + rating);
                                }
                                totalPrice = price * totalQuantity;
                            } else {
                                Log.d("DetailedActivity", "No such document");
                            }
                        } else {
                            Log.d("DetailedActivity", "get failed with ", task.getException());
                        }
                    }
                });
    }

    private void setSupportActionBar(Toolbar mToolBar) {
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