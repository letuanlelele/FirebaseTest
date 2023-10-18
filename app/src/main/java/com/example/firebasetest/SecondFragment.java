package com.example.firebasetest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.firebasetest.databinding.FragmentSecondBinding;

/* added imports*/
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;

public class SecondFragment extends Fragment {

    /* calling Firebase */
    FirebaseDatabase root;
    DatabaseReference reference;

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* when view is created, fetch the latest user message to DB */

        /* first, call Firebase to the (key, value) we want to fetch from */
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("ourKey"); // our key in DB

        /* then, read from DB */
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // get the value from the snapshot of reference to "ourKey"
                // and turn it into String class
                String value = snapshot.getValue(String.class);
                // set it as the text inside textviewSecond
                if (binding != null) { // binding check just to make sure
                    binding.textviewSecond.setText(value);
                }
                // log it as Debugging for developers
                Log.d("SecondFragment", "set text as " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // display some error message to user
                if (binding != null) { // binding check just to make sure
                    binding.textviewSecond.setText(
                            "Error encountered in fetching ourKey from DB");
                }
                // log it as Warning for developers
                Log.w("SecondFragment", "valueEventListener:onCancelled()",
                        error.toException());
            }
        });


        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        root = null;

        /* destroying */
        reference = null;
        binding = null;
    }

}