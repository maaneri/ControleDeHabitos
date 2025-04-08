package com.example.controledehabitos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HabitsFormActivity extends AppCompatActivity {
    private EditText nomeInput, descricaoInput;
    private CheckBox feitoHojeCheckBox;
    private Button salvarButton;
    private DatabaseHelper dbHelper;
    private int habitId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_form);

        nomeInput = findViewById(R.id.nomeInput);
        descricaoInput = findViewById(R.id.descricaoInput);
        feitoHojeCheckBox = findViewById(R.id.feitoHojeCheckBox);
        salvarButton = findViewById(R.id.salvarButton);
        dbHelper = new DatabaseHelper(this);

        // Verifica se veio um hábito para editar
        habitId = getIntent().getIntExtra("habitId", -1);
        if (habitId != -1) {
            Habits habit = dbHelper.getHabitById(habitId);
            if (habit != null) {
                nomeInput.setText(habit.getNome());
                descricaoInput.setText(habit.getDescricao());
                feitoHojeCheckBox.setChecked(habit.isFeitoHoje());
            }
        }

        // Salvar hábito
        salvarButton.setOnClickListener(v -> {
            String nome = nomeInput.getText().toString().trim();
            String descricao = descricaoInput.getText().toString().trim();
            boolean feitoHoje = feitoHojeCheckBox.isChecked();

            if (nome.isEmpty() || descricao.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (habitId == -1) {
                // Novo hábito
                dbHelper.insertHabit(new Habits(nome, descricao, feitoHoje));
            } else {
                // Atualizar hábito existente
                dbHelper.updateHabit(new Habits(habitId, nome, descricao, feitoHoje));
            }

            finish(); // Volta para a tela principal
        });
    }
}



