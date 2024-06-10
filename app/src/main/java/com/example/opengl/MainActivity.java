package com.example.opengl;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText numOfCar;

    private Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view từ XML
        numOfCar = findViewById(R.id.numOfCar);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(numOfCar.getText().toString());

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("number", number);
                startActivity(intent);
            }
        });
    }
}
