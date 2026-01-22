package com.example.javamachinery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//TODO: Annotate, annotate, annotate!

public class AppleStandMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.apple_stand);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appleStandMainScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageButton standEntry = findViewById(R.id.appleStandBtn);
        Button appleTradeBtn = findViewById(R.id.addAppleBtn);

        standEntry.setOnClickListener(view -> {
            Intent intent = new Intent(AppleStandMainScreen.this, AppleDirectoryScreen.class);
            startActivity(intent);
        });

        appleTradeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(AppleStandMainScreen.this, AppleTradeScreen.class);
            startActivity(intent);
        });

    }
}