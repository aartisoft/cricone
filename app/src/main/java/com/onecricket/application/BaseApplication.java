package com.onecricket.application;

import android.app.Application;

import com.onecricket.utils.AppEnvironment;


public class BaseApplication extends Application {

    AppEnvironment appEnvironment;

    @Override
    public void onCreate() {
        super.onCreate();
        appEnvironment = AppEnvironment.SANDBOX;
    }

    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    public void setAppEnvironment(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }
}
