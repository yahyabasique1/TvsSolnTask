package com.isma.dell.tvssolntask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);

        BarChart chart = findViewById(R.id.barchart);
        ArrayList year = new ArrayList();

        ArrayList NoOfEmp = new ArrayList();
        for(int i=0;i<10;i++){
            NoOfEmp.add(new BarEntry(Float.parseFloat(CacheData.listList.get(i).get(5).substring(1).replace(",","")), i));
            year.add(CacheData.listList.get(i).get(2));
        }


        BarDataSet bardataset = new BarDataSet(NoOfEmp, "Salaries Of Employees");
        chart.animateY(5000);
        BarData data = new BarData(year, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BarChartActivity.this,HomeActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}
