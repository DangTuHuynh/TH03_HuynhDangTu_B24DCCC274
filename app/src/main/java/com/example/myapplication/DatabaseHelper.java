package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "appointments.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableUsers = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "password TEXT NOT NULL)";
        db.execSQL(createTableUsers);

        String createTableAppointments = "CREATE TABLE appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "location TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "reminder INTEGER NOT NULL)";
        db.execSQL(createTableAppointments);

        // Insert default user
        ContentValues userValues = new ContentValues();
        userValues.put("username", "admin");
        userValues.put("password", "123456");
        db.insert("users", null, userValues);

        // Insert sample appointments
        insertSampleAppointment(db, "Họp nhóm đồ án", "Phòng B203", "2026-10-25", "09:00", 1);
        insertSampleAppointment(db, "Khám răng", "Nha khoa Minh Tâm", "2026-10-26", "14:30", 1);
        insertSampleAppointment(db, "Đi cà phê với bạn", "Highland Coffee", "2026-10-27", "19:00", 0);
    }

    private void insertSampleAppointment(SQLiteDatabase db, String title, String location, String date, String time, int reminder) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("location", location);
        values.put("date", date);
        values.put("time", time);
        values.put("reminder", reminder);
        db.insert("appointments", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS appointments");
        onCreate(db);
    }

    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        db.insert("users", null, values);
    }

    public long insertAppointment(Appointment a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", a.getTitle());
        values.put("location", a.getLocation());
        values.put("date", a.getDate());
        values.put("time", a.getTime());
        values.put("reminder", a.getReminder());
        return db.insert("appointments", null, values);
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM appointments ORDER BY date ASC, time ASC", null);
            if (cursor.moveToFirst()) {
                do {
                    Appointment a = new Appointment(
                            cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("title")),
                            cursor.getString(cursor.getColumnIndexOrThrow("location")),
                            cursor.getString(cursor.getColumnIndexOrThrow("date")),
                            cursor.getString(cursor.getColumnIndexOrThrow("time")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("reminder"))
                    );
                    list.add(a);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return list;
    }

    public Appointment getAppointmentById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM appointments WHERE id = ?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                return new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        cursor.getString(cursor.getColumnIndexOrThrow("location")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("time")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("reminder"))
                );
            }
        } finally {
            if (cursor != null) cursor.close();
        }
        return null;
    }

    public void updateReminder(int id, boolean on) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("reminder", on ? 1 : 0);
        db.update("appointments", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void deleteAppointment(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("appointments", "id = ?", new String[]{String.valueOf(id)});
    }

    public int countAll() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM appointments", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            return 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public int countReminders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM appointments WHERE reminder = 1", null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
            return 0;
        } finally {
            if (cursor != null) cursor.close();
        }
    }
}