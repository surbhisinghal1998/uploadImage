package com.solution.uploadimage.Utils;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static App instance;
    private static SharedPred sharedpref;
    private static Singletonclass singleton;
    Context context;



    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        sharedpref = new SharedPred(context);
        singleton = new Singletonclass();
    }

    public static SharedPred getSharedpref()
    {
        return sharedpref;
    }

    public static Singletonclass getSingleton() {
        return singleton;

    }

    public static App getAppContext() {
        return instance;
    }

}
