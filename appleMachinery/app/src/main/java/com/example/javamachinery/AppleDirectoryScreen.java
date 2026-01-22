package com.example.javamachinery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppleDirectoryScreen extends AppCompatActivity {

    private AppleListAdapter adapter;
    private final List<Apple> appleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.apple_directory);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.appleDirectoryScreen), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button backBtn = findViewById(R.id.backBtn);
        RecyclerView appleDirectory = findViewById(R.id.appleRecyclerView);
        ProgressBar pb = findViewById(R.id.progressBar);

        pb.setVisibility(RecyclerView.VISIBLE);

        adapter = new AppleListAdapter(appleList);
        appleDirectory.setLayoutManager(new LinearLayoutManager(this));
        appleDirectory.setAdapter(adapter);

        fetchApples();

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        long deletedId = result.getData().getLongExtra("deleted_id", -1);
                        if (deletedId != -1) {
                            fetchApples(); // refresh after deletion
                        }
                    }
                }
        );

        adapter.setOnItemClickListener(apple -> {
            Intent intent = new Intent(AppleDirectoryScreen.this, IndividualAppleScreen.class);
            intent.putExtra("apple_id", apple.getId());
            intent.putExtra("apple_type", apple.getType());
            intent.putExtra("apple_color", apple.getColor());
            intent.putExtra("apple_weight", apple.getWeight());
            launcher.launch(intent);
        });

        backBtn.setOnClickListener(view -> {
            Intent home = new Intent(AppleDirectoryScreen.this, AppleStandMainScreen.class);
            startActivity(home);
        });

    }

    private void fetchApples() {
        AppleApi appleApi = ApiClient.getClient().create(AppleApi.class);
        ProgressBar pb = findViewById(R.id.progressBar);
        RecyclerView aD = findViewById(R.id.appleRecyclerView);
        appleApi.getAllApples().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<List<Apple>> call, @NonNull Response<List<Apple>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pb.setVisibility(RecyclerView.INVISIBLE);
                    adapter.updateApples(response.body());
                    aD.setVisibility(RecyclerView.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Apple>> call, @NonNull Throwable t) {
                Log.e("API Error", t.getMessage(), t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchApples(); // refresh list after edits
    }
}