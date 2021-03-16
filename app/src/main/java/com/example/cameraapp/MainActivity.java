package com.example.cameraapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView mImageView;
    FloatingActionButton button;
    public android.hardware.Camera mCameraDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = findViewById(R.id.image_view);
        button = findViewById(R.id.capture_image);

        button.setOnClickListener(v -> {
            //checking if user has a camera
            if(!deviceHasCamera()){
                //disabling button
                button.setEnabled(false);
            }
            else{
                launchCamera(v);
            }
        });

    }

    //method to check device has camera or not
    public boolean deviceHasCamera(){
        PackageManager pm = getApplicationContext().getPackageManager();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        }
        return false;
    }

    //method to launch camera
    public void launchCamera(View v){
        //creating intent to open camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //this line will take us to page where image to be captured
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    //this override method will check that we have
    //successfully taken image or not
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //this line shows that intent data is in extras
            //so we have to extract it
            Bundle extras = data.getExtras();
            //convert intent to bitmap to set as image for our image view
            Bitmap photo = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(photo);
        }
    }
}