package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.Locale;

public class AddAppointmentActivity extends AppCompatActivity {
    private ImageButton btnBack;
    private EditText etTitle, etLocation;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Switch switchReminder;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        etTitle = findViewById(R.id.etTitle);
        etLocation = findViewById(R.id.etLocation);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        switchReminder = findViewById(R.id.switchReminder);
        btnSave = findViewById(R.id.btnSave);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAppointment();
            }
        });
    }

    private void saveAppointment() {
        String title = etTitle.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Không được để trống");
            return;
        }
        if (location.isEmpty()) {
            etLocation.setError("Không được để trống");
            return;
        }

        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();

        int hour, minute;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute, 0);

        if (cal.getTimeInMillis() <= System.currentTimeMillis()) {
            Toast.makeText(this, "Thời gian phải sau hiện tại", Toast.LENGTH_SHORT).show();
            return;
        }

        String dateStr = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, day);
        String timeStr = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);
        int reminder = switchReminder.isChecked() ? 1 : 0;

        Appointment a = new Appointment(title, location, dateStr, timeStr, reminder);
        dbHelper.insertAppointment(a);
        
        Toast.makeText(this, "Đã lưu", Toast.LENGTH_SHORT).show();
        finish();
    }
}