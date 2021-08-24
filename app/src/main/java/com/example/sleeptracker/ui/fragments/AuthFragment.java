package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sleeptracker.R;
import com.google.firebase.auth.FirebaseAuth;

public class AuthFragment extends Fragment {

    private static final String TAG = "AuthFragment";

    private EditText textEmail, textPassword;
    private Button btnSignIn, btnSignUp;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    public AuthFragment(){ super(R.layout.fragment_auth); }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
    }

    private void initViews(){
        textEmail = getView().findViewById(R.id.edit_text_email);
        textPassword = getView().findViewById(R.id.edit_text_password);
        btnSignIn = getView().findViewById(R.id.btn_sign_in);
        btnSignUp = getView().findViewById(R.id.btn_sign_up);

        btnSignIn.setOnClickListener(v -> signIn());
        btnSignUp.setOnClickListener(v -> signUp());
    }

    // All logic is in this view, since there is not much going on
    private void signUp(){
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        if(email.length() == 0 || password.length() == 0) return;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        moveToMainFragment();
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
    }

    private void signIn(){

    }

    private void moveToMainFragment(){
        Navigation.findNavController(getView()).navigate(R.id.action_authFragment_to_mainFragment);
    }

}
