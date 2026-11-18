package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    private Context context;
    private List<Appointment> list;

    public AppointmentAdapter(Context context, List<Appointment> list) {
        super(context, R.layout.item_appointment, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false);
        }

        Appointment a = list.get(position);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        TextView tvDateTime = convertView.findViewById(R.id.tvDateTime);
        TextView tvReminderBadge = convertView.findViewById(R.id.tvReminderBadge);

        tvTitle.setText(a.getTitle());
        tvLocation.setText("Địa điểm: " + a.getLocation());
        
        // Date format transformation if needed. SQLite has yyyy-MM-dd. We show something nicer or just as is.
        // Assuming we show yyyy-MM-dd directly based on mock. Wait, spec says [Ngày] · [Giờ]
        tvDateTime.setText(a.getDate() + " · " + a.getTime());

        if (a.getReminder() == 1) {
            tvReminderBadge.setText("Có nhắc");
            tvReminderBadge.setBackgroundResource(R.drawable.badge_orange);
            tvReminderBadge.setTextColor(Color.parseColor("#E65100"));
        } else {
            tvReminderBadge.setText("Không nhắc");
            tvReminderBadge.setBackgroundResource(R.drawable.badge_gray);
            tvReminderBadge.setTextColor(Color.parseColor("#424242"));
        }

        return convertView;
    }
}