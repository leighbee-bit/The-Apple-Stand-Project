package com.example.javamachinery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppleTradeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.apple_trade);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appleTradeScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText appleNameEntry = findViewById(R.id.appleNameEt);
        EditText appleColorEntry = findViewById(R.id.appleColorEt);
        EditText appleWeightEntry = findViewById(R.id.appleWeightEt);
        Button tradeAppleBtn = findViewById(R.id.submitAppleBtn);
        Button backToMain = findViewById(R.id.backFromTradeBtn);

        Snackbar errorSnack = Snackbar.make(findViewById(android.R.id.content), "Please enter all credentials.", Snackbar.LENGTH_LONG);
        View viewer = errorSnack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewer.getLayoutParams();
        params.gravity = Gravity.CENTER;
        viewer.setLayoutParams(params);

        Snackbar successSnack = Snackbar.make(findViewById(android.R.id.content), "Apple submitted!", Snackbar.LENGTH_LONG);
        View viewer2 = successSnack.getView();
        FrameLayout.LayoutParams params2 = (FrameLayout.LayoutParams) viewer2.getLayoutParams();
        params2.gravity = Gravity.CENTER;
        viewer2.setLayoutParams(params2);

        Snackbar failedSnack = Snackbar.make(findViewById(android.R.id.content), "There was an error!", Snackbar.LENGTH_LONG);
        View viewer3 = failedSnack.getView();
        FrameLayout.LayoutParams params3 = (FrameLayout.LayoutParams) viewer3.getLayoutParams();
        params3.gravity = Gravity.CENTER;
        viewer3.setLayoutParams(params3);

        AppleApi appleApi = ApiClient.getClient().create(AppleApi.class);

        backToMain.setOnClickListener(view -> finish());

        tradeAppleBtn.setOnClickListener(view -> {
            if(appleNameEntry.getText().length() > 0) {
                if(appleColorEntry.getText().length() > 0) {
                    if(appleWeightEntry.getText().length() > 0) {
                        int weight;
                        try {
                            weight = Integer.parseInt(appleWeightEntry.getText().toString());
                            Apple newApple = new Apple(appleColorEntry.getText().toString(), appleNameEntry.getText().toString(), weight);

                            appleApi.addApple(newApple).enqueue(new Callback<>() {
                                @Override
                                public void onResponse(@NonNull Call<Apple> call, @NonNull Response<Apple> response) {
                                    if(response.isSuccessful() && response.body() != null) {
                                        Toast.makeText(AppleTradeScreen.this, "New " + newApple.getType() + " added to the market! 🧺", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(AppleTradeScreen.this, AppleStandMainScreen.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        Log.e("API ERROR", "Server returned error: " + response.code());
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Apple> call, @NonNull Throwable t) {
                                    Log.e("API ERROR", t.getMessage(), t);
                                }
                            });
                        } catch (NumberFormatException e) {
                            failedSnack.show();
                        }
                    }
                    else {
                        errorSnack.show();
                    }
                }
                else {
                    errorSnack.show();
                }
            }
            else {
                errorSnack.show();
            }
        });

    }
}