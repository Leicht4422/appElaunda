package com.example.appelaunda.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.appelaunda.R;
import com.example.appelaunda.adapters.CategoryAdapter;
import com.example.appelaunda.adapters.NewProductAdapter;
import com.example.appelaunda.adapters.PopularProductAdapter;
import com.example.appelaunda.models.CategoryModel;
import com.example.appelaunda.models.NewProductsModel;
import com.example.appelaunda.models.PopularProductsModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView catRecyclerView, newProductRecyclerview, popularRecyclerView;
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    NewProductAdapter newProductAdapter;
    List<NewProductsModel> newProductsModelList;

    PopularProductAdapter popularProductAdapter;
    List<PopularProductsModel> popularProductsModelList;

    private StorageReference storageRef;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRecyclerView = root.findViewById(R.id.popular_rec);
        newProductRecyclerview = root.findViewById(R.id.new_product_rec);
        catRecyclerView = root.findViewById(R.id.rec_category);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        firestore = FirebaseFirestore.getInstance();

        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1, "Discounts!!", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2, "90% off Discounts!!", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3, "50% Off Discounts!!", ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

        catRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getActivity(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);

        fetchCategories();

        newProductRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProductAdapter = new NewProductAdapter(getContext(), newProductsModelList);
        newProductRecyclerview.setAdapter(newProductAdapter);

        fetchNewProducts();

        popularRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),2));
        popularProductsModelList = new ArrayList<>();
        popularProductAdapter = new PopularProductAdapter(getContext(), popularProductsModelList);
        popularRecyclerView.setAdapter(popularProductAdapter);

        fetchPopularProducts();

        return root;
    }

    private void fetchCategories() {
        firestore.collection("Category")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            CategoryModel category = new CategoryModel(
                                    document.getId(),
                                    document.getString("name")!= null? document.getString("name") : "",
                                    document.getLong("priority")!= null? document.getLong("priority").intValue() : 0
                            );
                            categoryModelList.add(category);
                        }
                        categoryAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HomeFragment", "Error getting categories.", e);
                        Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchNewProducts() {
        firestore.collection("NewProducts")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            NewProductsModel newProductsModel = new NewProductsModel(
                                    document.getId(),
                                    document.getString("name")!= null? document.getString("name") : "",
                                    document.getLong("priority")!= null? document.getLong("priority").intValue() : 0
                            );
                            newProductsModelList.add(newProductsModel);
                        }
                        newProductAdapter.notifyDataSetChanged();
                    }