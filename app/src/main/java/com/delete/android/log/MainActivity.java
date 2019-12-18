package com.delete.android.log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testLog1();
        testLog2();
    }

    private void testLog1(){
        Log.d(MainActivity.class.getSimpleName(),"testLog1");
    }

    private void testLog2(){
        Log.e(MainActivity.class.getSimpleName(),"testLog2");
    }
}
