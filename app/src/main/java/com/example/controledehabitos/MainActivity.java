package com.example.controledehabitos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private Button btnAdicionar;
    private ArrayAdapter<String> adapter;
    private ArrayList<Habits> habitsList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializando os elementos da interface
        listView = findViewById(R.id.listView);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        dbHelper = new DatabaseHelper(this);

        btnAdicionar = findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HabitsFormActivity.class);
            startActivity(intent);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, HabitsFormActivity.class);
            intent.putExtra("habitId", habitsList.get(position).getId());
            startActivity(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Habits habit = habitsList.get(position);
            habit.setFeitoHoje(!habit.isFeitoHoje());
            dbHelper.updateHabit(habit);
            loadList();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList();
    }

    private void loadList() {
        habitsList = dbHelper.getAllHabits();
        ArrayList<String> displayList = new ArrayList<>();
        for (Habits h : habitsList) {
            String status = h.isFeitoHoje() ? "✅" : "❌";
            displayList.add(status + " " + h.getNome() + " - " + h.getDescricao());
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);
    }
}
