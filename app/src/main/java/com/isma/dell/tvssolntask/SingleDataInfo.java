package com.isma.dell.tvssolntask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SingleDataInfo extends AppCompatActivity {
    private TextView tvDataName;
    private TextView tvDataDesignation;
    private TextView tvDataCity;
    private TextView tvDataCode;
    private TextView tvDataDate;
    private TextView tvDataSalary;
    private ImageView ivCaptureImage;

    FloatingActionButton floatingActionButton;

    private static final int CAMERA_REQUEST = 1888;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_data_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        String name=getIntent().getStringExtra("Name");
        String designation=getIntent().getStringExtra("Designation");
        String city=getIntent().getStringExtra("City");
        String code=getIntent().getStringExtra("Code");
        String date=getIntent().getStringExtra("Date");
        String salary=getIntent().getStringExtra("Salary");

        tvDataName.setText(name);
        tvDataDesignation.setText(designation);
        tvDataCity.setText(city);
        tvDataCode.setText(code);
        tvDataDate.setText(date);
        tvDataSalary.setText(salary);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap workingBitmap = Bitmap.createBitmap(photo);
            Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
            Canvas canvas = new Canvas(mutableBitmap);

            Paint paint = new Paint();


            paint.setColor(Color.RED);
            paint.setTextSize(20);

            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
            String currentDateandTime = sdf.format(new Date());
            canvas.drawText(currentDateandTime , 10, 25, paint);

            ivCaptureImage.setImageBitmap(mutableBitmap);

        }
    }

    private void findViews() {
        tvDataName = (TextView)findViewById( R.id.tvDataName );
        tvDataDesignation = (TextView)findViewById( R.id.tvDataDesignation );
        tvDataCity = (TextView)findViewById( R.id.tvDataCity );
        tvDataCode = (TextView)findViewById( R.id.tvDataCode );
        tvDataDate = (TextView)findViewById( R.id.tvDataDate );
        tvDataSalary = (TextView)findViewById( R.id.tvDataSalary );
        ivCaptureImage = (ImageView)findViewById( R.id.ivCaptureImage );
        floatingActionButton=findViewById(R.id.fab);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SingleDataInfo.this,HomeActivity.class));
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

    }
}
