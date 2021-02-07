package com.example.recipefy.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recipefy.R;
import com.example.recipefy.Recipe;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    ListView listV_recipes;
    private List<Recipe> listRecipe = new ArrayList<Recipe>();
    ArrayAdapter<Recipe> arrayAdapterRecipe;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView imagenes;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        listV_recipes = root.findViewById(R.id.listaRecipes);
        imagenes = root.findViewById(R.id.home_images);
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        listarDatos();




        return root;
    }

    private void listarDatos() {
        databaseReference.child("Recipe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listRecipe.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()){
                    Recipe r = objSnapshot.getValue(Recipe.class);
                    listRecipe.add(r);

                    arrayAdapterRecipe = new ArrayAdapter<Recipe>(getActivity(), android.R.layout.simple_list_item_1, listRecipe);
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/recipefy-1e6ba.appspot.com/o/Recipe%2F2d288b6e-e0ef-4b5e-9859-8591ee2b8780?alt=media&token=bf1fcd21-d03a-4675-9912-ee72b248d74b").into(imagenes);
                    listV_recipes.setAdapter(arrayAdapterRecipe);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}