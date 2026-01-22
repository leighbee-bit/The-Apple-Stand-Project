package com.example.javamachinery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class IndividualAppleScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.apple_item_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.individualAppleScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backFromIndiv = findViewById(R.id.backFromIndiv);
        Button deleteApple = findViewById(R.id.deleteAppleBtn);
        Button editAppleBtn = findViewById(R.id.editAppleBtn);
        TextView typeTv = findViewById(R.id.indivAppleTypeTv);
        TextView colorTv = findViewById(R.id.indivAppleColorTv);
        TextView weightTv = findViewById(R.id.indivAppleWeightTv);
        ImageView likenessIv = findViewById(R.id.indivAppleIv);
        AppleApi appleApi = ApiClient.getClient().create(AppleApi.class);
        Intent intent = getIntent();
        String type = intent.getStringExtra("apple_type");
        String color = intent.getStringExtra("apple_color");
        int weight = intent.getIntExtra("apple_weight", 0);


        typeTv.setText("ID: " + intent.getLongExtra("apple_id", 1) + " - " + type);
        colorTv.setText(color.substring(0,1).toUpperCase() + color.substring(1));
        weightTv.setText(weight + " grams");
        String aColor = intent.getStringExtra("apple_color").toLowerCase().strip();
        switch (aColor) {
            case "yellow":
            case "gold":
            case "orange":
                likenessIv.setImageResource(R.drawable.yellow_apple_transparent);
                break;
            case "green":
                likenessIv.setImageResource(R.drawable.green_apple_transparent);
                break;
            case "blue":
            case "purple":
                likenessIv.setImageResource(R.drawable.blue_apple_transparent);
                break;
            default:
                likenessIv.setImageResource(R.drawable.red_apple_transparent);
                break;
        }


        backFromIndiv.setOnClickListener(view -> finish());

        deleteApple.setOnClickListener(view -> {
            Long appleId = getIntent().getLongExtra("apple_id", -1);
            appleApi.deleteApple(appleId).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    if (response.isSuccessful()) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("deleted_id", appleId);
                        Toast.makeText(IndividualAppleScreen.this, "Apple Deleted! 🗑️", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    } else {
                            Toast.makeText(IndividualAppleScreen.this, "Delete failed", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    Toast.makeText(IndividualAppleScreen.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        });

        editAppleBtn.setOnClickListener(view -> {
            Intent editIntent = new Intent(IndividualAppleScreen.this, EditAppleScreen.class);

            editIntent.putExtra("edit_type", intent.getStringExtra("apple_type"));
            editIntent.putExtra("edit_color", intent.getStringExtra("apple_color"));
            editIntent.putExtra("edit_weight", intent.getIntExtra("apple_weight", 0));
            editIntent.putExtra("edit_id", intent.getLongExtra("apple_id", 1));

            startActivity(editIntent);
        });

    }
}