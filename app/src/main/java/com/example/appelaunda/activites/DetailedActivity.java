package com.example.appelaunda.activites;

import android.icu.util.Calendar;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;

    RatingBar rating;
    TextView  name,price,description,quantity;
    Button addToCart,buyNow;
    ImageView addItems,removeItems;
    Toolbar toolbar;
    int totalQuantity = 1;
    int totalPrice =0;
    NewProductsModel newProductsModel= null;
    PopularProductsModel popularProductsModel = null;
    ShowAllModel showAllModel = null;
    FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    private ActionBar setSuppportActionBar(Toolbar toolbar) {
    }
}