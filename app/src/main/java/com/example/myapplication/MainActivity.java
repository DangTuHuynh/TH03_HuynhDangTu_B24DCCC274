package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvTotal;
    private TextView tvReminder;
    private ListView listView;
    private Button btnAdd;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        tvTotal = findViewById(R.id.tvTotal);
        tvReminder = findViewById(R.id.tvReminder);
        listView = findViewById(R.id.listView);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAppointmentActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Appointment a = (Appointment) listView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, AppointmentDetailActivity.class);
                intent.putExtra("id", a.getId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        int total = dbHelper.countAll();
        int reminders = dbHelper.countReminders();
        
        tvTotal.setText(String.valueOf(total));
        tvReminder.setText(String.valueOf(reminders));

        List<Appointment> list = dbHelper.getAllAppointments();
        AppointmentAdapter adapter = new AppointmentAdapter(this, list);
        listView.setAdapter(adapter);
    }
}