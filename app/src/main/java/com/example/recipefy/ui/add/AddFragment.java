package com.example.recipefy.ui.add;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipefy.R;
import com.example.recipefy.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddFragment extends Fragment {

    Button btnAdd, btnChooseImage;
    EditText Name, Description;
    ImageView mImageView;
    TextView mTextViewShowUploads;
    private Uri mImageUri;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage mStorageRef;

    private static final int RESULT_OK = -1;

    private AddViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mStorageRef = FirebaseStorage.getInstance();


        Name = root.findViewById(R.id.editTextNameRecipe);
        Description = root.findViewById(R.id.editTextDescriptionRecipe);

        mImageView = root.findViewById(R.id.home_images);
        btnChooseImage = root.findViewById(R.id.btnUploadimage);
        btnAdd = root.findViewById(R.id.btnAddRecipe);

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String description = Description.getText().toString();

                if (name.isEmpty()){
                    Name.setError("Name is required");
                    Name.requestFocus();
                    return;
                } else if(description.isEmpty()){
                    Description.setError("Description is required");
                    Description.requestFocus();
                    return;
                } else {

                    Recipe recipe = new Recipe();
                    recipe.setName(name);
                    recipe.setDescription(description);
                    recipe.setKey(UUID.randomUUID().toString());
                    recipe.setImageURL("Recipe/" + recipe.getKey());
                    //recipe.setImageURL(mImageUri.toString());
                    //databaseReference.child("Recipe").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                    StorageReference fileReference = mStorageRef.getReference().child("Recipe/" + recipe.getKey());
                    fileReference.putFile(mImageUri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                    databaseReference.child("Recipe").child(recipe.getKey()).setValue(recipe);
                                    limpiar();
                                    Toast.makeText(getActivity(), "Recipe added successfully", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Recipe added failed", Toast.LENGTH_LONG).show();
                                }
                            });
                    //databaseReference.child("Recipe").child(recipe.getKey()).setValue(recipe);

                }

            }
        });


        return root;
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == AddFragment.RESULT_OK
                && data != null && data.getData() != null){
            mImageUri = data.getData();
            mImageView.setImageURI(mImageUri);


        }
    }





    private void limpiar(){
        Name.setText("");
        Description.setText("");
    }
}