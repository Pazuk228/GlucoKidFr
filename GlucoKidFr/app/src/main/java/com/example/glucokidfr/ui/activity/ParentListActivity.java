package com.example.glucokidfr.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glucokidfr.R;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.usecase.GetAllUsersUseCase;
import com.example.glucokidfr.ui.adapters.ParentAdapter;

import java.util.List;

public class ParentListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ParentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ParentAdapter();
        recyclerView.setAdapter(adapter);

        UserRepositoryImpl repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
        GetAllUsersUseCase useCase = new GetAllUsersUseCase(repository);

        useCase.execute(1L, status -> {
            if (status.getErrors() != null) {
                Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setParents(status.getValue());
            }
        });
    }
}