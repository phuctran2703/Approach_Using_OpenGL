package com.example.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DisplayActivity extends AppCompatActivity {
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGLSurfaceView = new GLSurfaceView(this);

        // Get data from Intent
        int number = getIntent().getIntExtra("number", 0);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2)
        {
            mGLSurfaceView.setEGLContextClientVersion(2);
            mGLSurfaceView.setRenderer(new GLRenderer(this, number));
        }
        else
        {
            return;
        }

        setContentView(mGLSurfaceView);
    }
    protected void onResume()
    {
        super.onResume();
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mGLSurfaceView.onPause();
    }
}