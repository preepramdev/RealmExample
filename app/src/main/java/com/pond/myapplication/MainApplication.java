package com.pond.myapplication;

import android.app.Application;

import io.realm.Realm;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext()); // todo 3. add this line
    }
}
