package ru.example.searchrepogithub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ChartActivity extends AppCompatActivity {
    String date;
    String  actorLogin;
    String dateArray[];
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        date ="";
        actorLogin="";

//      String repo = getIntent().getStringExtra("repo");
      date = getIntent().getStringExtra("date");

      Log.i("message Array", date);
        dateArray = date.split(";");



        BarChart barChart = (BarChart) findViewById(R.id.barChat);
        ArrayList<BarEntry> barEntry = new ArrayList<>();
//        ArrayList<BarEntry> barEntry2 = new ArrayList<BarEntry>(messageList);

        barEntry.add(new BarEntry(20,420));
        barEntry.add(new BarEntry(24,420));
        barEntry.add(new BarEntry(26,420));
        barEntry.add(new BarEntry(27,420));
        barEntry.add(new BarEntry(29,420));
        barEntry.add(new BarEntry(24,420));
        BarDataSet barDataSet =new BarDataSet(barEntry,"barentrytext");
        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData= new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("BarChart exz");
        barChart.animateY(5000);


        ListView dateListView = (ListView) findViewById(R.id.dateListView);
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, dateArray);

        dateListView.setAdapter(adapter);



    }
}
