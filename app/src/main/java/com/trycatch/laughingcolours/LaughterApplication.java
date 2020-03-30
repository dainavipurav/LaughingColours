package com.trycatch.laughingcolours;

import android.app.Application;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;

public class LaughterApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        Stetho.initializeWithDefaults(this);
    }
}
