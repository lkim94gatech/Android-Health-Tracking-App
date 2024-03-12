package com.example.a2340project;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.health.connect.datatypes.MealType;
import android.os.Bundle;


import org.checkerframework.checker.units.qual.A;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;

import java.util.List;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);

        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();

        data.add(new ValueDataEntry("Hohn", 199));
        data.add(new ValueDataEntry("Bowman", 133));
        data.add(new ValueDataEntry("Dear", 445));

        pie.data(data);

        anyChartView.setChart(pie);

        pie.draw(true);

        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, LoginScreen.class));
                }
            }
        };
        thread.start();
    }
}