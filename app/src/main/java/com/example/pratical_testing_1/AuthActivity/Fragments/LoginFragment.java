package com.example.pratical_testing_1.AuthActivity.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pratical_testing_1.MainActivity.MainActivity;
import com.example.pratical_testing_1.R;
import com.example.pratical_testing_1.dataBind.Model.User;
import com.example.pratical_testing_1.dataBind.RoomDB.AppDataBase;
import com.example.pratical_testing_1.dataBind.RoomDB.UserDao;
import com.example.pratical_testing_1.dataBind.SharedPreference.SharedPrefHelper;
import com.google.android.material.progressindicator.LinearProgressIndicator;


public class LoginFragment extends Fragment {

    private EditText username, password;
    private Button loginButton;
    private TextView signupTxtView;
    private ImageView passwordToggle;
    private boolean isPasswordVisible = false;
    AppCompatImageView truckicon;
    LinearProgressIndicator linearProgressIndicator;
    SharedPrefHelper sharedPrefHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        loginButton = view.findViewById(R.id.loginButton);
        signupTxtView = view.findViewById(R.id.signup);
        passwordToggle = view.findViewById(R.id.passwordToggle);
        truckicon = view.findViewById(R.id.truckicon);
        linearProgressIndicator = view.findViewById(R.id.linearprogressindicator);

        sharedPrefHelper = new SharedPrefHelper(getContext());


        NavController navController = NavHostFragment.findNavController(this);
        signupTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.to_signupFragment);
            }
        });


        passwordToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordToggle.setImageResource(R.drawable.baseline_remove_red_eye_24);
                } else {
                    // Show password
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordToggle.setImageResource(R.drawable.baseline_remove_red_eye_24);
                }
                // Move cursor to the end of the text
                password.setSelection(password.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });


        loginButton.setOnClickListener(v -> {
            String usernameText = username.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (TextUtils.isEmpty(usernameText)) {
                username.setError("Username is required");
                return;
            }

            if (TextUtils.isEmpty(passwordText)) {
                password.setError("Password is required");
                return;
            }

            loginButton.setEnabled(false);
            loginButton.setAlpha(0.5f);
            linearProgressIndicator.setVisibility(View.VISIBLE);


            AppDataBase db = AppDataBase.getInstance(getContext());
            UserDao userDao = db.userDao();

            // Retrieve user
            User user = userDao.login(usernameText, passwordText);
            if (user != null) {

                sharedPrefHelper.saveUserProfile(
                        user.getUsername(),
                        user.getEmail(),
                        String.valueOf(user.getId()),
                        ""
                );


                Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
                loginButton.setEnabled(true);
                loginButton.setAlpha(1.0f);
                linearProgressIndicator.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(getContext(), "Login failed: ", Toast.LENGTH_SHORT).show();
                loginButton.setEnabled(true);
                loginButton.setAlpha(1.0f);
                linearProgressIndicator.setVisibility(View.INVISIBLE);
            }

        });

        return view;
    }
}