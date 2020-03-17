package com.example.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

public class CalendarActivity extends AppCompatActivity {
    //Variables
    CalendarView calendarView;
    TextView myDate;
    Button bChckRec;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Declarations
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);
        bChckRec = (Button) findViewById(R.id.buttonAddFood);

        Calendar today = Calendar.getInstance(TimeZone.getDefault());
        date = String.format("%d/%d/%d", today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), today.get(Calendar.YEAR));
        myDate.setText(date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = month + "/" + (dayOfMonth) + "/" + year;
                myDate.setText(date);
            }
        });

        bChckRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("date", date);
                Intent intent = new Intent(CalendarActivity.this, DailyActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    }
}
