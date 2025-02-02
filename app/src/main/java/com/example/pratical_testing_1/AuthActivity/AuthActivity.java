package com.example.pratical_testing_1.AuthActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pratical_testing_1.MainActivity.MainActivity;
import com.example.pratical_testing_1.R;
import com.example.pratical_testing_1.dataBind.SharedPreference.SharedPrefHelper;

public class AuthActivity extends AppCompatActivity {
    SharedPrefHelper sharedPrefHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);


        sharedPrefHelper = new SharedPrefHelper(this);

        if (!sharedPrefHelper.getUserName().isEmpty() ) {
            Log.d("Auth Activity", "Verification Complete");
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close AuthActivity to prevent the user from going back here
        }


    }
}