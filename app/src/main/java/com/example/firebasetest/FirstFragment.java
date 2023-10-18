package com.example.firebasetest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.firebasetest.databinding.FragmentFirstBinding;

/* added imports*/
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class FirstFragment extends Fragment {

    /* calling Firebase */
    FirebaseDatabase root;
    DatabaseReference reference;

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* when next button is clicked, save the user message to DB */

                /* first, call Firebase to the (key, value) we want to edit */
                root = FirebaseDatabase.getInstance();
                reference = root.getReference("ourKey"); // our key in DB

                /* then, save the value */
                reference.setValue(binding.edittextFirst.getText().toString());


                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

        /* destroying */
        root = null;
        reference = null;
    }

}