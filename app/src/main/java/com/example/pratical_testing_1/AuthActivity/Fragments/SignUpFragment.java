package com.example.pratical_testing_1.AuthActivity.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pratical_testing_1.MainActivity.MainActivity;
import com.example.pratical_testing_1.R;
import com.example.pratical_testing_1.dataBind.Model.User;
import com.example.pratical_testing_1.dataBind.RoomDB.AppDataBase;
import com.example.pratical_testing_1.dataBind.RoomDB.UserDao;
import com.example.pratical_testing_1.dataBind.SharedPreference.SharedPrefHelper;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Locale;

public class SignUpFragment extends Fragment {

    private TextView signinTxtView;

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton;
    LinearProgressIndicator linearProgressIndicator;
    SharedPrefHelper sharedPrefHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        signinTxtView = view.findViewById(R.id.signin);
        NavController navController = NavHostFragment.findNavController(this);
        signinTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.to_loginFragment);
            }
        });

        sharedPrefHelper = new SharedPrefHelper(getContext());


        nameEditText = view.findViewById(R.id.name);
        emailEditText = view.findViewById(R.id.email);
        passwordEditText = view.findViewById(R.id.password);
        confirmPasswordEditText = view.findViewById(R.id.confirmpassword);
        signUpButton = view.findViewById(R.id.singupButton);
        linearProgressIndicator = view.findViewById(R.id.linearprogressindicator);


        signUpButton.setOnClickListener(v -> signUp());


        return view;
    }


    private void signUp() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            nameEditText.setError("Name is required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return;
        }
        signUpButton.setEnabled(false);
        signUpButton.setAlpha(0.5f);
        linearProgressIndicator.setVisibility(View.VISIBLE);


        AppDataBase db = AppDataBase.getInstance(getContext());
        UserDao userDao = db.userDao();

        // Insert a user
        User newUser = new User(
                name,
                password,
                email,
                ""
        );

        userDao.insertUser(newUser);
        User checking = userDao.login(name, password);
        if (checking != null){
            Toast.makeText(getContext(), "Sign Up Successful", Toast.LENGTH_SHORT).show();

            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.to_loginFragment);

            signUpButton.setEnabled(true);
            signUpButton.setAlpha(1.0f);
            linearProgressIndicator.setVisibility(View.INVISIBLE);

        } else {
            Toast.makeText(getContext(), "Failed to save user info", Toast.LENGTH_SHORT).show();
            signUpButton.setEnabled(true);
            signUpButton.setAlpha(1.0f);
            linearProgressIndicator.setVisibility(View.INVISIBLE);

        }

    }



}