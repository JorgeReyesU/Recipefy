package com.example.recipefy.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.recipefy.MainActivity;
import com.example.recipefy.R;
import com.example.recipefy.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class AccountFragment extends Fragment {

    private  Button btnLogout;
    private ImageView image;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userID;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);

        image = root.findViewById(R.id.home_images);
        btnLogout = root.findViewById(R.id.BtnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent newIntent = new Intent(getActivity(), MainActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(newIntent);
            }
        });
       //  FirebaseStorage store = FirebaseStorage.getInstance().getReference('gs://recipefy-1e6ba.appspot.com/Recipe/');

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();



        final TextView greetingTextView = (TextView) root.findViewById(R.id.greeting);
        final TextView fullNameTextView = (TextView) root.findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) root.findViewById(R.id.emailAddress);
        final TextView nickTextView = (TextView) root.findViewById(R.id.nick);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String fullName = userProfile.name;
                    String email = userProfile.email;
                    String nickname = userProfile.nickname;

                    greetingTextView.setText("Welcome, " + fullName + "!");
                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    nickTextView.setText(nickname);
                    Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/recipefy-1e6ba.appspot.com/o/Recipe%2F2d288b6e-e0ef-4b5e-9859-8591ee2b8780?alt=media&token=bf1fcd21-d03a-4675-9912-ee72b248d74b").into(image);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Toast.makeText(AccountFragment.this, "Something wrong happened!", Toast.LENGTH_LONG).show();

            }
        });

        return root;
    }
}
