package com.example.javamachinery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAppleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.edit_apple_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editAppleScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText updatedType = findViewById(R.id.newTypeEt);
        EditText updatedColor = findViewById(R.id.newColorEt);
        EditText updatedWeight = findViewById(R.id.newWeightEt);
        Button updateAppleBtn = findViewById(R.id.updateAppleBtn);
        Button backFromUpdateBtn = findViewById(R.id.backFromEditApple);
        AppleApi appleApi = ApiClient.getClient().create(AppleApi.class);
        Intent intent = getIntent();

        backFromUpdateBtn.setOnClickListener(view -> finish());

        updateAppleBtn.setOnClickListener(view -> {
            String finalColor;
            String finalType;
            int finalWeight;

            if(updatedType.getText().isEmpty()) {
                finalType = intent.getStringExtra("edit_type");
            }
            else {
                finalType = updatedType.getText().toString();
            }
            if(updatedColor.getText().isEmpty()) {
                finalColor = intent.getStringExtra("edit_color");
            }
            else {
                finalColor = updatedColor.getText().toString();
            }
            if(updatedWeight.getText().isEmpty()) {
                finalWeight = intent.getIntExtra("edit_weight", 0);
            }
            else {
                finalWeight = 0;
                try {
                    finalWeight = Integer.parseInt(updatedWeight.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(EditAppleScreen.this, "Please enter an integer!", Toast.LENGTH_LONG).show();
                }
            }

            Apple updatedApple = new Apple(finalColor, finalType, finalWeight);

            appleApi.updateApple(intent.getLongExtra("edit_id", 1), updatedApple).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Apple> call, @NonNull Response<Apple> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(
                                EditAppleScreen.this,
                                "Apple Updated! 🍎",
                                Toast.LENGTH_SHORT
                        ).show();
                        Intent home = new Intent(EditAppleScreen.this, AppleDirectoryScreen.class);
                        startActivity(home);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Apple> call, @NonNull Throwable t) {
                    Toast.makeText(
                            EditAppleScreen.this,
                            "Update failed",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        });


    }
}