package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AppointmentDetailActivity extends AppCompatActivity {
    private ImageButton btnBackToolbar;
    private TextView tvDetailTitle, tvDetailLocation, tvDetailTime, tvReminderStatus;
    private Switch switchDetailReminder;
    private Button btnBack, btnDelete;
    private DatabaseHelper dbHelper;
    private int appointmentId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);

        dbHelper = new DatabaseHelper(this);

        btnBackToolbar = findViewById(R.id.btnBackToolbar);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailLocation = findViewById(R.id.tvDetailLocation);
        tvDetailTime = findViewById(R.id.tvDetailTime);
        tvReminderStatus = findViewById(R.id.tvReminderStatus);
        switchDetailReminder = findViewById(R.id.switchDetailReminder);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        appointmentId = getIntent().getIntExtra("id", -1);
        if (appointmentId == -1) {
            Toast.makeText(this, "Lỗi ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadData();

        btnBackToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteAppointment(appointmentId);
                Toast.makeText(AppointmentDetailActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        switchDetailReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dbHelper.updateReminder(appointmentId, isChecked);
                if (isChecked) {
                    tvReminderStatus.setText("Đang bật");
                } else {
                    tvReminderStatus.setText("Đang tắt");
                }
            }
        });
    }

    private void loadData() {
        Appointment a = dbHelper.getAppointmentById(appointmentId);
        if (a != null) {
            tvDetailTitle.setText(a.getTitle());
            tvDetailLocation.setText(a.getLocation());
            tvDetailTime.setText(a.getDate() + " · " + a.getTime());
            
            switchDetailReminder.setOnCheckedChangeListener(null); // prevent trigger
            if (a.getReminder() == 1) {
                switchDetailReminder.setChecked(true);
                tvReminderStatus.setText("Đang bật");
            } else {
                switchDetailReminder.setChecked(false);
                tvReminderStatus.setText("Đang tắt");
            }
            switchDetailReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    dbHelper.updateReminder(appointmentId, isChecked);
                    if (isChecked) {
                        tvReminderStatus.setText("Đang bật");
                    } else {
                        tvReminderStatus.setText("Đang tắt");
                    }
                }
            });
        }
    }
}